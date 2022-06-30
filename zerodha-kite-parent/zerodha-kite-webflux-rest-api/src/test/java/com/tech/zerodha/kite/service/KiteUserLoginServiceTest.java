package com.tech.zerodha.kite.service;

import static com.tech.zerodha.kite.utils.WebClientUtils.KITE_USER_LOGIN_RESPONSE_TO_KITE_2_FA_REQUEST;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.tech.zerodha.kite.KiteRestApplicationTest;
import com.tech.zerodha.kite.core.domain.KiteUserLoginRequest;

@SpringBootTest(classes = { KiteRestApplicationTest.class })
@ActiveProfiles(value = "test")
public class KiteUserLoginServiceTest {

	@Autowired
	private KiteUserLoginService kiteUserLoginService;

	//@Test
	public void test_LoginService() {
		KiteUserLoginRequest requestUser = new KiteUserLoginRequest();
		requestUser.setPassword("nilam@1966");
		requestUser.setUserId("DBW346");
		//kiteUserLoginService.executeLogin(requestUser).map(response -> KITE_USER_LOGIN_RESPONSE_TO_KITE_2_FA_REQUEST.apply("123456", response)).map(request -> kiteUserLoginService.executeKiteTwoFactorAuthenticator(request)).block();
	}
}
