package org.group7.medical.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "patient_allergy")
@Access(value = AccessType.FIELD)
public class PatientAllergy implements Serializable {
	private static final long serialVersionUID = 769012838229798884L;

	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column(name = "date_diagnosis")
	private LocalDate dateDiagnosis;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@ManyToOne
	@JoinColumn(name = "patient")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "doctor")
	private Physician doctor;
	
	@ManyToOne
	@JoinColumn(name = "allergy")
	private Allergy allergy;

	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Allergy getAllergy() {
		return allergy;
	}

	public void setAllergy(Allergy allergy) {
		this.allergy = allergy;
	}

	public LocalDate getDateDiagnosis() {
		return dateDiagnosis;
	}

	public void setDateDiagnosis(LocalDate dateDiagnosis) {
		this.dateDiagnosis = dateDiagnosis;
	}

	public Physician getDoctor() {
		return doctor;
	}

	public void setDoctor(Physician doctor) {
		this.doctor = doctor;
	}
	
}
