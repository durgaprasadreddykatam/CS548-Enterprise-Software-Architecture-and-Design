package edu.stevens.cs548.clinic.data;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//
@Entity
public class PhysiotherapyTreatment extends Treatment {

	private static final long serialVersionUID = 5602950140629148756L;
	
	// Order by date
	@ElementCollection
	@OrderBy
	protected List<LocalDate> treatmentDates;

	public void addTreatmentDate(LocalDate date) {
		treatmentDates.add(date);
	}

	public PhysiotherapyTreatment() {
		super();
		treatmentDates = new ArrayList<LocalDate>();
	}
}
