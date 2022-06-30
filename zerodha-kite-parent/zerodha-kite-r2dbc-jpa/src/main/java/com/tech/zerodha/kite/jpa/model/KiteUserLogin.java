package com.tech.zerodha.kite.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(name = "SB_KITE_USER_LOGIN_TBL")
public class KiteUserLogin implements KiteId, Serializable {

	private static final long serialVersionUID = 3294900426877139212L;

	@Id
	@Column(value = "SB_KITE_USER_ID")
	private String id;

	@Column(value = "SB_KITE_NICK_NAME")
	private String nickName;

	@Column(value = "SB_KITE_USER_NAME")
	private byte[] userName;

	@Column(value = "SB_KITE_PASSWORD")
	private byte[] password;

	@Column(value = "SB_KITE_PIN")
	private byte[] pin;
	
	@Column(value = "SB_KITE_ACTIVE")
	private boolean active;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KiteUserLogin)) {
			return false;
		}
		KiteUserLogin other = (KiteUserLogin) obj;
		return Objects.equals(nickName, other.nickName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nickName);
	}
}
