package com.tech.zerodha.kite.jpa.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

import com.tech.zerodha.kite.jpa.configuration.properties.KiteR2dbcTestProperties;
import com.tech.zerodha.kite.jpa.configuration.properties.KiteR2dbcTestProperties.KiteTestSqlProperties;

import io.r2dbc.spi.ConnectionFactory;

@TestConfiguration
@ComponentScan(basePackages = { "com.tech.zerodha.kite.jpa" })
public class R2dbcTestConfiguration {

	@Bean
	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory,
			KiteR2dbcTestProperties kiteR2dbcTestProperties) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		// This will create our database table and schema
		KiteTestSqlProperties kiteTestSqlProperties = kiteR2dbcTestProperties.getSqlProperties();
		kiteTestSqlProperties.initTestSql(initializer, connectionFactory.getMetadata().getName());
		// This will drop our table after we are done so we can have a fresh start next
		// run
		// initializer.setDatabaseCleaner(new ResourceDatabasePopulator(new
		// ClassPathResource("cleanup.sql")));
		return initializer;
	}

}
