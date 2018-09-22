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

import org.group7.medical.constant.InteractionSeverity;

@Entity
@Table(name = "interaction")
@Access(value = AccessType.FIELD)
public class Interaction implements Serializable {
	private static final long serialVersionUID = 769060128229746884L;

	/*------------------------
	 * Fields
	 ------------------------*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer seq;
	
	@Column
	private InteractionSeverity severity;
	
	/*------------------------
	 * Mapping Fields
	 ------------------------*/
	@ManyToOne
	@JoinColumn
	private Medicine medicine1;

	@ManyToOne
	@JoinColumn
	private Medicine medicine2;

	/*------------------------
	 * Getter & Setter
	 ------------------------*/
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public InteractionSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(InteractionSeverity severity) {
		this.severity = severity;
	}

	public Medicine getMedicine1() {
		return medicine1;
	}

	public void setMedicine1(Medicine medicine1) {
		this.medicine1 = medicine1;
	}

	public Medicine getMedicine2() {
		return medicine2;
	}

	public void setMedicine2(Medicine medicine2) {
		this.medicine2 = medicine2;
	}

}
