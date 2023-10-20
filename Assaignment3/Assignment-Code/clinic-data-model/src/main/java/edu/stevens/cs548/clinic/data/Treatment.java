package edu.stevens.cs548.clinic.data;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.EAGER;


/**
 * Entity implementation class for Entity: Treatment
 *
 */
@NamedQueries({
	@NamedQuery(
		name="SearchTreatmentByTreatmentId",
		query="select t from Treatment t where t.treatmentId = :treatmentId"),
	@NamedQuery(
			name="CountTreatmentByTreatmentId",
			query="select count(t) from Treatment t where t.treatmentId = :treatmentId"),
	@NamedQuery(
		name = "RemoveAllTreatments", 
		query = "delete from Treatment t")
})

//


@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Treatment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//  PK
	@Id
	@GeneratedValue
	protected long id;
	
	//
	@Column(nullable = false, unique = true)
	protected UUID treatmentId;
	
	protected String diagnosis;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public UUID getTreatmentId() {
		return treatmentId;
	}

	public void setTreatmentId(UUID treatmentId) {
		this.treatmentId = treatmentId;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	/*
	 *  including cascade of persist
	 */
	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "patient_fk")
	protected Patient patient;

	public Patient getPatient() {
		return patient;
	}

	void setPatient(Patient patient) {
		this.patient = patient;
		/*
		 * Make sure the patient also links back to this treatment.
		 */
		if (!patient.receives(this)) {
			patient.addTreatment(this);
		}
	}

	/*
	 *  including cascade of persist
	 */
	@ManyToOne(fetch = EAGER)
	@JoinColumn(name = "provider_fk")
	protected Provider provider;

	public Provider getProvider() {
		return provider;
	}	
	
	public void setProvider(Provider provider) {
		//  see setPatient
		this.provider = provider;
		if (!provider.administers(this)) {
			provider.addTreatment(this);
		}


	}	
	
	/*
	 * including cascade of persist
	 */
	@OneToMany(cascade = PERSIST)
	protected Collection<Treatment> followupTreatments;
	
	public void addFollowupTreatment(Treatment t) {
		followupTreatments.add(t);
	}

	
	public Treatment() {
		super();
		followupTreatments = new ArrayList<Treatment>();
	}
}
