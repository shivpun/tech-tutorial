package com.tech.zerodha.kite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class ZerodhaSampleController {
	
	@GetMapping(value = "/hello-world")
	public Mono<String> helloWorld() {
		return Mono.just("Hello World");
	}
}
