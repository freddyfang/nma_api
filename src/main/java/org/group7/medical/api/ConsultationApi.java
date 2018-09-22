package org.group7.medical.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.group7.medical.domain.Consultation;
import org.group7.medical.service.ConsultationService;
import org.group7.medical.util.ApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/consultation")
public class ConsultationApi {

	@Autowired
	private ConsultationService consultationServ;
	
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> viewConsultations(
			@RequestParam(required = false) Integer doctorSeq, 
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		List<Consultation> result = consultationServ.findAll(doctorSeq, date);
		return ApiHelper.successResp(result);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> postConsultations(@RequestBody Map<String, Object> params) {
		int doctorSeq = (int)params.get("doctorSeq");
		int patientSeq = (int)params.get("patientSeq");
		LocalDateTime datetime = LocalDateTime.parse((String)params.get("datetime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Consultation result = consultationServ.createConsultation(patientSeq, doctorSeq, datetime);
		return ApiHelper.successResp(result);
	}
}
