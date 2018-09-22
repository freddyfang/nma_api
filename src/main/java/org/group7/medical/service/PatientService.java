package org.group7.medical.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.group7.medical.annotation.StrictTransaction;
import org.group7.medical.domain.Allergy;
import org.group7.medical.domain.Illness;
import org.group7.medical.domain.Patient;
import org.group7.medical.domain.PatientAllergy;
import org.group7.medical.domain.PatientIllness;
import org.group7.medical.domain.Physician;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
	
	@PersistenceContext
	public EntityManager em;
	
	@Transactional(readOnly = true)
	public List<Patient> findAll() {
		return em.createQuery("FROM Patient p", Patient.class)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public Patient findBySeq(int patientSeq) {
		return em.find(Patient.class, patientSeq);
	}
	
	@Transactional(readOnly = true)
	public boolean checkIfSSNExist(String ssn) {
		return !em.createQuery("FROM Patient p WHERE p.ssn = :ssn", Patient.class)
				.setParameter("ssn", ssn)
				.getResultList()
				.isEmpty();
	}
	
	@StrictTransaction
	public Patient addPatient(Patient patient) {
		List<Integer> illnesses = patient.getIllnesses();
		List<Integer> allergies = patient.getAllergies();

		addPrimaryDoctor(patient);
		em.persist(patient);
		
		patient.setPatientNo(patient.getSeq());
		addIllnesses(patient, illnesses);
		addAllergies(patient, allergies);
		
		return patient;
	}
	
	@Transactional(readOnly = true)
	public List<PatientIllness> findIllnessHistory(int patientSeq) {
		return em.createQuery("FROM PatientIllness pi WHERE pi.patient.seq = :seq", PatientIllness.class)
				.setParameter("seq", patientSeq)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<PatientAllergy> findAllergyHistory(int patientSeq) {
		return em.createQuery("FROM PatientAllergy pi WHERE pi.patient.seq = :seq", PatientAllergy.class)
				.setParameter("seq", patientSeq)
				.getResultList();
	}
	
	public List<Patient> findAvailablePatients(LocalDateTime datetime) {
		String query = "FROM Patient p WHERE p.seq NOT IN (SELECT c.patient.seq FROM Consultation c WHERE c.datetime > :start AND c.datetime < :end)";
		return em.createQuery(query, Patient.class)
				.setParameter("start", datetime.minusMinutes(30))
				.setParameter("end", datetime.plusMinutes(30))
				.getResultList();
	}
	
	@StrictTransaction
	public Patient assignDoctor(int patientSeq, int doctorSeq) {
		Patient patient = em.find(Patient.class, patientSeq);
		
		if(patient != null) {
			Physician doctor = em.find(Physician.class, doctorSeq);
			patient.setPrimaryDoctor(doctor);
		}
		
		return patient;
	}
	
	@StrictTransaction
	public Patient removeDoctor(int patientSeq) {
		Patient patient = em.find(Patient.class, patientSeq);
		
		if(patient != null) {
			patient.setPrimaryDoctor(null);
		}
		
		return patient;
	}
	
	//---------------------------------------------------------------------
	private void addPrimaryDoctor(Patient patient) {
		int doctorSeq = patient.getPrimaryDoctorSeq();
		Physician doctor = em.find(Physician.class, doctorSeq);
		patient.setPrimaryDoctor(doctor);
	}
	
	private void addIllnesses(Patient patient, List<Integer> illnesses) {
		if(illnesses == null || illnesses.isEmpty()) {
			return;
		}
		
		for(Integer illnessSeq : illnesses) {
			PatientIllness pi = new PatientIllness();
			pi.setPatient(patient);
			pi.setIllness(em.find(Illness.class, illnessSeq));
			em.persist(pi);
		}
	}

	private void addAllergies(Patient patient, List<Integer> allergies) {
		if(allergies == null || allergies.isEmpty()) {
			return;
		}
		
		for(Integer allergySeq : allergies) {
			PatientAllergy pa = new PatientAllergy();
			pa.setPatient(patient);
			pa.setAllergy(em.find(Allergy.class, allergySeq));
			em.persist(pa);
		}
	}
}
