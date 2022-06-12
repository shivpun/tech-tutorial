package com.tech.zerodha.kite.jpa.configuration.properties;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.util.StringUtils;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "kite.r2dbc")
public class KiteR2dbcTestProperties {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KiteR2dbcTestProperties.class);
	
	private final KiteTestSqlProperties sqlProperties = new KiteTestSqlProperties();
	
	@Data
	public static class KiteTestSqlProperties {
		
		private String configSqlPath = "/config/sql";
		
		private String defaultInitDataName = "schema";
		
		public String getKiteTestSqlPath(String dialect) {
			StringBuilder schemaSB = new StringBuilder();
			schemaSB.append(configSqlPath)
					.append("/")
					.append("schema");
			
			if (dialect == null || StringUtils.trimAllWhitespace(dialect).length() > 1) {
				schemaSB.append("-")
						.append(dialect.toLowerCase(Locale.ENGLISH));
			}
			schemaSB.append(".sql");
			return schemaSB.toString();
		}
		
		public void initTestSql(ConnectionFactoryInitializer initializer, String dialect) {
			String configPath = getKiteTestSqlPath(dialect);
			Resource resource = new ClassPathResource(configPath);
			if (resource.exists()) {
				initializer.setDatabasePopulator(new ResourceDatabasePopulator(resource));
			} else {
				LOGGER.info("Initialization database script doesn't exist at location [{}] for dialect {}", configPath, dialect);
			}
		}
	}
}
