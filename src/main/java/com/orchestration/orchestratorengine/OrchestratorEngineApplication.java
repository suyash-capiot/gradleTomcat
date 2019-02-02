package com.orchestration.orchestratorengine;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.core.config.plugins.util.PluginManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.orchestration.orchestratorengine.config.activities.ActivitiesConfig;
import com.orchestration.orchestratorengine.utils.TrackingContextPatternConverter;

@SpringBootApplication
public class OrchestratorEngineApplication extends SpringBootServletInitializer{
	
	@PostConstruct
	public void init() throws Exception {
		PluginManager.addPackage(TrackingContextPatternConverter.class.getPackage().getName());
		try {
			ActivitiesConfig.loadConfig();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void main(String[] args) {
		try {
			SpringApplication.run(OrchestratorEngineApplication.class, args);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/*@PreDestroy
	public void shutdown() {
		System.out.println("Shutting down application");
	}*/
}

