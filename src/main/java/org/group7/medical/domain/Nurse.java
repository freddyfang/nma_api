package org.group7.medical.domain;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nurse")
@Access(value = AccessType.FIELD)
public class Nurse implements Serializable {
	private static final long serialVersionUID = 744625231889318211L;
	
	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column(name = "annual_salary")
	private Double annualSalary;
	
	@Column
	private Integer grade;

	@Column
	private Integer year;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "employee")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name = "surgery_type")
	private SurgeryType surgeryType;
	
	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Double getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(Double annualSalary) {
		this.annualSalary = annualSalary;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public SurgeryType getSurgeryType() {
		return surgeryType;
	}

	public void setSurgeryType(SurgeryType surgeryType) {
		this.surgeryType = surgeryType;
	}
	
	
	
}
