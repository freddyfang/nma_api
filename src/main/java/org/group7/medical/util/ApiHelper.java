package org.group7.medical.util;

import static org.group7.medical.constant.ApiRespConstant.CODE_FAILED;
import static org.group7.medical.constant.ApiRespConstant.CODE_FIELD_ERR;
import static org.group7.medical.constant.ApiRespConstant.CODE_SUCCESS;
import static org.group7.medical.constant.ApiRespConstant.KEY_CODE;
import static org.group7.medical.constant.ApiRespConstant.KEY_DATA;
import static org.group7.medical.constant.ApiRespConstant.KEY_ERROR;
import static org.group7.medical.constant.ApiRespConstant.KEY_MESSAGE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApiHelper {
	
	public static Map<String, Object> successResp(Object data) {
		Map<String, Object> result = new HashMap<>();
		result.put(KEY_CODE, CODE_SUCCESS);
		result.put(KEY_DATA, data);
		return result;
	}
	
	public static Map<String, Object> failedResp(String message) {
		Map<String, Object> result = new HashMap<>();
		result.put(KEY_CODE, CODE_FAILED);
		result.put(KEY_MESSAGE, message);
		return result;
	}
	
	@SafeVarargs
	public static Map<String, Object> fieldErrdResp(Pair<String, String>... pairs) {
		Map<String, Object> result = new HashMap<>();
		result.put(KEY_CODE, CODE_FIELD_ERR);
		result.put(KEY_ERROR, Arrays.asList(pairs));
		return result;
	}
}
