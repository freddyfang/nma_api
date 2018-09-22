package org.group7.medical.domain;

import java.io.Serializable;

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
@Table(name = "in_patient")
@Access(value = AccessType.FIELD)
public class InPatient implements Serializable {
	private static final long serialVersionUID = 761060838279306884L;

	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column(name = "nursing_unit")
	private String nursingUnit;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@ManyToOne
	@JoinColumn(name = "bed")
	private InPatientBed bed;
	
	@ManyToOne
	@JoinColumn(name = "patient")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "nurse")
	private Nurse nurse;
	
	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getNursingUnit() {
		return nursingUnit;
	}

	public void setNursingUnit(String nursingUnit) {
		this.nursingUnit = nursingUnit;
	}

	public InPatientBed getBed() {
		return bed;
	}

	public void setBed(InPatientBed bed) {
		this.bed = bed;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

}
