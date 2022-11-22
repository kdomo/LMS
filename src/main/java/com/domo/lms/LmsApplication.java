package com.domo.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LmsApplication {
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "classpath:aws.yml";
	public static void main(String[] args) {
		new SpringApplicationBuilder(LmsApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);

//		SpringApplication.run(LmsApplication.class, args);
	}

}
