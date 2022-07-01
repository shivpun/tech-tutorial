package com.tech.zerodha.kite.config.server;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.springtest.MockServerTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tech.zerodha.kite.core.properties.KiteApiProperties;

@MockServerTest
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(value = SpringExtension.class)
public interface KiteApiMockServer {

	@BeforeAll
	default void setUp() throws MalformedURLException {
		URL url = new URL(getKiteApiProperties().getKiteBaseUrl());
		getMockServerClient().bind(url.getPort());
	}

	@AfterAll
	default void stopServer() {
		if (getMockServerClient() != null || !getMockServerClient().hasStopped()) {
			getMockServerClient().stop();
		}
	}

	MockServerClient getMockServerClient();

	KiteApiProperties getKiteApiProperties();
}
