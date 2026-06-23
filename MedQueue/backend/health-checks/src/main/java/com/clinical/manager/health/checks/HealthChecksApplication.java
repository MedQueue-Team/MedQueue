package com.clinical.manager.health.checks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthChecksApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthChecksApplication.class, args);
		System.out.println("Health Checks Application started successfully!");
	}

}
