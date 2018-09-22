package org.group7.medical.api;

import java.util.List;
import java.util.Map;

import org.group7.medical.domain.Nurse;
import org.group7.medical.service.EmployeeService;
import org.group7.medical.util.ApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/nurse")
public class NurseApi {
	
	@Autowired
	private EmployeeService employeeServ;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getNurses() {
		List<Nurse> nurses = employeeServ.findAllNurses();
		return ApiHelper.successResp(nurses);
	}

	@RequestMapping(value = "/{seq}", method = RequestMethod.GET)
	public Map<String, Object> getNurse(@PathVariable int seq) {
		Nurse result = employeeServ.findNurse(seq);
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> addNurse(@RequestBody Nurse nurse) {
		if(employeeServ.checkIfSSNExist(nurse.getEmployee().getSsn())) {
			// SSN exist
			return ApiHelper.failedResp("SSN exist!");
		} else if(nurse.getEmployee().getSsn() == null || nurse.getEmployee().getSsn().length() != 9) {
			// SSN incorrect
			return ApiHelper.failedResp("Incorrect SSN number!");
		} else if(nurse.getEmployee().getName() == null) {
			// Name
			return ApiHelper.failedResp("Name required!");
		} else if(nurse.getEmployee().getTel() == null || nurse.getEmployee().getTel().length() != 10) {
			// Tel incorrect
			return ApiHelper.failedResp("Incorrect phone number!");
		}
		
		nurse = employeeServ.createNurse(nurse);
		return ApiHelper.successResp(nurse);
	}
	
	@RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
	public Map<String, Object> removeNurse(@PathVariable int seq) {
		employeeServ.removeNurse(seq);
		return ApiHelper.successResp(null);
	}

}
