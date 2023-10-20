package edu.stevens.cs548.clinic.data;


public interface ITreatmentFactory {
	
	public DrugTreatment createDrugTreatment ();
	public RadiologyTreatment createRadiologyTreatment();

	public SurgeryTreatment createSurgeryTreatment();

	public PhysiotherapyTreatment createPhysiotherapyTreatment();
	
	/*
	 *  add methods for Radiology, Surgery, Physiotherapy
	 */


}
