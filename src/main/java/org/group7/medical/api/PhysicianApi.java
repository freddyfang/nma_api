package org.group7.medical.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.group7.medical.domain.Physician;
import org.group7.medical.service.EmployeeService;
import org.group7.medical.util.ApiHelper;
import org.group7.medical.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/physician")
public class PhysicianApi {
	
	@Autowired
	private EmployeeService employeeServ;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getPhysicians() {
		List<Physician> physicians = employeeServ.findAllPhysicians();
		return ApiHelper.successResp(physicians);
	}
	
	@RequestMapping(value = "/{seq}", method = RequestMethod.GET)
	public Map<String, Object> getPhysician(@PathVariable int seq) {
		Physician result = employeeServ.findPhysician(seq);
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(value = "/available", method = RequestMethod.GET)
	public Map<String, Object> queryAvailablePhysicians(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime datetime) {
		List<Physician> result = employeeServ.findAvailablePhysicians(datetime);
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> addPhysician(@RequestBody Physician physician) {
		if(employeeServ.checkIfSSNExist(physician.getEmployee().getSsn())) {
			// SSN exist
			return ApiHelper.fieldErrdResp(Pair.of("ssn", "SSN exist!"));
		} else if(physician.getEmployee().getSsn() == null || physician.getEmployee().getSsn().length() != 9) {
			// SSN incorrect
			return ApiHelper.failedResp("Incorrect SSN number!");
		} else if(physician.getEmployee().getName() == null) {
			// Name
			return ApiHelper.failedResp("Name required!");
		} else if(physician.getEmployee().getTel() == null || physician.getEmployee().getTel().length() != 10) {
			// Tel incorrect
			return ApiHelper.failedResp("Incorrect phone number!");
		}
		
		physician = employeeServ.createPhysician(physician);
		return ApiHelper.successResp(physician);
	}
	
	@RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
	public Map<String, Object> removePhysician(@PathVariable int seq) {
		employeeServ.removePhysician(seq);
		return ApiHelper.successResp(null);
	}


}
