package com.tech.zerodha.kite.utils;

import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.HEADER_USER_AGENT;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.HEADER_X_KITE_USER_ID;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.HEADER_X_KITE_VERSION;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_FORM_PASSWORD;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_FORM_USER_ID;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_FORM_REQUEST_ID;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_FORM_TWOFA_VALUE;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.BodyInserters.FormInserter;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;

import com.tech.zerodha.kite.core.domain.KiteTwoFactorAuthenticatorResponse;
import com.tech.zerodha.kite.core.domain.KiteTwoFactorAuthenticatorUserRequest;
import com.tech.zerodha.kite.core.domain.KiteUserLoginRequest;
import com.tech.zerodha.kite.core.domain.KiteUserLoginResponse;
import com.tech.zerodha.kite.core.properties.KiteApiProperties;

import reactor.core.publisher.Mono;

public final class WebClientUtils {
	
	public static Function<ClientResponse, Mono<KiteUserLoginResponse>> KITE_USER_LOGIN_RESPONSE = (clientResponse) -> clientResponse.bodyToMono(KiteUserLoginResponse.class);
	
	public static BiFunction<String, KiteUserLoginResponse, KiteTwoFactorAuthenticatorUserRequest> KITE_USER_LOGIN_RESPONSE_TO_KITE_2_FA_REQUEST = (twoFactorAuthenticatorValue,kiteUserLoginResponse) -> convertKiteUserLoginResponseToKiteTwoFactorAuthenticatorUserRequest(kiteUserLoginResponse, twoFactorAuthenticatorValue);

	public static RequestBodySpec composeUserApiHeader(RequestBodySpec requestBodySpec, KiteApiProperties kiteApiProperties, String userId) {
		 return requestBodySpec.header(HEADER_X_KITE_USER_ID, userId)
				 			   .header(HEADER_USER_AGENT, kiteApiProperties.getWebUserAgentValue())
				 			   .header(HEADER_X_KITE_VERSION, kiteApiProperties.getWebVersionValue());
	}
	
	public static FormInserter<String> loginBodyInserter(KiteUserLoginRequest kiteLoginUserRequest) {
		return BodyInserters.fromFormData(KITE_FORM_USER_ID, kiteLoginUserRequest.getUserId())
	 						.with(KITE_FORM_PASSWORD, kiteLoginUserRequest.getPassword());
	}
	
	public static FormInserter<String> twoFABodyInserter(KiteTwoFactorAuthenticatorUserRequest kiteTwoFactorAuthenticatorUserRequest) {
		return BodyInserters.fromFormData(KITE_FORM_USER_ID, kiteTwoFactorAuthenticatorUserRequest.getUserId())
	 						.with(KITE_FORM_REQUEST_ID, kiteTwoFactorAuthenticatorUserRequest.getRequestId())
	 						.with(KITE_FORM_TWOFA_VALUE, kiteTwoFactorAuthenticatorUserRequest.getTwofaValue());
	}
	
	private static KiteTwoFactorAuthenticatorUserRequest convertKiteUserLoginResponseToKiteTwoFactorAuthenticatorUserRequest(KiteUserLoginResponse kiteUserLoginResponse, String twoFactorAuthenticatorValue) {
		Assert.notNull(twoFactorAuthenticatorValue, "Two Factor Authenticator value must not be Null");
		Assert.hasLength(twoFactorAuthenticatorValue, "Two Factor Authenticator value must not be Empty");
		
		KiteTwoFactorAuthenticatorResponse kiteTwoFactorAuthenticatorResponse = kiteUserLoginResponse.getData();
		Assert.notNull(kiteTwoFactorAuthenticatorResponse, "Two Factor Authenticator response must not be Null");
		KiteTwoFactorAuthenticatorUserRequest kiteTwoFactorAuthenticatorUserRequest = new KiteTwoFactorAuthenticatorUserRequest();
		kiteTwoFactorAuthenticatorUserRequest.setRequestId(kiteTwoFactorAuthenticatorResponse.getRequestId());
		kiteTwoFactorAuthenticatorUserRequest.setTwofaValue(twoFactorAuthenticatorValue);
		kiteTwoFactorAuthenticatorUserRequest.setUserId(kiteTwoFactorAuthenticatorResponse.getUserId());
		
		return kiteTwoFactorAuthenticatorUserRequest;
	}
}
