package com.orchestration.orchestratorengine.config.activities;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.orchestration.orchestratorengine.config.CommercialTypeConfig;
import com.orchestration.orchestratorengine.config.OffersConfig;
import com.orchestration.orchestratorengine.config.mongo.MongoProductConfig;
import com.orchestration.orchestratorengine.service.activities.ActivitiesConstants;

public class ActivitiesConfig {
	private static OffersConfig mOffConfig;
	private static Map<String, String> mHttpHeaders = new HashMap<String, String>();

	public static void loadConfig() {

		Document configDoc = MongoProductConfig.getConfig(ActivitiesConstants.PRODUCT);
		mOffConfig = new OffersConfig((Document) configDoc.get("offers"));
	}
	
	public static OffersConfig getOffersConfig() {
		return mOffConfig;
	}
	
	public static CommercialTypeConfig getOffersTypeConfig(OffersConfig.Type offType) {
		return mOffConfig.getOfferTypeConfig(offType);
	}
	
	public static Map<String, String> getHttpHeaders() {
		return mHttpHeaders;
	}
}
