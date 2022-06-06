package com.tech.zerodha.kite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;

import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class KiteRestApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(KiteRestApplication.class);
		application.run(args);
	}
	
	@Bean
	public ClientHttpConnector customHttpClient() {
		return new ReactorClientHttpConnector(HttpClient.create().wiretap(true).protocol(HttpProtocol.H2));
	}
}
