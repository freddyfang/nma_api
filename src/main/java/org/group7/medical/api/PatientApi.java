package org.group7.medical.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.group7.medical.domain.Patient;
import org.group7.medical.domain.PatientAllergy;
import org.group7.medical.domain.PatientIllness;
import org.group7.medical.service.PatientService;
import org.group7.medical.util.ApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/patient")
public class PatientApi {
	
	@Autowired
	private PatientService patientServ;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getPatients() {
		List<Patient> patients = patientServ.findAll();
		return ApiHelper.successResp(patients);
	}

	@RequestMapping(value = "/{patientSeq}", method = RequestMethod.GET)
	public Map<String, Object> getPatient(@PathVariable int patientSeq) {
		Patient patient = patientServ.findBySeq(patientSeq);
		return ApiHelper.successResp(patient);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> addPatient(@RequestBody Patient patient) {
		if(patient.getSsn() == null || patient.getSsn().length() != 9) {
			// SSN incorrect
			return ApiHelper.failedResp("Incorrect SSN number!");
		} else if(patientServ.checkIfSSNExist(patient.getSsn())) {
			// SSN exist
			return ApiHelper.failedResp("SSN exist!");
		} else if(patient.getName() == null) {
			// Name
			return ApiHelper.failedResp("Name required!");
		} else if(patient.getBirth() == null) {
			// Birth
			return ApiHelper.failedResp("Birth required!");
		} else if(patient.getTel() == null || patient.getTel().length() != 10) {
			// Tel
			return ApiHelper.failedResp("Incorrect phone number!");
		}
		
		patient.setSeq(null);
		patient.setPatientNo(null);
		patient = patientServ.addPatient(patient);
		
		return ApiHelper.successResp(patient);
	}
	
	@RequestMapping(value = "/illness", method = RequestMethod.GET)
	public Map<String, Object> queryIllnessHistory(@RequestParam int patientSeq) {
		List<PatientIllness> history = patientServ.findIllnessHistory(patientSeq);
		return ApiHelper.successResp(history);
	}

	@RequestMapping(value = "/allergy", method = RequestMethod.GET)
	public Map<String, Object> queryAllergyHistory(@RequestParam int patientSeq) {
		List<PatientAllergy> history = patientServ.findAllergyHistory(patientSeq);
		return ApiHelper.successResp(history);
	}

	@RequestMapping(value = "/available", method = RequestMethod.GET)
	public Map<String, Object> queryAvailablePatients(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime datetime) {
		List<Patient> result = patientServ.findAvailablePatients(datetime);
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(value = "/doctor", method = RequestMethod.PUT)
	public Map<String, Object> assignDoctor(@RequestBody Map<String, Object> params) {
		int patientSeq = (int) params.get("patientSeq");
		int doctorSeq = (int) params.get("doctorSeq");
		
		Patient result = patientServ.assignDoctor(patientSeq, doctorSeq);
		
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(value = "/doctor", method = RequestMethod.DELETE)
	public Map<String, Object> removeDoctor(@RequestParam int patientSeq) {
		Patient result = patientServ.removeDoctor(patientSeq);
		return ApiHelper.successResp(result);
	}
	

}
