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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "support_staff")
@Access(value = AccessType.FIELD)
public class SupportStaff implements Serializable {
	private static final long serialVersionUID = -2585086592072286467L;
	
	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column(name = "annual_salary")
	private Double annualSalary;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "employee")
	private Employee employee;

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
