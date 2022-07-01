package com.tech.zerodha.kite.configuration;

import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_COOKIE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.tech.zerodha.kite.core.properties.KiteApiProperties;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(KiteApiProperties.class)
public class KiteWebClientConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(KiteWebClientConfiguration.class);

	private final KiteApiProperties kiteApiProperties;

	@Bean(value = { "non-blocking-web-api-client" })
	public WebClient webApiClient() {
		WebClient webClient = WebClient.builder().baseUrl(kiteApiProperties.getKiteBaseUrl()).filter(logFilter())
				.build();
		return webClient;
	}

	private ExchangeFilterFunction logFilter() {
		return (clientRequest, next) -> {
			LOGGER.info("URL [{}] has cookies [{}]", clientRequest.url(), clientRequest.headers().get(KITE_COOKIE));
			return next.exchange(clientRequest);
		};
	}
}
