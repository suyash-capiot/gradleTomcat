package com.orchestration.orchestratorengine.service.activities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.orchestration.orchestratorengine.common.HTTPServiceConsumer;
import com.orchestration.orchestratorengine.config.CommercialTypeConfig;
import com.orchestration.orchestratorengine.config.OffersConfig;
import com.orchestration.orchestratorengine.config.activities.ActivitiesConfig;

public class ActivitiesSupplierOffers implements ActivitiesConstants {

	private static final Logger logger = LogManager.getLogger(ActivitiesSupplierOffers.class);

	public static void getCompanyOffers(JSONObject reqJson, JSONObject resJson, OffersConfig.Type invocationType) {

		JSONObject reqBodyJson = reqJson.getJSONObject(JSON_PROP_REQBODY);
		
		JSONObject resBodyJson = resJson.getJSONObject(JSON_PROP_RESBODY);
		
		OffersConfig offConfig = ActivitiesConfig.getOffersConfig();
		CommercialTypeConfig offTypeConfig = offConfig.getOfferTypeConfig(invocationType);

		JSONObject breCpnyOffReqJson = new JSONObject(new JSONTokener(offTypeConfig.getRequestJSONShell()));
		JSONArray briJsonArr = breCpnyOffReqJson.getJSONArray(JSON_PROP_COMMANDS).getJSONObject(0).getJSONObject(JSON_PROP_INSERT)
				.getJSONObject(JSON_PROP_OBJECT).getJSONObject(JSON_PROP_ACT_ROOT).getJSONArray(JSON_PROP_BUSSRULEINTAKE);
		JSONObject briJson = new JSONObject();
		
		JSONArray reqBriJsonArr = reqBodyJson.getJSONArray(JSON_PROP_BUSSRULEINTAKE);
		for(int init=0; init<reqBriJsonArr.length(); init++) {
			JSONObject reqBriJsonObj = reqBriJsonArr.getJSONObject(init);
			JSONObject commReqElem = reqBriJsonObj.getJSONObject(JSON_PROP_COMMELEM);
			
			commReqElem.put(JSON_PROP_PROD_CAT_SUBTYPE, commReqElem.optString(JSON_PROP_PROD_CAT_SUBTYPE));
			commReqElem.put(JSON_PROP_SUPPNAME, commReqElem.optString(JSON_PROP_SUPPNAME));
			commReqElem.put(JSON_PROP_SUPPMARKET, commReqElem.optString(JSON_PROP_SUPPMARKET));
			commReqElem.put(JSON_PROP_SUPPRATETYPE, commReqElem.optString(JSON_PROP_SUPPRATETYPE));
			commReqElem.put(JSON_PROP_SUPPRATECODE, commReqElem.optString(JSON_PROP_SUPPRATECODE));
			
			briJson.put(JSON_PROP_COMMELEM, commReqElem);
			briJsonArr.put(briJson);
		}

		JSONObject breOffResJson = null;
		try {
			breOffResJson = HTTPServiceConsumer.consumeJSONService("BRMS/SupplierOffers", offTypeConfig.getServiceURL(),
					offConfig.getHttpHeaders(), breCpnyOffReqJson);
			
		} catch (Exception x) {
			logger.warn("An exception occurred when calling company offers", x);
		}

		if (BRMS_STATUS_TYPE_FAILURE.equals(breOffResJson.getString(JSON_PROP_TYPE))) {
			logger.warn(String.format("A failure response was received from Company Offers calculation engine: %s",
					breOffResJson.toString()));
			return;
		}
		
        // Check offers invocation type
		if (OffersConfig.Type.SUPPLIER_SEARCH_TIME == invocationType) {
			appendOffersToResults(resBodyJson, breOffResJson);
		}
	}
	
	private static void appendOffersToResults(JSONObject resBodyJson, JSONObject offResJson) {
		resBodyJson.remove(JSON_PROP_BUSSRULEINTAKE);
		JSONArray briResJsonArr = null;
		briResJsonArr = offResJson.getJSONObject(JSON_PROP_RESULT).getJSONObject(JSON_PROP_EXECRESULTS).getJSONArray(JSON_PROP_RESULTS).getJSONObject(0).
				getJSONArray(JSON_PROP_VALUE).getJSONObject(0).getJSONObject(JSON_PROP_ACT_ROOT).getJSONArray(JSON_PROP_BUSSRULEINTAKE);
		resBodyJson.put(JSON_PROP_BUSSRULEINTAKE, briResJsonArr);
	}
}
