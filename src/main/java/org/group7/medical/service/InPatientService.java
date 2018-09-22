package org.group7.medical.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.group7.medical.annotation.StrictTransaction;
import org.group7.medical.domain.InPatient;
import org.group7.medical.domain.InPatientBed;
import org.group7.medical.domain.Nurse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InPatientService {
	
	@PersistenceContext
	public EntityManager em;
	
	@Transactional(readOnly = true)
	public List<InPatient> findAll() {
		return em.createQuery("FROM InPatient ip", InPatient.class)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public InPatient findBySeq(int inpatientSeq) {
		return em.find(InPatient.class, inpatientSeq);
	}
	
	@Transactional(readOnly = true)
	public List<InPatientBed> findAvailableBeds() {
		String query = "FROM InPatientBed ipb WHERE ipb.seq NOT IN (SELECT ip.bed.seq FROM InPatient ip WHERE ip.bed IS NOT NULL)";
		return em.createQuery(query, InPatientBed.class)
				.getResultList();
	}
	
	@StrictTransaction
	public InPatient assignBed(int inpatientSeq, int bedSeq) {
		InPatient inpatient = em.find(InPatient.class, inpatientSeq);
		
		if(inpatient != null) {
			InPatientBed bed = em.find(InPatientBed.class, bedSeq);
			inpatient.setBed(bed);
		}
		
		return inpatient;
	}
	
	@StrictTransaction
	public InPatient removeBed(int inpatientSeq) {
		InPatient inpatient = em.find(InPatient.class, inpatientSeq);
		
		if(inpatient != null) {
			inpatient.setBed(null);
		}
		
		return inpatient;
	}

	@StrictTransaction
	public InPatient assignNurse(int inpatientSeq, int nurseSeq) {
		InPatient inpatient = em.find(InPatient.class, inpatientSeq);
		
		if(inpatient != null) {
			Nurse nurse = em.find(Nurse.class, nurseSeq);
			inpatient.setNurse(nurse);
		}
		
		return inpatient;
	}
	
	@StrictTransaction
	public InPatient removeNurse(int inpatientSeq) {
		InPatient inpatient = em.find(InPatient.class, inpatientSeq);
		
		if(inpatient != null) {
			inpatient.setNurse(null);
		}
		
		return inpatient;
	}
}
