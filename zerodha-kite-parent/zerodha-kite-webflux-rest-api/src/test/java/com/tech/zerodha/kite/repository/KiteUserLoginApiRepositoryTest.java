package com.tech.zerodha.kite.repository;

import static com.tech.zerodha.kite.utils.WebClientUtils.KITE_USER_LOGIN_RESPONSE_TO_KITE_2_FA_REQUEST;

import java.util.List;

import static com.tech.zerodha.kite.test.data.KiteApiTestConstants.TEST_APPLICATION_PROPERTIES;
import static com.tech.zerodha.kite.test.data.KiteApiTestConstants.TEST_PROFILE;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.tech.zerodha.kite.config.server.KiteApiMockServer;
import com.tech.zerodha.kite.configuration.KiteWebClientConfiguration;
import com.tech.zerodha.kite.core.domain.KiteTwoFactorAuthenticatorUserRequest;
import com.tech.zerodha.kite.core.domain.KiteUserLoginRequest;
import com.tech.zerodha.kite.core.properties.KiteApiProperties;
import com.tech.zerodha.kite.jpa.model.KiteLoginHistory;

import lombok.Getter;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Getter
//@ActiveProfiles(value = TEST_PROFILE)
//@TestPropertySource(locations = { TEST_APPLICATION_PROPERTIES })
@ContextConfiguration(classes = { KiteWebClientConfiguration.class, KiteUserLoginApiRepository.class })
public class KiteUserLoginApiRepositoryTest implements KiteApiMockServer {

	private MockServerClient mockServerClient;

	@Autowired
	private KiteApiProperties kiteApiProperties;

	@Autowired
	private KiteUserLoginApiRepository kiteUserLoginApiRepository;

	@Test
	public void test_LoginService() {
		KiteUserLoginRequest requestUser = new KiteUserLoginRequest();
		requestUser.setPassword("nilam@1966");
		requestUser.setUserId("DBW346");
		Mono<KiteLoginHistory> kiteLoginHistoryResponse = this.kiteUserLoginApiRepository.executeLogin(requestUser)
				.map(response -> KITE_USER_LOGIN_RESPONSE_TO_KITE_2_FA_REQUEST.apply("123456", response))
				.flatMap(request -> kiteUserLoginApiRepository.executeKiteTwoFactorAuthenticator(request));
		StepVerifier.create(kiteLoginHistoryResponse).expectNextCount(1).verifyComplete();
	}
}
