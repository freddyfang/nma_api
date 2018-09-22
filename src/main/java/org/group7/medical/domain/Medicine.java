package org.group7.medical.domain;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicine")
@Access(value = AccessType.FIELD)
public class Medicine implements Serializable {
	private static final long serialVersionUID = 769061838229306884L;

	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column
	private String code;
	
	@Column
	private String name;
	
	@Column
	private Integer year;
	
	@Column
	private Integer qtyOnHand;
	
	@Column
	private Integer qtyOnOrder;
	
	@Column
	private Double unitCost;
	
	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getQtyOnHand() {
		return qtyOnHand;
	}

	public void setQtyOnHand(Integer qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}

	public Integer getQtyOnOrder() {
		return qtyOnOrder;
	}

	public void setQtyOnOrder(Integer qtyOnOrder) {
		this.qtyOnOrder = qtyOnOrder;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	
}
