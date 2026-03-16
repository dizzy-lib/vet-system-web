package com.vet.vetweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.vet.vetweb"})
public class VetwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(VetwebApplication.class, args);
	}

}
