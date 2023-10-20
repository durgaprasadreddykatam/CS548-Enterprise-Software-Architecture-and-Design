package edu.stevens.cs548.clinic.service.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

public class SurgeryTreatmentDto extends TreatmentDto {

	/*
	 * add two fields:
	 *  surgeryDate (type LocalDate)
	 *  dischargeInstructions (type String)
	 * Annotate with @SerializedName (surgery-date and discharge-instructions)
	 * Also add getter and setter methods for these properties.
	 */

	private LocalDate surgeryDate;
	private String dischargeInstructions;
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
