package com.tech.zerodha.kite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.tech.zerodha.kite", "com.tech.zerodha.kite.core.properties" })
public class KiteRestApplicationTest {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(KiteRestApplicationTest.class);
		application.run(args);
	}
}
