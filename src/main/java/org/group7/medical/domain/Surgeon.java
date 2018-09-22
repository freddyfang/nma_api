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
@Table(name = "surgeon")
@Access(value = AccessType.FIELD)
public class Surgeon implements Serializable {
	private static final long serialVersionUID = 8692884779751837731L;
	
	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column
	private String specialty;
	
	@Column(name = "contract_type")
	private String contractType;
	
	@Column(name = "contract_year")
	private Integer contractYear;
	
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

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Integer getContractYear() {
		return contractYear;
	}

	public void setContractYear(Integer contractYear) {
		this.contractYear = contractYear;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
