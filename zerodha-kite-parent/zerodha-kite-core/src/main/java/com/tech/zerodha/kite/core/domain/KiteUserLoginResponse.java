package com.tech.zerodha.kite.core.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class KiteUserLoginResponse implements Serializable {

	private static final long serialVersionUID = -5631454637251401808L;

	private String status;
	
	private KiteTwoFactorAuthenticatorResponse data;
}
