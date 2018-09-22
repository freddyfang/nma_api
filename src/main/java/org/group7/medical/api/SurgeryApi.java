package org.group7.medical.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.group7.medical.domain.Patient;
import org.group7.medical.domain.Surgeon;
import org.group7.medical.domain.SurgeryRoom;
import org.group7.medical.domain.SurgerySchedule;
import org.group7.medical.domain.SurgeryType;
import org.group7.medical.service.SurgeryService;
import org.group7.medical.util.ApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/surgery")
public class SurgeryApi {
	
	@Autowired
	private SurgeryService surgeryServ;
	
	@RequestMapping(value = "/schedule", method = RequestMethod.GET)
	public Map<String, Object> viewSurgerySchedules(
			@RequestParam(required = false) Integer roomSeq,
			@RequestParam(required = false) Integer patientSeq,
			@RequestParam(required = false) Integer surgeonSeq,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		List<SurgerySchedule> result = surgeryServ.findByConditions(roomSeq, patientSeq, surgeonSeq, date);
		return ApiHelper.successResp(result);
	}

	@RequestMapping(value = "/available_room", method = RequestMethod.GET)
	public Map<String, Object> getAvailableRooms(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDatetime,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDatetime) {
		List<SurgeryRoom> result = surgeryServ.findAvailableRooms(startDatetime, endDatetime);
		return ApiHelper.successResp(result);
	}

	@RequestMapping(value = "/available_surgeon", method = RequestMethod.GET)
	public Map<String, Object> getAvailableSurgeons(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDatetime,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDatetime) {
		List<Surgeon> result = surgeryServ.findAvailableSurgeons(startDatetime, endDatetime);
		return ApiHelper.successResp(result);
	}

	@RequestMapping(value = "/available_patient", method = RequestMethod.GET)
	public Map<String, Object> getAvailablePatients(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDatetime,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDatetime) {
		List<Patient> result = surgeryServ.findAvailablePatients(startDatetime, endDatetime);
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public Map<String, Object> getSurgeryTypes() {
		List<SurgeryType> result = surgeryServ.findAllSurgeryTypes();
		return ApiHelper.successResp(result);
	}

	@RequestMapping(value = "/room", method = RequestMethod.GET)
	public Map<String, Object> getSurgeryRooms() {
		List<SurgeryRoom> result = surgeryServ.findAllSurgeryRooms();
		return ApiHelper.successResp(result);
	}
	
	@RequestMapping(value = "/schedule", method = RequestMethod.POST)
	public Map<String, Object> scheduleSurgery(@RequestBody Map<String, Object> params) {
		int patientSeq = (int) params.get("patientSeq");
		int surgeonSeq = (int) params.get("surgeonSeq");
		int roomSeq = (int) params.get("roomSeq");
		int surgeryTypeSeq = (int) params.get("surgeryTypeSeq");
		String startDatetime = (String) params.get("startDatetime");
		String endDatetime = (String) params.get("endDatetime");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		SurgerySchedule result = surgeryServ.scheduleSurgery(
				patientSeq, surgeonSeq, roomSeq, surgeryTypeSeq, 
				LocalDateTime.parse(startDatetime, formatter), 
				LocalDateTime.parse(endDatetime, formatter));
		
		return ApiHelper.successResp(result);
	}

}
