package com.jeeplus.common.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlowableBeanConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(FlowableBeanConfig.class);

	protected static final String LIQUIBASE_CHANGELOG_PREFIX = "ACT_DE_";

	@Bean
	public FlowableModelerAppProperties flowableModelerAppProperties() {
		FlowableModelerAppProperties flowableModelerAppProperties = new FlowableModelerAppProperties();
		return flowableModelerAppProperties;
	}


	@Bean
	public Liquibase liquibase(DataSource dataSource) {
		LOGGER.info("Configuring Liquibase");
		try {
			DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
			Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
			database.setDatabaseChangeLogTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
			database.setDatabaseChangeLogLockTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());

			Liquibase liquibase = new Liquibase("META-INF/liquibase/flowable-modeler-app-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
			liquibase.update("flowable");
			return liquibase;

		} catch (Exception e) {
			throw new RuntimeException("Error creating liquibase database", e);
		}
	}

}
