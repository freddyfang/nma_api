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
@Table(name = "surgery_type")
@Access(value = AccessType.FIELD)
public class SurgeryType implements Serializable {
	private static final long serialVersionUID = 749060838279312884L;

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
	private String category;
	
	@Column(name = "special_needs")
	private String specialNeeds;
	
	@Column(name = "anatomical_location")
	private String anatomicalLocation;

	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSpecialNeeds() {
		return specialNeeds;
	}

	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public String getAnatomicalLocation() {
		return anatomicalLocation;
	}

	public void setAnatomicalLocation(String anatomicalLocation) {
		this.anatomicalLocation = anatomicalLocation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
