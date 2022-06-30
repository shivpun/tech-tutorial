package com.tech.zerodha.kite.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(name = "SB_KITE_LOGIN_HIST_TBL")
public class KiteLoginHistory implements KiteId, Serializable {
	
	private static final long serialVersionUID = 7158893373325027943L;

	@Id
	@Column(value = "SB_KITE_HIST_ID")
	private String id;
	
	@Column(value = "SB_KITE_USER_NAME")
	private String userName;
	
	@Column(value = "SB_KITE_PUBLIC_TOKEN")
	private String publicToken;
	
	@Column(value = "SB_KITE_ACTIVE")
	private boolean active;
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KiteLoginHistory)) {
			return false;
		}
		KiteLoginHistory other = (KiteLoginHistory) obj;
		return Objects.equals(userName, other.userName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName);
	}
}
