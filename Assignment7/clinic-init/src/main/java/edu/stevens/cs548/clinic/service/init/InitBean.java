package edu.stevens.cs548.clinic.service.init;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import edu.stevens.cs548.clinic.service.IPatientService;
import edu.stevens.cs548.clinic.service.IProviderService;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentDto;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.PhysiotherapyTreatmentDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyTreatmentDto;
import edu.stevens.cs548.clinic.service.dto.SurgeryTreatmentDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDtoFactory;

@Singleton
@LocalBean
@Startup
// @ApplicationScoped
// @Transactional
public class InitBean {

	private static final Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());

	private static final ZoneId ZONE_ID = ZoneOffset.UTC;

	private PatientDtoFactory patientFactory = new PatientDtoFactory();

	private ProviderDtoFactory providerFactory = new ProviderDtoFactory();

	private TreatmentDtoFactory treatmentFactory = new TreatmentDtoFactory();

	@Inject
	private IPatientService patientService;

	@Inject
	private IProviderService providerService;

	/*
	 * Initialize using EJB logic
	 */
	@PostConstruct
	/*
	 * This should work to initialize with CDI bean, but there is a bug in
	 * Payara.....
	 */
	// public void init(@Observes @Initialized(ApplicationScoped.class)
	// ServletContext init) {
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
			 * Note that the service generates the external ids, when adding the entities.
			 */

			providerService.removeAll();
			patientService.removeAll();


			PatientDto john = patientFactory.createPatientDto();
			john.setName("John Doe");
			john.setDob(LocalDate.parse("1995-08-15"));
			john.setId(patientService.addPatient(john));

			PatientDto Kumar=patientFactory.createPatientDto();
			Kumar.setName("Kumar");
			Kumar.setDob(LocalDate.parse("2001-02-10"));
			Kumar.setId(patientService.addPatient(Kumar));

			PatientDto KRISHNA=patientFactory.createPatientDto();
			KRISHNA.setName("KRISHNA");
			KRISHNA.setDob(LocalDate.parse("1999-05-03"));
			KRISHNA.setId(patientService.addPatient(KRISHNA));

			PatientDto Peter=patientFactory.createPatientDto();
			Peter.setName("Peter");
			Peter.setDob(LocalDate.parse("1990-12-25"));
			Peter.setId(patientService.addPatient(Peter));

			PatientDto Sup=patientFactory.createPatientDto();
			Sup.setName("SUP RAT");
			Sup.setDob(LocalDate.parse("1990-12-25"));
			Sup.setId(patientService.addPatient(Sup));


			ProviderDto jane = providerFactory.createProviderDto();
			jane.setName("Jane Doe");
			jane.setNpi("1234");
			jane.setId(providerService.addProvider(jane));

			ProviderDto surgdoc = providerFactory.createProviderDto();
			surgdoc.setName("Surgery Doctor");
			surgdoc.setNpi("1234");
			surgdoc.setId(providerService.addProvider(surgdoc));

			ProviderDto physiodoc = providerFactory.createProviderDto();
			physiodoc.setName("Physio Doctor");
			physiodoc.setNpi("4589");
			physiodoc.setId(providerService.addProvider(physiodoc));

			ProviderDto drugdoc = providerFactory.createProviderDto();
			drugdoc.setName("DRUG Doctor");
			drugdoc.setNpi("4890");
			drugdoc.setId(providerService.addProvider( drugdoc));

			ProviderDto radiodoc = providerFactory.createProviderDto();
			radiodoc.setName("Radiology Doctor");
			radiodoc.setNpi("3489");
			radiodoc.setId(providerService.addProvider(radiodoc));

			ProviderDto Isac = providerFactory.createProviderDto();
			Isac.setName("Isac Benjamin");
			Isac.setNpi("3489");
			Isac.setId(providerService.addProvider(Isac));

			DrugTreatmentDto drug01 = treatmentFactory.createDrugTreatmentDto();
			drug01.setPatientId(john.getId());
			drug01.setPatientName(john.getName());
			drug01.setProviderId(drugdoc.getId());
			drug01.setProviderName(drugdoc.getName());
			drug01.setDiagnosis("Headache");
			drug01.setDrug("Aspirin");
			drug01.setDosage(20);
			drug01.setFrequency(7);
			drug01.setStartDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));
			drug01.setEndDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));

			providerService.addTreatment(drug01);



			DrugTreatmentDto drug02 = treatmentFactory.createDrugTreatmentDto();
			drug02.setPatientId(Kumar.getId());
			drug02.setPatientName(Kumar.getName());
			drug02.setProviderId( drugdoc.getId());
			drug02.setProviderName( drugdoc.getName());
			drug02.setDiagnosis("Knne");
			drug02.setDrug("Kneerelif");
			drug02.setDosage(10);
			drug02.setFrequency(3);
			drug02.setStartDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));
			drug02.setEndDate(LocalDate.ofInstant(Instant.now(), ZONE_ID));

			providerService.addTreatment(drug02);





			RadiologyTreatmentDto radio1=treatmentFactory.createRadiologyTreatmentDto();
			radio1.setPatientId(Peter.getId());
			radio1.setPatientName(Peter.getName());
			radio1.setProviderId(radiodoc.getId());
			radio1.setProviderName(radiodoc.getName());
			radio1.setDiagnosis("Radio1");
			List<LocalDate> radiodates=new ArrayList<>();
			radiodates.add(LocalDate.of(2022,12,10));
			radiodates.add(LocalDate.of(2022,12,30));
			radio1.setTreatmentDates(radiodates);

			providerService.addTreatment(radio1);


			PhysiotherapyTreatmentDto physio1=new PhysiotherapyTreatmentDto();
			physio1.setPatientId(Sup.getId());
			physio1.setPatientName(Sup.getName());
			physio1.setProviderId(physiodoc.getId());
			physio1.setProviderName(physiodoc.getName());
			physio1.setDiagnosis("Physio1diag");
			List<LocalDate> physiodates1=new ArrayList<>();
			physiodates1.add(LocalDate.of(2022,12,10));
			physiodates1.add(LocalDate.of(2022,12,30));

			physio1.setTreatmentDates(physiodates1);
			providerService.addTreatment(physio1);

			PhysiotherapyTreatmentDto followup=treatmentFactory.createPhysiotherapyTreatmentDto();
			followup.setPatientId(KRISHNA.getId());
			followup.setPatientName(KRISHNA.getName());
			followup.setProviderId(Isac.getId());
			followup.setProviderName(Isac.getName());
			followup.setDiagnosis("Physio2diag");
			List<LocalDate> phydates1=new ArrayList<>();
			phydates1.add(LocalDate.of(2023,12,23));
			phydates1.add(LocalDate.of(2024,11,22));
			followup.setTreatmentDates(phydates1);




			SurgeryTreatmentDto surg1=treatmentFactory.createSurgeryTreatmentDto();
			surg1.setPatientId(KRISHNA.getId());
			surg1.setPatientName(KRISHNA.getName());
			surg1.setProviderId(surgdoc.getId());
			surg1.setProviderName(surgdoc.getName());
			surg1.setDiagnosis("Surgery1");
			surg1.setSurgeryDate(LocalDate.of(2023,10,13));
			surg1.setDischargeInstructions("Immediately");
			surg1.setFollowupTreatments(Arrays.asList(followup));
			providerService.addTreatment(surg1);

			DrugTreatmentDto drug3=treatmentFactory.createDrugTreatmentDto();
			drug3.setPatientId(KRISHNA.getId());
			drug3.setPatientName(KRISHNA.getName());
			drug3.setProviderId(drugdoc.getId());
			drug3.setProviderName(drugdoc.getName());
			drug3.setDiagnosis("Surgery Drug");
			drug3.setDrug("Morphin");
			drug3.setDosage(1);
			drug3.setFrequency(1);
			drug3.setStartDate(LocalDate.of(2023,10,19));
			drug3.setEndDate(LocalDate.of(2023,10,19));
			providerService.addTreatment(drug3);


			Collection<PatientDto> patients = patientService.getPatients();
			for (PatientDto p : patients) {
				logger.info(String.format("Patient %s, ID %s, DOB %s", p.getName(), p.getId().toString(),
						p.getDob().toString()));
				logTreatments(p.getTreatments());
			}

			Collection<ProviderDto> providers = providerService.getProviders();
			for (ProviderDto p : providers) {
				logger.info(String.format("Provider %s, ID %s, NPI %s", p.getName(), p.getId().toString(), p.getNpi()));
				logTreatments(p.getTreatments());
			}

		} catch (Exception e) {

			throw new IllegalStateException("Failed to add record.", e);

		}

	}

	public void shutdown(@Observes @Destroyed(ApplicationScoped.class) ServletContext init) {
		logger.info("App shutting down....");
	}

	private void logTreatments(Collection<TreatmentDto> treatments) {
		for (TreatmentDto treatment : treatments) {
			if (treatment instanceof DrugTreatmentDto) {
				logTreatment((DrugTreatmentDto) treatment);
			} else if (treatment instanceof SurgeryTreatmentDto) {
				logTreatment((SurgeryTreatmentDto) treatment);
			} else if (treatment instanceof RadiologyTreatmentDto) {
				logTreatment((RadiologyTreatmentDto) treatment);
			} else if (treatment instanceof PhysiotherapyTreatmentDto) {
				logTreatment((PhysiotherapyTreatmentDto) treatment);
			}
			if (!treatment.getFollowupTreatments().isEmpty()) {
				logger.info("============= Follow-up Treatments");
				logTreatments(treatment.getFollowupTreatments());
				logger.info("============= End Follow-up Treatments");
			}
		}
	}

	private void logTreatment(DrugTreatmentDto t) {
		logger.info(String.format("...Drug treatment for %s, drug %s", t.getPatientName(), t.getDrug()));
	}

	private void logTreatment(RadiologyTreatmentDto t) {
		logger.info(String.format("...Radiology treatment for %s", t.getPatientName()));
	}

	private void logTreatment(SurgeryTreatmentDto t) {
		logger.info(String.format("...Surgery treatment for %s", t.getPatientName()));
	}

	private void logTreatment(PhysiotherapyTreatmentDto t) {
		logger.info(String.format("...Physiotherapy treatment for %s", t.getPatientName()));
	}

}
