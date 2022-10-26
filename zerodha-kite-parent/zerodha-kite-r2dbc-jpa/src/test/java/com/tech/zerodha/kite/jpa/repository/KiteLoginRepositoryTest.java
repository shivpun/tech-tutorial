package com.tech.zerodha.kite.jpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;

import com.tech.zerodha.kite.jpa.configuration.R2dbcTestConfiguration;
import com.tech.zerodha.kite.jpa.configuration.SqlScriptsR2dbcTestExecutionListener;
import com.tech.zerodha.kite.jpa.model.KiteUserLogin;

import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles(value = "test")
@Import(value = { R2dbcTestConfiguration.class })
public class KiteLoginRepositoryTest {

	@Autowired
	private KiteLoginRepository kiteLoginRepository;

	@Tags(value = { @Tag(value = "r2dbc"), @Tag(value = "save"), @Tag(value = "findAll") })
	@DisplayName(value = "KiteLoginRepository to test save and findAll")
	@Test
	public void create() {
		KiteUserLogin kiteTestLogin = new KiteUserLogin();
		kiteTestLogin.setNickName("TEST-001");
		kiteTestLogin.setUserName("Test".getBytes());
		kiteTestLogin.setPassword("password".getBytes());
		kiteTestLogin.setPin("123".getBytes());

		kiteLoginRepository.save(kiteTestLogin).as(StepVerifier::create)
				.expectNextMatches(kiteUserLogin -> kiteUserLogin.equals(kiteTestLogin)).verifyComplete();

		kiteLoginRepository.findAll().as(StepVerifier::create).expectNextCount(1).verifyComplete();
	}

	@Tags(value = { @Tag(value = "r2dbc"), @Tag(value = "save"), @Tag(value = "findByActiveTrue") })
	@DisplayName(value = "KiteLoginRepository to test save and findAll")
	@Test
	public void test_findByActiveTrue() {
		KiteUserLogin kiteTestLogin = new KiteUserLogin();
		kiteTestLogin.setNickName("TEST-001");
		kiteTestLogin.setUserName("Test".getBytes());
		kiteTestLogin.setPassword("password".getBytes());
		kiteTestLogin.setPin("123".getBytes());
		kiteTestLogin.setActive(true);

		kiteLoginRepository.save(kiteTestLogin).as(StepVerifier::create)
				.expectNextMatches(kiteUserLogin -> kiteUserLogin.equals(kiteTestLogin)).verifyComplete();

		kiteLoginRepository.findByActiveTrue().as(StepVerifier::create)
				.expectNextMatches(kiteUserLogin -> Boolean.TRUE.equals(kiteUserLogin.isActive()))
				.verifyComplete();
	}
}
