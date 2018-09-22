package org.group7.medical.api;

import java.util.List;
import java.util.Map;

import org.group7.medical.domain.Surgeon;
import org.group7.medical.service.EmployeeService;
import org.group7.medical.util.ApiHelper;
import org.group7.medical.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/surgeon")
public class SurgeonApi {
	
	@Autowired
	private EmployeeService employeeServ;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getSurgeons() {
		List<Surgeon> surgeons = employeeServ.findAllSurgeons();
		return ApiHelper.successResp(surgeons);
	}
	
	@RequestMapping(value = "/{seq}", method = RequestMethod.GET)
	public Map<String, Object> getSurgeon(@PathVariable int seq) {
		Surgeon result = employeeServ.findSurgeon(seq);
		return ApiHelper.successResp(result);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> addSurgeon(@RequestBody Surgeon surgeon) {
		if(surgeon.getEmployee().getSsn() == null || surgeon.getEmployee().getSsn().length() != 9) {
			// SSN incorrect
			return ApiHelper.failedResp("Incorrect SSN number!");
		} else if(employeeServ.checkIfSSNExist(surgeon.getEmployee().getSsn())) {
			// SSN exist
			return ApiHelper.fieldErrdResp(Pair.of("ssn", "SSN exist!"));
		} else if(surgeon.getEmployee().getName() == null) {
			// Name
			return ApiHelper.failedResp("Name required!");
		} else if(surgeon.getEmployee().getTel() == null || surgeon.getEmployee().getTel().length() != 10) {
			// Tel incorrect
			return ApiHelper.failedResp("Incorrect phone number!");
		}
		
		surgeon = employeeServ.createSurgeon(surgeon);
		return ApiHelper.successResp(surgeon);
	}
	
	@RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
	public Map<String, Object> removeSurgeon(@PathVariable int seq) {
		try {
			employeeServ.removeSurgeon(seq);
			return ApiHelper.successResp(null);
		} catch(IllegalStateException e) {
			return ApiHelper.failedResp(e.getMessage());
		}
	}

}
