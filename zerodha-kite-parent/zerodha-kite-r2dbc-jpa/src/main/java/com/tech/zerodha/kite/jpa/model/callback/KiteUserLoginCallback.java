package com.tech.zerodha.kite.jpa.model.callback;

import java.util.UUID;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;

import com.tech.zerodha.kite.jpa.model.KiteUserLogin;

import reactor.core.publisher.Mono;

@Configuration
public class KiteUserLoginCallback {

	@Bean(value = "kite-user-login-before-convert-callback")
	public BeforeSaveCallback<KiteUserLogin> kiteUserLoginBeforeConvertCallback() {
		return new BeforeSaveCallback<KiteUserLogin>() {
			
			@Override
			public Publisher<KiteUserLogin> onBeforeSave(KiteUserLogin kiteUserLoginEntity, OutboundRow row, SqlIdentifier table) {
				if (kiteUserLoginEntity.getId() == null) {
					kiteUserLoginEntity.setId(UUID.randomUUID().toString());
				}
				return Mono.just(kiteUserLoginEntity);
			}
		};
	}
}
