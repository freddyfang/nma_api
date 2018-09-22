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
@Table(name = "in_patient_bed")
@Access(value = AccessType.FIELD)
public class InPatientBed implements Serializable {
	private static final long serialVersionUID = 769060838221906884L;

	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column(name = "bed_id")
	private String bedId;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@ManyToOne
	@JoinColumn(name = "room")
	private InPatientRoom room;
	
	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getBedId() {
		return bedId;
	}

	public void setBedId(String bedId) {
		this.bedId = bedId;
	}

	public InPatientRoom getRoom() {
		return room;
	}

	public void setRoom(InPatientRoom room) {
		this.room = room;
	}
	
}
