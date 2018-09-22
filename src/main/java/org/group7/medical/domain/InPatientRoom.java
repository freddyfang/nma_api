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
@Table(name = "in_patient_room")
@Access(value = AccessType.FIELD)
public class InPatientRoom implements Serializable {
	private static final long serialVersionUID = 769060838221306884L;

	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column(name = "room_num")
	private Integer roomNum;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
//	@OneToMany(mappedBy = "room")
//	private List<InPatientBed> beds;
	
	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

//	public List<InPatientBed> getBeds() {
//		return beds;
//	}
//
//	public void setBeds(List<InPatientBed> beds) {
//		this.beds = beds;
//	}
}
