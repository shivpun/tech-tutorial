package com.tech.zerodha.kite.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KiteTwoFactorAuthenticatorUserRequest implements Serializable {
	
	private static final long serialVersionUID = -1585422667666761700L;

	@JsonProperty(value = "user_id")
	private String userId;
	
	@JsonProperty(value = "request_id")
	private String requestId;
	
	@JsonProperty(value = "twofa_value")
	private String twofaValue;
	
	@JsonProperty(value = "skip_session")
	private String skipSession;
}
