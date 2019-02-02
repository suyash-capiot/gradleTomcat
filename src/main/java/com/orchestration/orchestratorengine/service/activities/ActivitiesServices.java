package com.orchestration.orchestratorengine.service.activities;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.orchestration.orchestratorengine.config.OffersConfig;
import com.orchestration.orchestratorengine.utils.ServletContext;
import com.orchestration.orchestratorengine.utils.TrackingContext;

public class ActivitiesServices implements ActivitiesConstants{

	private static final Logger logger = LogManager.getLogger(ActivitiesServices.class);

	public static String process(JSONObject reqJson) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		ServletContext.setServletContext(request, response);
		
		JSONObject reqHdrJson = (reqJson.optJSONObject(JSON_PROP_REQHEADER) != null) ? reqJson.optJSONObject(JSON_PROP_REQHEADER) : new JSONObject();
        JSONObject reqBodyJson = (reqJson.optJSONObject(JSON_PROP_REQBODY) != null) ? reqJson.optJSONObject(JSON_PROP_REQBODY) : new JSONObject();
        
        JSONObject resJson = new JSONObject();
		try {
			TrackingContext.setTrackingContext(reqJson);
            resJson.put(JSON_PROP_RESHEADER, reqHdrJson);
            resJson.put(JSON_PROP_RESBODY, reqBodyJson);
            
            logger.info(String.format("Booking Engine Request: "+reqJson));
            ActivitiesSupplierOffers.getCompanyOffers(reqJson, resJson, OffersConfig.Type.SUPPLIER_SEARCH_TIME);
            logger.info(String.format("Booking Engine Response: "+resJson));
            
		} catch (Exception e) {
			logger.error("Exception received while processing",e);
			//e.printStackTrace();
			return getEmptyResponse(reqHdrJson).toString();
		}

		return resJson.toString();
	}
	
	public static JSONObject getEmptyResponse(JSONObject reqHdrJson) {
        JSONObject resJson = new JSONObject();
        resJson.put(JSON_PROP_RESHEADER, reqHdrJson);
        resJson.put(JSON_PROP_RESBODY, new JSONObject());
        return resJson;
    }
}
