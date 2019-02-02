package com.orchestration.orchestratorengine.utils;

import java.text.SimpleDateFormat;

public interface Constants {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T00:00:00'");
	
	public static final String BRMS_STATUS_TYPE_FAILURE = "FAILURE";
	public static final String BRMS_STATUS_TYPE_SUCCESS = "SUCCESS";
	
	public static final String CONFIG_PROP_TYPE = "type";
	public static final String CONFIG_PROP_REQ_JSON_SHELL = "reqJSONShell";
	public static final String CONFIG_PROP_SERVICE_URL = "serviceURL";
	
	public static final String JSON_PROP_REQHEADER = "requestHeader";
	public static final String JSON_PROP_RESHEADER = "responseHeader";
	public static final String JSON_PROP_CLIENTCONTEXT = "clientContext";
	public static final String JSON_PROP_CLIENTCALLBACK = "clientCallbackAddress";
	public static final String JSON_PROP_REQBODY = "requestBody";
	public static final String JSON_PROP_RESBODY = "responseBody";
	public static final String JSON_PROP_BUSSRULEINTAKE = "businessRuleIntake";
	
	// General application constants
	public static final String PROP_USER_ID = "userID";
	public static final String PROP_PASSWORD = "password";
	
	public static final String HTTP_AUTH_BASIC_PREFIX = "Basic ";
	public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
	public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
	public static final String HTTP_CONTENT_TYPE_APP_JSON = "application/json";
}
