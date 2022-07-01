package com.tech.zerodha.kite.core.properties;

import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.API_KITE_OMS_URL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.API_KITE_URL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_API_KEY_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_API_KEY_VALUE;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_ENCTOKEN_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_PUBLIC_TOKEN;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_SESSION_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_UID_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_USER_AGENT_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_USER_AGENT_VALUE;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_WEB_USER_AGENT;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_USER_ID_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_VERSION_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_VERSION_VALUE;

import java.util.Date;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.kite.api.websocket")
public class KiteApiProperties {

	private String userIdValue;

	private String kiteBaseUrl = API_KITE_URL;

	private String kiteOmsBaseUrl = API_KITE_OMS_URL;

	private String userIdLabel = KITE_USER_ID_LABEL;

	private String sessionLabel = KITE_SESSION_LABEL;

	private String publicTokenLabel = KITE_PUBLIC_TOKEN;

	private String webVersionValue = KITE_VERSION_VALUE;

	private String webVersionLabel = KITE_VERSION_LABEL;

	private String userAgentLabel = KITE_USER_AGENT_LABEL;

	private String userAgentValue = KITE_USER_AGENT_VALUE;
	
	private String webUserAgentValue = KITE_WEB_USER_AGENT;

	private String uidLabel = KITE_UID_LABEL;

	private String enctokenLabel = KITE_ENCTOKEN_LABEL;

	private String webApiKeyLabel = KITE_API_KEY_LABEL;

	private String webApiKeyValue = KITE_API_KEY_VALUE;
	
	public long getUidValue() {
		return new Date().getTime();
	}
}
