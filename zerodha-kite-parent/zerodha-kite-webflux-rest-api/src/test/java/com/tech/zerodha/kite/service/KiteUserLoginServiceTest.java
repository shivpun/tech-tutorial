package com.tech.zerodha.kite.service;

import static com.tech.zerodha.kite.utils.WebClientUtils.KITE_USER_LOGIN_RESPONSE_TO_KITE_2_FA_REQUEST;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.tech.zerodha.kite.config.server.KiteApiMockServer;
import com.tech.zerodha.kite.configuration.KiteWebClientConfiguration;
import com.tech.zerodha.kite.core.domain.KiteUserLoginRequest;
import com.tech.zerodha.kite.core.properties.KiteApiProperties;

import lombok.Getter;

@Getter
@ActiveProfiles(value = "test")
@TestPropertySource(locations = { "classpath:application-test.properties" })
@ContextConfiguration(classes = { KiteWebClientConfiguration.class, KiteUserLoginService.class })
public class KiteUserLoginServiceTest implements KiteApiMockServer {

	private MockServerClient mockServerClient;

	@Autowired
	private KiteApiProperties kiteApiProperties;

	@Autowired
	private KiteUserLoginService kiteUserLoginService;

	@Test
	public void test_LoginService() {
		KiteUserLoginRequest requestUser = new KiteUserLoginRequest();
		requestUser.setPassword("nilam@1966");
		requestUser.setUserId("DBW346");
		kiteUserLoginService.executeLogin(requestUser)
				.map(response -> KITE_USER_LOGIN_RESPONSE_TO_KITE_2_FA_REQUEST.apply("123456", response))
				.map(request -> kiteUserLoginService.executeKiteTwoFactorAuthenticator(request)).block();
	}
}
