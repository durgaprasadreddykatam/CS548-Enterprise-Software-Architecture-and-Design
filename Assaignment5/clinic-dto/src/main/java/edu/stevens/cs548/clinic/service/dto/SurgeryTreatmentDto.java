package edu.stevens.cs548.clinic.service.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class SurgeryTreatmentDto extends TreatmentDto {

	@SerializedName("surgery-date")
	public LocalDate surgeryDate;
	@SerializedName("discharge-instructions")
	public String dischargeInstructions;

	public LocalDate getSurgeryDate() {
		return surgeryDate;
	}

	public void setSurgeryDate(LocalDate surgeryDate) {
		this.surgeryDate = surgeryDate;
	}

	public String getDischargeInstructions() {
		return dischargeInstructions;
	}

	public void setDischargeInstructions(String dischargeInstructions) {
		this.dischargeInstructions = dischargeInstructions;
	}



}
