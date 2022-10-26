package com.tech.zerodha.kite.utils;

import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.COOKIE_KF_SESSION;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.COOKIE_PUBLIC_TOKEN;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.COOKIE_USER_ID;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.COOKIE_ENC_TOKEN;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.HEADER_USER_AGENT;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.HEADER_X_KITE_USER_ID;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.HEADER_X_KITE_VERSION;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_SET_COOKIE;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_FORM_PASSWORD;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_FORM_USER_ID;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_FORM_REQUEST_ID;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_FORM_TWOFA_VALUE;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.tech.zerodha.kite.jpa.model.KiteLoginHistory;

import reactor.core.publisher.Mono;

public final class WebClientUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebClientUtils.class);
	
	public static Function<ClientResponse, Mono<KiteUserLoginResponse>> KITE_USER_LOGIN_RESPONSE = (clientResponse) -> clientResponse.bodyToMono(KiteUserLoginResponse.class);
	
	public static BiFunction<String, KiteUserLoginResponse, KiteTwoFactorAuthenticatorUserRequest> KITE_USER_LOGIN_RESPONSE_TO_KITE_2_FA_REQUEST = (twoFactorAuthenticatorValue,kiteUserLoginResponse) -> convertKiteUserLoginResponseToKiteTwoFactorAuthenticatorUserRequest(kiteUserLoginResponse, twoFactorAuthenticatorValue);
	
	public static Function<ClientResponse, Mono<KiteLoginHistory>> API_KITE_LOGIN_HISTORY = (clientResponse) -> Mono.justOrEmpty(extractSessionFromCookie(clientResponse));

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
	
	public static KiteLoginHistory kiteLoginHistory(KiteLoginHistory kiteLoginHistory, String jsonString) {
		Assert.notNull(jsonString, "Json string must not be null");
		Assert.hasLength(jsonString, "Json string must have length");
		String strArray[] = jsonString.split(";")[0].split("=");
		Assert.isTrue(strArray.length < 2, "Must have length 2 or more but not less");
		switch (strArray[0]) {
			case COOKIE_KF_SESSION:
				kiteLoginHistory.setKfSession(strArray[1]);
				break;
			case COOKIE_ENC_TOKEN:
				kiteLoginHistory.setEncToken(strArray[1]);
				break;
			case COOKIE_PUBLIC_TOKEN:
				kiteLoginHistory.setPublicToken(strArray[1]);
				break;
			case COOKIE_USER_ID:
				kiteLoginHistory.setUserName(strArray[1]);
				break;
		}
		return kiteLoginHistory;
	}
	
	private static KiteLoginHistory extractSessionFromCookie(ClientResponse clientResponse) {
		List<String> headers = clientResponse.headers().header(KITE_SET_COOKIE);
		Assert.notEmpty(headers, "Header must not be empty");
		KiteLoginHistory kiteLoginHistory = new KiteLoginHistory();
		kiteLoginHistory.setActive(true);
		for (String header: headers) {
			kiteLoginHistory(kiteLoginHistory, header);
		}
		Assert.notNull(kiteLoginHistory.getUserName(), "User name must not be null");
		LOGGER.info("User Id [{}] has been login successfully !!", kiteLoginHistory.getUserName());
		return kiteLoginHistory;
	}
}
