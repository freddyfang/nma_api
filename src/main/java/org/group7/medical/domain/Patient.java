package org.group7.medical.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "patient")
@Access(value = AccessType.FIELD)
public class Patient implements Serializable {
	private static final long serialVersionUID = 832755492438766568L;
	
	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column(name = "patient_no")
	private Integer patientNo;
	
	@Column
	private String ssn;

	@Column
	private String name;
	
	@Column
	private String gender;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	@Column
	private LocalDate birth;
	
	@Column
	private String address;
	
	@Column
	private String tel;
	
	@Column(name = "blood_type")
	private String bloodType;
	
	@Column(name = "blood_surger")
	private Double bloodSurger;
	
	@Column
	private Double ldl;
	
	@Column
	private Double hdl;
	
	@Column
	private Double triglyceride;
	
	@Transient
	private String riskHeartDisease;
	
	@Transient
	private List<Integer> illnesses;
	
	@Transient
	private List<Integer> allergies;
	
	@Transient
	private Integer primaryDoctorSeq;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@ManyToOne
	@JoinColumn(name = "primary_doctor")
	private Physician primaryDoctor;
	
	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getPatientNo() {
		return patientNo;
	}

	public void setPatientNo(Integer patientNo) {
		this.patientNo = patientNo;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public Double getBloodSurger() {
		return bloodSurger;
	}

	public void setBloodSurger(Double bloodSurger) {
		this.bloodSurger = bloodSurger;
	}

	public Double getLdl() {
		return ldl;
	}

	public void setLdl(Double ldl) {
		this.ldl = ldl;
	}

	public Double getHdl() {
		return hdl;
	}

	public void setHdl(Double hdl) {
		this.hdl = hdl;
	}

	public Double getTriglyceride() {
		return triglyceride;
	}

	public void setTriglyceride(Double triglyceride) {
		this.triglyceride = triglyceride;
	}

	public Physician getPrimaryDoctor() {
		return primaryDoctor;
	}

	public void setPrimaryDoctor(Physician primaryDoctor) {
		this.primaryDoctor = primaryDoctor;
	}

	public String getRiskHeartDisease() {
		double cholesterol = hdl + ldl + triglyceride / 5;
		double ratio = cholesterol / hdl;
		
		if(ratio < 4) {
			return "N";
		} else if(ratio >= 4 && ratio <= 5) {
			return "L";
		} else {
			return "M";
		}
	}

	public void setRiskHeartDisease(String riskHeartDisease) {
		this.riskHeartDisease = riskHeartDisease;
	}

	public List<Integer> getIllnesses() {
		return illnesses;
	}

	public void setIllnesses(List<Integer> illnesses) {
		this.illnesses = illnesses;
	}

	public List<Integer> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Integer> allergies) {
		this.allergies = allergies;
	}

	public Integer getPrimaryDoctorSeq() {
		return primaryDoctorSeq;
	}

	public void setPrimaryDoctorSeq(Integer primaryDoctorSeq) {
		this.primaryDoctorSeq = primaryDoctorSeq;
	}
	
}
