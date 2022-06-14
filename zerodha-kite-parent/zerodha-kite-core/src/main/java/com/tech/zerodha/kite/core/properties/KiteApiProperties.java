package com.tech.zerodha.kite.core.properties;

import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_USER_ID_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_API_KEY_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_API_KEY_VALUE;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_USER_AGENT_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_USER_AGENT_VALUE;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_VERSION_LABEL;
import static com.tech.zerodha.kite.core.constants.KiteApiEndpoints.KITE_VERSION_VALUE;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.kite.api.websocket")
public class KiteApiProperties {
	
	private String userIdLabel = KITE_USER_ID_LABEL;
	
	private String userIdValue;
	
	private String webVersionValue  = KITE_VERSION_VALUE;
	
	private String webVersionLabel = KITE_VERSION_LABEL;
	
	private String userAgentLabel = KITE_USER_AGENT_LABEL;
	
	private String userAgentValue = KITE_USER_AGENT_VALUE;
	
	private String webApiKeyLabel = KITE_API_KEY_LABEL;
	
	private String webApiKeyValue = KITE_API_KEY_VALUE;
}
