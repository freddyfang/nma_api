package org.group7.medical.api;

import java.util.List;
import java.util.Map;

import org.group7.medical.domain.InPatient;
import org.group7.medical.domain.InPatientBed;
import org.group7.medical.service.InPatientService;
import org.group7.medical.util.ApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/inpatient")
public class InPatientApi {
	
	@Autowired
	private InPatientService inpatientServ = new InPatientService();
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getInPatients() {
		List<InPatient> inpatients = inpatientServ.findAll();
		return ApiHelper.successResp(inpatients);
	}
	
	@RequestMapping(value = "/available_bed", method = RequestMethod.GET)
	public Map<String, Object> getAvailableBeds() {
		List<InPatientBed> beds = inpatientServ.findAvailableBeds();
		return ApiHelper.successResp(beds);
	}
	
	@RequestMapping(value = "/bed", method = RequestMethod.PUT)
	public Map<String, Object> assignBed(@RequestBody Map<String, Object> params) {
		int inpatientSeq = (int) params.get("inPatientSeq");
		int bedSeq = (int) params.get("bedSeq");
		
		InPatient result = inpatientServ.assignBed(inpatientSeq, bedSeq);
		
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(value = "/bed", method = RequestMethod.DELETE)
	public Map<String, Object> removeBed(@RequestParam int inPatientSeq) {
		InPatient result = inpatientServ.removeBed(inPatientSeq);
		return ApiHelper.successResp(result);
	}

	@RequestMapping(value = "/nurse", method = RequestMethod.PUT)
	public Map<String, Object> assignNurse(@RequestBody Map<String, Object> params) {
		int inpatientSeq = (int) params.get("inPatientSeq");
		int nurseSeq = (int) params.get("nurseSeq");
		
		InPatient result = inpatientServ.assignNurse(inpatientSeq, nurseSeq);
		
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(value = "/nurse", method = RequestMethod.DELETE)
	public Map<String, Object> removeNurse(@RequestParam int inPatientSeq) {
		InPatient result = inpatientServ.removeNurse(inPatientSeq);
		return ApiHelper.successResp(result);
	}
}
