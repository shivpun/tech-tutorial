package com.tech.zerodha.kite.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.tech.zerodha.kite.jpa.configuration.R2dbcTestConfiguration;
import com.tech.zerodha.kite.jpa.model.KiteUserLogin;

import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles(value = "test")
@Import(value = { R2dbcTestConfiguration.class })
public class KiteLoginRepositoryTest {

	@Autowired
	private KiteLoginRepository kiteLoginRepository;

	@Test
	public void create() {
		KiteUserLogin kiteTestLogin = new KiteUserLogin();
		kiteTestLogin.setNickName("TEST-001");
		kiteTestLogin.setUserName("Test".getBytes());
		kiteTestLogin.setPassword("password".getBytes());
		kiteTestLogin.setPin("123".getBytes());

		kiteLoginRepository.save(kiteTestLogin).as(StepVerifier::create)
				.expectNextCount(1)
				//.expectNextMatches(kiteUserLogin -> kiteUserLogin.equals(kiteTestLogin))
		.verifyComplete();
	}
}
