package com.tech.zerodha.kite.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KiteUserLoginRequest implements Serializable {

	private static final long serialVersionUID = -1248561699669372827L;
	
	@JsonProperty(value = "user_id")
	private String userId;
	
	@JsonProperty(value = "password")
	private String password;
}
