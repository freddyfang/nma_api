package org.group7.medical.api;

import java.util.List;
import java.util.Map;

import org.group7.medical.domain.Allergy;
import org.group7.medical.domain.Illness;
import org.group7.medical.service.SymptomService;
import org.group7.medical.util.ApiHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/symptom")
public class SymptomApi {
	
	@Autowired
	private SymptomService symptomServ;
	
	@RequestMapping(value = "/illness", method = RequestMethod.GET)
	public Map<String, Object> viewIllnesses() {
		List<Illness> result = symptomServ.findAllIllnesses();
		return ApiHelper.successResp(result);
	}

	@RequestMapping(value = "/allergy", method = RequestMethod.GET)
	public Map<String, Object> viewAllergies() {
		List<Allergy> result = symptomServ.findAllAllergies();
		return ApiHelper.successResp(result);
	}
}
