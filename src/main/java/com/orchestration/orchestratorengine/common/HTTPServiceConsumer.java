package com.orchestration.orchestratorengine.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HTTPServiceConsumer {
	private static final Logger logger = LogManager.getLogger(HTTPServiceConsumer.class);

	public static JSONObject consumeJSONService(String tgtSysId, URL tgtSysURL, Map<String, String> httpHdrs, JSONObject reqJson) throws Exception {
		HttpURLConnection svcConn = null;
		try {
			svcConn = (HttpURLConnection) tgtSysURL.openConnection();
			String reqJsonStr = reqJson.toString(); 
			if (logger.isInfoEnabled()) {
				logger.info(String.format("%s_RQ = %s", tgtSysId, reqJsonStr));
			}
			
			InputStream httpResStream = consumeService(tgtSysId, svcConn, httpHdrs, reqJsonStr.getBytes());
			if (httpResStream != null) {
				//return new JSONObject(new JSONTokener(httpResStream));
				JSONObject resJson = new JSONObject(new JSONTokener(httpResStream));
				if (logger.isInfoEnabled()) {
					logger.info(String.format("%s_RS = %s", tgtSysId, resJson.toString()));
				}
				return resJson;
			}
		}
		catch (Exception x) {
			logger.warn(String.format("%s_ERR JSON Service <%s> Consume Error", tgtSysId, tgtSysURL), x);
		}
		finally {
			if (svcConn != null) {
				svcConn.disconnect();
			}
		}
		
		return null;
	}

	private static InputStream consumeService(String tgtSysId, HttpURLConnection svcConn, Map<String, String> httpHdrs, byte[] payload) throws Exception {
		svcConn.setDoOutput(true);
		svcConn.setRequestMethod("POST");
		
		if (httpHdrs != null) {
			Set<Entry<String,String>> httpHeaders = httpHdrs.entrySet();
			if (httpHeaders != null && httpHeaders.size() > 0) {
				Iterator<Entry<String,String>> httpHeadersIter = httpHeaders.iterator();
				while (httpHeadersIter.hasNext()) {
					Entry<String,String> httpHeader = httpHeadersIter.next();
					svcConn.setRequestProperty(httpHeader.getKey(), httpHeader.getValue());
				}
			}
		}
		
		logger.trace(String.format("Sending request to %s",tgtSysId));
		OutputStream httpOut = svcConn.getOutputStream();
		httpOut.write(payload);
		httpOut.flush();
		httpOut.close();

		int resCode = svcConn.getResponseCode();
		logger.debug(String.format("Receiving response from %s with HTTP response status: %s", tgtSysId, resCode));
		
		if (resCode == HttpURLConnection.HTTP_OK || (HttpURLConnection.HTTP_ACCEPTED == resCode && "COMMCACHE".equals(tgtSysId))) {
			return svcConn.getInputStream();
		}
		
		return null;
	}
	
	public static JSONArray produceJSONArrayResponse(String tgtSysId, String urlToRead, Map<String, String> httpHdrs) throws Exception {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlToRead);
			// To Handle space and special Characters in URL
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			url = uri.toURL();
	
			conn = (HttpURLConnection) url.openConnection();
	
			InputStream httpResStream = produceService(tgtSysId, conn, httpHdrs);
			if (httpResStream != null) {
				
				JSONArray resJson = new JSONArray(new JSONTokener(httpResStream));
				if (logger.isInfoEnabled()) {
					logger.info(String.format("%s_RS = %s", tgtSysId, resJson.toString()));
				}
				return resJson;
				
			}
		}
		catch (Exception x) {
			logger.warn(String.format("%s_ERR JSON Service <%s> Consume Error", tgtSysId, urlToRead), x);
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	public static JSONObject produceJSONObjectResponse(String tgtSysId, String urlToRead,  Map<String, String> httpHdrs) throws Exception {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlToRead);
			// To Handle space and special Characters in URL
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			url = uri.toURL();
	
			conn = (HttpURLConnection) url.openConnection();
	
			InputStream httpResStream = produceService(tgtSysId, conn, httpHdrs);
			if (httpResStream != null) {
				
				JSONObject resJson = new JSONObject(new JSONTokener(httpResStream));
				if (logger.isInfoEnabled()) {
					logger.info(String.format("%s_RS = %s", tgtSysId, resJson.toString()));
				}
				return resJson;
				
			}
		}
		catch (Exception x) {
			logger.warn(String.format("%s_ERR JSON Service <%s> Consume Error", tgtSysId, urlToRead), x);
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}
	
	private static InputStream produceService(String tgtSysId, HttpURLConnection svcConn, Map<String, String> httpHdrs)
			throws Exception {

		svcConn.setRequestMethod("GET");

		if (httpHdrs != null) {
			Set<Entry<String, String>> httpHeaders = httpHdrs.entrySet();
			if (httpHeaders != null && httpHeaders.size() > 0) {
				Iterator<Entry<String, String>> httpHeadersIter = httpHeaders.iterator();
				while (httpHeadersIter.hasNext()) {
					Entry<String, String> httpHeader = httpHeadersIter.next();
					svcConn.setRequestProperty(httpHeader.getKey(), httpHeader.getValue());
				}
			}
		}
		
		logger.trace(String.format("Sending request to %s", tgtSysId));
		
		int resCode = svcConn.getResponseCode();
		logger.debug(String.format("Receiving response from %s with HTTP response status: %s", tgtSysId, resCode));
		if (resCode == HttpURLConnection.HTTP_OK) {
			return svcConn.getInputStream();
		}

		return null;
	}
	
}
