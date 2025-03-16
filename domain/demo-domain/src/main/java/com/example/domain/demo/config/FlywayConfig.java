package com.example.domain.demo.config;

import static com.example.domain.demo.config.DataSourceConfig.*;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(BeanName.PREFIX + "FlywayConfig")
public class FlywayConfig {

	public static final String FLYWAY_PROPERTIES_BEAN_NAME = BeanName.PREFIX + "FlywayProperties";

	@Bean(FLYWAY_PROPERTIES_BEAN_NAME)
	@ConfigurationProperties("spring.flyway.demo")
	public CustomFlywayProperties customFlywayProperties() {
		return new CustomFlywayProperties();
	}

	@Bean(name = BeanName.PREFIX + "Flyway", initMethod = "migrate")
	@ConditionalOnProperty(name = "spring.flyway.demo.enabled", havingValue = "true")
	public Flyway flyway(@Qualifier(DATA_SOURCE_BEAN_NAME) DataSource dataSource,
		@Qualifier(FLYWAY_PROPERTIES_BEAN_NAME) CustomFlywayProperties flywayProperties) {
		FluentConfiguration configuration = Flyway.configure()
			.dataSource(dataSource)
			.locations(flywayProperties.getLocations())
			.baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
			.outOfOrder(flywayProperties.isOutOfOrder())
			.validateOnMigrate(flywayProperties.isValidateOnMigrate());

		if (flywayProperties.getSchemas() != null && !flywayProperties.getSchemas().isEmpty()) {
			configuration.schemas(flywayProperties.getSchemas().toArray(String[]::new));
		}

		return configuration.load();
	}
}
