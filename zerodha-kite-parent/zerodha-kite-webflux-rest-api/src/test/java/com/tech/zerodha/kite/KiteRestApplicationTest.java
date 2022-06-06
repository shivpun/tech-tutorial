package com.tech.zerodha.kite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KiteRestApplicationTest {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(KiteRestApplicationTest.class);
		application.run(args);
	}
}
