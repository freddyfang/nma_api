package org.group7.medical.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.group7.medical.annotation.StrictTransaction;
import org.group7.medical.domain.Consultation;
import org.group7.medical.domain.Patient;
import org.group7.medical.domain.Physician;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultationService {

	@PersistenceContext
	public EntityManager em;
	
	@Transactional(readOnly = true)
	public List<Consultation> findAll(Integer doctorSeq, LocalDate date) {
		StringBuilder queryBuilder = new StringBuilder("FROM Consultation c WHERE ");
		Object[] params = new Object[3];
		
		if(doctorSeq != null) {
			queryBuilder.append("c.doctor.seq = ?0 AND ");
			params[0] = doctorSeq;
		}
		
		if(date != null) {
			queryBuilder.append("c.datetime >= ?1 AND c.datetime < ?2");
			params[1] = date.atStartOfDay();
			params[2] = date.plusDays(1).atStartOfDay();
		}
		
		// Trim the last AND or WHERE if exist
		String queryStr = queryBuilder.toString();
		if(queryStr.endsWith("AND ")) {
			queryStr = queryStr.substring(0, queryStr.length() - 4);
		} else if(queryStr.endsWith("WHERE ")) {
			queryStr = queryStr.substring(0, queryStr.length() - 6);
		}
		
		TypedQuery<Consultation> query = em.createQuery(queryStr, Consultation.class);
		for(int i = 0; i < params.length; i++) {
			if(params[i] != null) {
				query.setParameter(i, params[i]);
			}
		}
		
		return query.getResultList();
	}
	
	@StrictTransaction
	public Consultation createConsultation(int patientSeq, int doctorSeq, LocalDateTime datetime) {
		Consultation consultation = new Consultation();
		
		consultation.setPatient(em.find(Patient.class, patientSeq));
		consultation.setDoctor(em.find(Physician.class, doctorSeq));
		consultation.setDatetime(datetime);
		em.persist(consultation);
		
		return consultation;
	}
}
