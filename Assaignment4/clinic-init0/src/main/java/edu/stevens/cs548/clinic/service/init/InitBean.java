package edu.stevens.cs548.clinic.service.init;

import edu.stevens.cs548.clinic.domain.IPatientDao;
import edu.stevens.cs548.clinic.domain.IProviderDao;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Singleton
@LocalBean
@Startup
// @ApplicationScoped
// @Transactional
public class InitBean implements ITreatmentExporter<Void> {

	private static final Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());

	private static final ZoneId ZONE_ID = ZoneOffset.UTC;

	private PatientFactory patientFactory = new PatientFactory();

	private ProviderFactory providerFactory = new ProviderFactory();

	//
	@Inject
	private IPatientDao patientDao;

	//
	@Inject
	private IProviderDao providerDao;

	/*
	 * Initialize using EJB logic
	 */
	@PostConstruct
	public void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the
		 * server logs.
		 */
		logger.info("Your name here: ");
		System.err.println("Your name here!");

		try {

			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * Deletion of all providers also ensures that all treatments are deleted.
			 */

			providerDao.deleteProviders();
			patientDao.deletePatients();

			Patient john = patientFactory.createPatient();
			john.setPatientId(UUID.randomUUID());
			john.setName("John Doe");
			john.setDob(LocalDate.parse("1995-08-15"));
			patientDao.addPatient(john);

			Provider jane = providerFactory.createProvider();
			jane.setProviderId(UUID.randomUUID());
			jane.setName("Jane Doe");
			jane.setNpi("1234");
			providerDao.addProvider(jane);
			jane.importtDrugTreatment(UUID.randomUUID(), john, jane, "Headache", "Aspirin", 10,
					LocalDate.ofInstant(Instant.now(), ZONE_ID), LocalDate.ofInstant(Instant.now(), ZONE_ID), 
					3, null);
			List<LocalDate> physioDates = new ArrayList<>();
			physioDates.add(LocalDate.of(2023, 10, 10));
			physioDates.add(LocalDate.of(2023, 10, 11));
			jane.importPhysiotherapy(UUID.randomUUID(), john, jane, "Headache",physioDates,null);
			jane.importRadiology(UUID.randomUUID(), john, jane, "Headache", physioDates, null );
			jane.importSurgery(UUID.randomUUID(), john, jane, "Headache", LocalDate.now(),"immediate",null);

			// add more testing, including all treatment types and more patients and providers
			Patient Ram = patientFactory.createPatient();
			Ram.setPatientId(UUID.randomUUID());
			Ram.setName("Ram");
			Ram.setDob(LocalDate.parse("2001-08-08"));
			patientDao.addPatient(Ram);

			Provider Kailash = providerFactory.createProvider();
			Kailash.setProviderId(UUID.randomUUID());
			Kailash.setName("Kailash");
			Kailash.setNpi("5678");
			providerDao.addProvider(Kailash);
			List<LocalDate> physioDates1 = new ArrayList<>();
			physioDates1.add(LocalDate.of(2023, 12, 10));
			physioDates1.add(LocalDate.of(2023, 03, 11));

			Kailash.importtDrugTreatment(UUID.randomUUID(), Ram, Kailash, "ToothPain", "Something", 10,
					LocalDate.ofInstant(Instant.now(), ZONE_ID), LocalDate.ofInstant(Instant.now(), ZONE_ID),
					30, null);
			Kailash.importPhysiotherapy(UUID.randomUUID(), Ram, Kailash, "KneePain", physioDates1,null);
			Kailash.importSurgery(UUID.randomUUID(), Ram, Kailash, "StomachPain",  LocalDate.now(),"immediate",null);
			Kailash.importRadiology(UUID.randomUUID(), Ram, Kailash, "ToothPain",  physioDates1, null);
			Consumer add1= jane.importSurgery(UUID.randomUUID(), john, jane, "EyeSurgery",  LocalDate.now(),"immediate",null);
			Kailash.importRadiology(UUID.randomUUID(), Ram, Kailash, "Additionaldiagnosis",  physioDates1, add1);
			// Now show in the logs what has been added

			Collection<Patient> patients = patientDao.getPatients();
			for (Patient p : patients) {
				String dob = p.getDob().toString();
				logger.info(String.format("Patient %s, ID %s, DOB %s", p.getName(), p.getPatientId().toString(), dob));
				p.exportTreatments(this);
			}

			Collection<Provider> providers = providerDao.getProviders();
			for (Provider p : providers) {
				logger.info(String.format("Provider %s, ID %s", p.getName(), p.getProviderId().toString()));
				p.exportTreatments(this);
			}

		} catch (Exception e) {
			;
			throw new IllegalStateException("Failed to add record.", e);

		}

	}


	@Override
	public Void exportDrugTreatment(UUID tid, UUID patientId, String patientName, UUID providerId, String providerName,
			String diagnosis, String drug, float dosage, LocalDate start, LocalDate end, int frequency,
			Supplier<Collection<Void>> followups) {
		logger.info(String.format("...Drug treatment for %s, drug %s", patientName, drug));
		followups.get();
		return null;
	}

	@Override
	public Void exportRadiology(UUID tid, UUID patientId, String patientName, UUID providerId, String providerName,
			String diagnosis, List<LocalDate> dates, Supplier<Collection<Void>> followups) {
		logger.info(String.format("...Radiology treatment for %s", patientName));
		followups.get();
		return null;
	}

	@Override
	public Void exportSurgery(UUID tid, UUID patientId, String patientName, UUID providerId, String providerName,
			String diagnosis, LocalDate date, String dischargeInstructions, Supplier<Collection<Void>> followups) {
		logger.info(String.format("...Surgery treatment for %s", patientName));
		followups.get();
		return null;
	}

	@Override
	public Void exportPhysiotherapy(UUID tid, UUID patientId, String patientName, UUID providerId, String providerName,
			String diagnosis, List<LocalDate> dates, Supplier<Collection<Void>> followups) {
		logger.info(String.format("...Physiotherapy treatment for %s", patientName));
		followups.get();
		return null;
	}

}
