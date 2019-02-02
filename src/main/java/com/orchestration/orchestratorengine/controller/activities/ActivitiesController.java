package com.orchestration.orchestratorengine.controller.activities;

import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orchestration.orchestratorengine.service.activities.ActivitiesServices;

@RestController
@RequestMapping("/ActivitiesService/v1")
public class ActivitiesController {

	@GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> pingService(InputStream req) {
		return new ResponseEntity<String>("{\"operation\": \"ActivitiesService ping\", \"status\": \"SUCCESS\"}", HttpStatus.OK);
	}

	@PostMapping(value = "/getActivityOffer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTour(InputStream ip, JSONObject reqJson) throws Exception {
	  
  	  try {
  		  reqJson = new JSONObject(new JSONTokener(ip));
          String res = ActivitiesServices.process(reqJson);
          return new ResponseEntity<String>(res, HttpStatus.OK);
      }
  	  catch (Exception x) {
          return new ResponseEntity<String>("CKIL231556_PKGSSR_ER01", HttpStatus.BAD_REQUEST);
      }
	 
	}
}
