package com.tech.zerodha.kite.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KiteTwoFactorAuthenticatorResponse implements Serializable {

	private static final long serialVersionUID = -2802943709134698010L;
	
	@JsonProperty(value = "request_id")
	private String requestId;
	
	@JsonProperty(value = "twofa_type")
	private String twofaType;
	
	@JsonProperty(value = "twofa_status")
	private String twofaStatus;
	
	@JsonProperty(value = "user_id")
	private String userId;
}
