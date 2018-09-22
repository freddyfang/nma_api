package org.group7.medical.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "surgery_schedule")
@Access(value = AccessType.FIELD)
public class SurgerySchedule implements Serializable {
	private static final long serialVersionUID = 769060838239746884L;

	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "start_datetime")
	private LocalDateTime startDatetime;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "end_datetime")
	private LocalDateTime endDatetime;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@ManyToOne
	@JoinColumn(name = "surgeon")
	private Surgeon surgeon;
	
	@ManyToOne
	@JoinColumn(name = "patient")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "type")
	private SurgeryType type;
	
	@ManyToOne
	@JoinColumn(name = "room")
	private SurgeryRoom room;
	
	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public LocalDateTime getStartDatetime() {
		return startDatetime;
	}

	public void setStartDatetime(LocalDateTime startDatetime) {
		this.startDatetime = startDatetime;
	}

	public LocalDateTime getEndDatetime() {
		return endDatetime;
	}

	public void setEndDatetime(LocalDateTime endDatetime) {
		this.endDatetime = endDatetime;
	}

	public SurgeryRoom getRoom() {
		return room;
	}

	public void setRoom(SurgeryRoom room) {
		this.room = room;
	}

	public Surgeon getSurgeon() {
		return surgeon;
	}

	public void setSurgeon(Surgeon surgeon) {
		this.surgeon = surgeon;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public SurgeryType getType() {
		return type;
	}

	public void setType(SurgeryType type) {
		this.type = type;
	}

}
