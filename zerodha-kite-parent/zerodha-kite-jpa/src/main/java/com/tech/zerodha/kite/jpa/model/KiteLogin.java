package com.tech.zerodha.kite.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(name = "SB_KITE_TBL")
public class KiteLogin implements Serializable {
	
	private static final long serialVersionUID = 3294900426877139212L;

	@Id
	@Column(value = "SB_KITE_LOGIN_ID")
	private Long id;
	
	@Column(value = "SB_KITE_USER_NAME")
	private String userName;
	
	@Column(value = "SB_KITE_PASSWORD")
	private String password;
	
	@Column(value = "SB_KITE_PIN")
	private String pin;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KiteLogin)) {
			return false;
		}
		KiteLogin other = (KiteLogin) obj;
		return Objects.equals(userName, other.userName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName);
	}
}
