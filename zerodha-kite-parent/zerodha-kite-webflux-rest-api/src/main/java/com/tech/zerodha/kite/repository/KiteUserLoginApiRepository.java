package com.tech.zerodha.kite.repository;

import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_COOKIE;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_LOGIN_ENDPOINT;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_TWO_FACTOR_AUTHENTICATOR_ENDPOINT;
import static com.tech.zerodha.kite.utils.WebClientUtils.KITE_USER_LOGIN_RESPONSE;
import static com.tech.zerodha.kite.utils.WebClientUtils.composeUserApiHeader;
import static com.tech.zerodha.kite.utils.WebClientUtils.loginBodyInserter;
import static com.tech.zerodha.kite.utils.WebClientUtils.twoFABodyInserter;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;

import com.tech.zerodha.kite.core.domain.KiteTwoFactorAuthenticatorUserRequest;
import com.tech.zerodha.kite.core.domain.KiteUserLoginRequest;
import com.tech.zerodha.kite.core.domain.KiteUserLoginResponse;
import com.tech.zerodha.kite.core.properties.KiteApiProperties;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Repository(value = "kite-user-login-api-repository")
public class KiteUserLoginApiRepository {

	private final KiteApiProperties kiteApiProperties;

	@Qualifier(value = "non-blocking-web-api-client")
	private final WebClient webApiClient;

	public Mono<KiteUserLoginResponse> executeLogin(KiteUserLoginRequest kiteLoginUser) {
		RequestBodySpec requestBodySpec = webApiClient.post().uri(KITE_LOGIN_ENDPOINT);

		Mono<KiteUserLoginResponse> kiteUserLoginResponseMono = composeUserApiHeader(requestBodySpec, kiteApiProperties,
				kiteLoginUser.getUserId()).body(loginBodyInserter(kiteLoginUser))
				.exchangeToMono(KITE_USER_LOGIN_RESPONSE);

		return kiteUserLoginResponseMono;
	}

	public Mono<String> executeKiteTwoFactorAuthenticator(
			KiteTwoFactorAuthenticatorUserRequest kiteTwoFactorAuthenticatorUserRequest) {
		RequestBodySpec requestBodySpec = webApiClient.post().uri(KITE_TWO_FACTOR_AUTHENTICATOR_ENDPOINT);

		Mono<String> kiteUserLoginResponseMono = composeUserApiHeader(requestBodySpec, kiteApiProperties,
				kiteTwoFactorAuthenticatorUserRequest.getUserId())
				.body(twoFABodyInserter(kiteTwoFactorAuthenticatorUserRequest)).exchangeToMono(clientResponse -> {
					List<String> headers = clientResponse.headers().header(KITE_COOKIE);
					System.out.println(headers);
					return null;
				});

		return kiteUserLoginResponseMono;
	}
}
