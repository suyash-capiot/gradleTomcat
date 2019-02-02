package com.orchestration.orchestratorengine.service.activities;

import java.text.SimpleDateFormat;

import com.orchestration.orchestratorengine.utils.Constants;

public interface ActivitiesConstants extends Constants {
	
	public static final String PRODUCT = "ACTIVITIES";
	
	public static final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd'T00:00:00'");

	public static final String JSON_PROP_TYPE = "type";
	public static final String JSON_PROP_COMMANDS = "commands";
	public static final String JSON_PROP_INSERT = "insert";
	public static final String JSON_PROP_OBJECT = "object";
	public static final String JSON_PROP_ACT_ROOT = "cnk.activity_supplieroffers.Root";
	public static final String JSON_PROP_COMMELEM = "commonElements";
	
	public static final String JSON_PROP_RESULT = "result";
	public static final String JSON_PROP_RESULTS = "results";
	public static final String JSON_PROP_EXECRESULTS = "execution-results";
	public static final String JSON_PROP_VALUE = "value";
	
	public static final String JSON_PROP_PROD_CAT_SUBTYPE = "productCategorySubType";
	public static final String JSON_PROP_SUPPNAME = "supplierName";
	public static final String JSON_PROP_SUPPMARKET = "supplierMarket";
	public static final String JSON_PROP_SUPPRATETYPE = "supplierRateType";
	public static final String JSON_PROP_SUPPRATECODE = "supplierRateCode";
	
}
