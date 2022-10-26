package com.tech.zerodha.kite.jpa.configuration;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import io.r2dbc.spi.ConnectionFactory;

public class SqlScriptsR2dbcTestExecutionListener implements TestExecutionListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlScriptsR2dbcTestExecutionListener.class);

	public void beforeTestMethod(TestContext testContext) throws Exception {
		executeSqlScripts(testContext, BEFORE_TEST_METHOD);
	}
	
	public void afterTestMethod(TestContext testContext) throws Exception {
		executeSqlScripts(testContext, AFTER_TEST_METHOD);
	}

	/**
	 * Execute SQL scripts configured via {@link Sql @Sql} for the supplied
	 * {@link TestContext} and {@link ExecutionPhase}.
	 */
	private void executeSqlScripts(TestContext testContext, ExecutionPhase executionPhase) throws Exception {
		boolean classLevel = false;
		Set<Sql> sqlAnnotations = AnnotatedElementUtils.getMergedRepeatableAnnotations(testContext.getTestMethod(),
				Sql.class, SqlGroup.class);
		if (sqlAnnotations.isEmpty()) {
			sqlAnnotations = AnnotatedElementUtils.getMergedRepeatableAnnotations(testContext.getTestClass(), Sql.class,
					SqlGroup.class);
			if (!sqlAnnotations.isEmpty()) {
				classLevel = true;
			}
		}

		for (Sql sql : sqlAnnotations) {
			executeSqlScripts(sql, executionPhase, testContext, classLevel);
		}
	}

	private void executeSqlScripts(Sql sql, ExecutionPhase executionPhase, TestContext testContext,
			boolean classLevel) {
		if (sql == null || sql.scripts() == null || sql.scripts().length < 1) {
			return ;
		}
		ApplicationContext applicationContext = testContext.getApplicationContext();
		ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);
		List<FileSystemResource> resources = Arrays.asList(sql.scripts())
										 .stream()
										 .peek(path -> LOGGER.info("Script path: {}", path))
										 .map(scriptPath -> new FileSystemResource(scriptPath))
										 .toList();
		Resource []scriptResources = resources.toArray(new Resource[resources.size()]);
		createConnectionFactoryInitializer(connectionFactory, scriptResources);
	}
	
	private ConnectionFactoryInitializer createConnectionFactoryInitializer(ConnectionFactory connectionFactory, Resource ...resources) {
		ConnectionFactoryInitializer connectionFactoryInitializer = new ConnectionFactoryInitializer();
		connectionFactoryInitializer.setConnectionFactory(connectionFactory);
		connectionFactoryInitializer.setDatabasePopulator(new ResourceDatabasePopulator(resources));
		connectionFactoryInitializer.afterPropertiesSet();
		return connectionFactoryInitializer;
	}
}
