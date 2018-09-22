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
@Table(name = "surgery_type_skill")
@Access(value = AccessType.FIELD)
public class SurgeryTypeSkill implements Serializable {
	private static final long serialVersionUID = 769023838229746884L;

	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@ManyToOne
	@JoinColumn
	private SurgeryType type;
	
	@ManyToOne
	@JoinColumn
	private SurgerySkill skill;

	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public SurgeryType getType() {
		return type;
	}

	public void setType(SurgeryType type) {
		this.type = type;
	}

	public SurgerySkill getSkill() {
		return skill;
	}

	public void setSkill(SurgerySkill skill) {
		this.skill = skill;
	}
	
}
