package org.group7.medical.api;

import java.util.List;
import java.util.Map;

import org.group7.medical.domain.SupportStaff;
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
@RequestMapping(value = "api/support_staff")
public class SupportStaffApi {
	
	@Autowired
	private EmployeeService employeeServ;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getSupportStaffs() {
		List<SupportStaff> staffs = employeeServ.findAllSupportStaffs();
		return ApiHelper.successResp(staffs);
	}
	
	@RequestMapping(value = "/{seq}", method = RequestMethod.GET)
	public Map<String, Object> getSupportStaff(@PathVariable int seq) {
		SupportStaff result = employeeServ.findSupportStaff(seq);
		return ApiHelper.successResp(result);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> addSupportStaff(@RequestBody SupportStaff staff) {
		if(employeeServ.checkIfSSNExist(staff.getEmployee().getSsn())) {
			// SSN exist
			return ApiHelper.fieldErrdResp(Pair.of("ssn", "SSN exist!"));
		} else if(staff.getEmployee().getSsn() == null || staff.getEmployee().getSsn().length() != 9) {
			// SSN incorrect
			return ApiHelper.failedResp("Incorrect SSN number!");
		} else if(staff.getEmployee().getName() == null) {
			// Name
			return ApiHelper.failedResp("Name required!");
		} else if(staff.getEmployee().getTel() == null || staff.getEmployee().getTel().length() != 10) {
			// Tel incorrect
			return ApiHelper.failedResp("Incorrect phone number!");
		}
		
		staff = employeeServ.createSupportStaff(staff);
		return ApiHelper.successResp(staff);
	}
	
	@RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
	public Map<String, Object> removeSupportStaff(@PathVariable int seq) {
		employeeServ.removeSupportStaff(seq);
		return ApiHelper.successResp(null);
	}
}
