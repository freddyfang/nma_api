package org.group7.medical.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.group7.medical.annotation.StrictTransaction;
import org.group7.medical.domain.Patient;
import org.group7.medical.domain.Surgeon;
import org.group7.medical.domain.SurgeryRoom;
import org.group7.medical.domain.SurgerySchedule;
import org.group7.medical.domain.SurgeryType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SurgeryService {

	@PersistenceContext
	public EntityManager em;
	
	@Transactional(readOnly = true)
	public List<SurgerySchedule> findByConditions(Integer roomSeq, Integer patientSeq, Integer surgeonSeq, LocalDate date) {
		StringBuilder queryBuilder = new StringBuilder("FROM SurgerySchedule ss WHERE ");
		Object[] params = new Object[5];
		
		if(roomSeq != null) {
			queryBuilder.append("ss.room.seq = ?0 AND ");
			params[0] = roomSeq;
		}
		
		if(patientSeq != null) {
			queryBuilder.append("ss.patient.seq = ?1 AND ");
			params[1] = patientSeq;
		} 
		
		if(surgeonSeq != null) {
			queryBuilder.append("ss.surgeon.seq = ?2 AND ");
			params[2] = surgeonSeq;
		}
		
		if(date != null) {
			queryBuilder.append("ss.startDatetime >= ?3 AND ss.endDatetime < ?4");
			params[3] = date.atStartOfDay();
			params[4] = date.plusDays(1).atStartOfDay();
		}
		
		// Trim the last AND or WHERE if exist
		String queryStr = queryBuilder.toString();
		if(queryStr.endsWith("AND ")) {
			queryStr = queryStr.substring(0, queryStr.length() - 4);
		} else if(queryStr.endsWith("WHERE ")) {
			queryStr = queryStr.substring(0, queryStr.length() - 6);
		}
		
		TypedQuery<SurgerySchedule> query = em.createQuery(queryStr, SurgerySchedule.class);
		for(int i = 0; i < params.length; i++) {
			if(params[i] != null) {
				query.setParameter(i, params[i]);
			}
		}
		
		return query.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<SurgeryRoom> findAvailableRooms(LocalDateTime start, LocalDateTime end) {
		String query = "FROM SurgeryRoom sr WHERE sr.seq NOT IN "
				+ "(SELECT ss.room.seq FROM SurgerySchedule ss WHERE ss.endDatetime > :start AND ss.startDatetime < :end)";
		return em.createQuery(query, SurgeryRoom.class)
				.setParameter("start", start)
				.setParameter("end", end)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Surgeon> findAvailableSurgeons(LocalDateTime start, LocalDateTime end) {
		String query = "FROM Surgeon s WHERE s.seq NOT IN "
				+ "(SELECT ss.surgeon.seq FROM SurgerySchedule ss WHERE ss.endDatetime > :start AND ss.startDatetime < :end)";
		return em.createQuery(query, Surgeon.class)
				.setParameter("start", start)
				.setParameter("end", end)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Patient> findAvailablePatients(LocalDateTime start, LocalDateTime end) {
		String query = "FROM Patient p WHERE p.seq NOT IN "
				+ "(SELECT ss.patient.seq FROM SurgerySchedule ss WHERE ss.endDatetime > :start AND ss.startDatetime < :end)";
		return em.createQuery(query, Patient.class)
				.setParameter("start", start)
				.setParameter("end", end)
				.getResultList();
	}

	@Transactional(readOnly = true)
	public List<SurgeryType> findAllSurgeryTypes() {
		return em.createQuery("FROM SurgeryType st", SurgeryType.class)
				.getResultList();
	}

	@Transactional(readOnly = true)
	public List<SurgeryRoom> findAllSurgeryRooms() {
		return em.createQuery("FROM SurgeryRoom sr", SurgeryRoom.class)
				.getResultList();
	}
	
	@StrictTransaction
	public SurgerySchedule scheduleSurgery(int patientSeq, int surgeonSeq, int roomSeq, int surgeryTypeSeq, 
			LocalDateTime startDatetime, LocalDateTime endDatetime) {
		SurgerySchedule result = new SurgerySchedule();
		Patient patient = em.find(Patient.class, patientSeq);
		Surgeon surgeon = em.find(Surgeon.class, surgeonSeq);
		SurgeryRoom room = em.find(SurgeryRoom.class, roomSeq);
		SurgeryType type = em.find(SurgeryType.class, surgeryTypeSeq);
		
		result.setPatient(patient);
		result.setSurgeon(surgeon);
		result.setRoom(room);
		result.setType(type);
		result.setStartDatetime(startDatetime);
		result.setEndDatetime(endDatetime);
		
		em.persist(result);
		return result;
	}
}
