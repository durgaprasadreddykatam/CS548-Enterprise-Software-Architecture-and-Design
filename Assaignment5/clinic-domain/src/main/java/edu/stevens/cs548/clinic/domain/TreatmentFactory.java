package edu.stevens.cs548.clinic.domain;

public class TreatmentFactory implements ITreatmentFactory {


	@Override
	public DrugTreatment createDrugTreatment() {
		return new DrugTreatment();
	}
	@Override

	public RadiologyTreatment createRadiologyTreatment() {
		return new RadiologyTreatment();
	}
	@Override

	public SurgeryTreatment createSurgeryTreatment() {
		return new SurgeryTreatment();
	}
	@Override
	public PhysiotherapyTreatment createPhysiotherapyTreatment() {
		return new PhysiotherapyTreatment();
	}




}
