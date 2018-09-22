package org.group7.medical.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.group7.medical.annotation.StrictTransaction;
import org.group7.medical.constant.StaffType;
import org.group7.medical.domain.Employee;
import org.group7.medical.domain.Nurse;
import org.group7.medical.domain.Physician;
import org.group7.medical.domain.SupportStaff;
import org.group7.medical.domain.Surgeon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
	
	@PersistenceContext
	public EntityManager em;
	
	@Transactional(readOnly = true)
	public Surgeon findSurgeon(int seq) {
		return em.find(Surgeon.class, seq);
	}
	
	@Transactional(readOnly = true)
	public Nurse findNurse(int seq) {
		return em.find(Nurse.class, seq);
	}
	
	@Transactional(readOnly = true)
	public Physician findPhysician(int seq) {
		return em.find(Physician.class, seq);
	}
	
	@Transactional(readOnly = true)
	public SupportStaff findSupportStaff(int seq) {
		return em.find(SupportStaff.class, seq);
	}
	
	@Transactional(readOnly = true)
	public List<Surgeon> findAllSurgeons() {
		return em.createQuery("FROM Surgeon s WHERE s.employee.disable IS FALSE", Surgeon.class)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<SupportStaff> findAllSupportStaffs() {
		return em.createQuery("FROM SupportStaff s WHERE s.employee.disable IS FALSE", SupportStaff.class)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Nurse> findAllNurses() {
		return em.createQuery("FROM Nurse n WHERE n.employee.disable IS FALSE", Nurse.class)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Physician> findAllPhysicians() {
		return em.createQuery("FROM Physician p WHERE p.employee.disable IS FALSE", Physician.class)
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Physician> findAvailablePhysicians(LocalDateTime datetime) {
		String query = "FROM Physician p WHERE p.employee.disable IS FALSE AND p.seq NOT IN (SELECT c.doctor.seq FROM Consultation c WHERE c.datetime > :start AND c.datetime < :end)";
		return em.createQuery(query, Physician.class)
				.setParameter("start", datetime.minusMinutes(30))
				.setParameter("end", datetime.plusMinutes(30))
				.getResultList();
	}
	
	@Transactional(readOnly = true)
	public boolean checkIfSSNExist(String ssn) {
		return !em.createQuery("FROM Employee e WHERE e.ssn = :ssn", Employee.class)
				.setParameter("ssn", ssn)
				.getResultList()
				.isEmpty();
	}
	
	@StrictTransaction
	public SupportStaff createSupportStaff(SupportStaff staff) {
		staff.getEmployee().setStaffType(StaffType.SUPPORT_STAFF);
		em.persist(staff);
		staff.getEmployee().setEmpNo(staff.getEmployee().getSeq());
		return staff;
	}
	
	@StrictTransaction
	public Surgeon createSurgeon(Surgeon surgeon) {
		surgeon.getEmployee().setStaffType(StaffType.SURGEON);
		em.persist(surgeon);
		surgeon.getEmployee().setEmpNo(surgeon.getEmployee().getSeq());
		return surgeon;
	}
	
	@StrictTransaction
	public Nurse createNurse(Nurse nurse) {
		nurse.getEmployee().setStaffType(StaffType.NURSE);
		em.persist(nurse);
		nurse.getEmployee().setEmpNo(nurse.getEmployee().getSeq());
		return nurse;
	}

	@StrictTransaction
	public Physician createPhysician(Physician physician) {
		physician.getEmployee().setStaffType(StaffType.PHYSICIAN);
		em.persist(physician);
		physician.getEmployee().setEmpNo(physician.getEmployee().getSeq());
		return physician;
	}
	
	@StrictTransaction
	public void removeSupportStaff(int staffSeq) {
		SupportStaff staff = em.find(SupportStaff.class, staffSeq);
		if(staff != null) {
			staff.getEmployee().setDisable(true);
		}
	}
	
	@StrictTransaction
	public void removeSurgeon(int surgeonSeq) throws IllegalStateException{
		Surgeon surgeon = em.find(Surgeon.class, surgeonSeq);
		if(surgeon == null) {
			return;
		}
		
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		boolean surgeryRetained = !em.createQuery("FROM SurgerySchedule ss WHERE ss.surgeon.seq = :seq AND ss.endDatetime > :now")
				.setParameter("seq", surgeonSeq)
				.setParameter("now", now)
				.getResultList()
				.isEmpty();
		
		if(surgeryRetained) {
			throw new IllegalStateException("Cannot remove the surgeon! Some surgery performed by this surgeon is retained.");
		}
		
		surgeon.getEmployee().setDisable(true);
	}
	
	@StrictTransaction
	public void removeNurse(int nurseSeq) {
		Nurse nurse = em.find(Nurse.class, nurseSeq);
		if(nurse == null) {
			return;
		}
		
		em.createQuery("UPDATE InPatient ip SET ip.nurse = NULL WHERE ip.nurse.seq = :seq")
				.setParameter("seq", nurseSeq)
				.executeUpdate();
		
		nurse.getEmployee().setDisable(true);
	}
	
	@StrictTransaction
	public void removePhysician(int physicianSeq) {
		Physician physician = em.find(Physician.class, physicianSeq);
		if(physician == null) {
			return;
		}
		
		Physician chief = em.createQuery("FROM Physician p WHERE p.employee.empNo = 0", Physician.class)
				.getSingleResult();
		
		LocalDateTime now = LocalDateTime.now();
		em.createQuery("UPDATE Consultation c SET c.doctor = :doctor WHERE c.doctor.seq = :seq AND c.datetime > :now")
				.setParameter("doctor", chief)
				.setParameter("seq", physicianSeq)
				.setParameter("now", now)
				.executeUpdate();
		
		em.createQuery("UPDATE Patient p SET p.primaryDoctor = :doctor WHERE p.primaryDoctor.seq = :seq")
				.setParameter("doctor", chief)
				.setParameter("seq", physicianSeq)
				.executeUpdate();
		
		physician.getEmployee().setDisable(true);
	}
}
