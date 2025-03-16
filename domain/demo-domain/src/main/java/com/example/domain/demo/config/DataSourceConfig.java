package com.example.domain.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration(BeanName.PREFIX + "DataSourceConfig")
public class DataSourceConfig {

	public static final String DATA_SOURCE_PROPERTIES_BEAN_NAME = BeanName.PREFIX + "DataSourceProperties";
	public static final String DATA_SOURCE_BEAN_NAME = BeanName.PREFIX + "DataSource";

	@Bean(DATA_SOURCE_PROPERTIES_BEAN_NAME)
	@ConfigurationProperties("spring.datasource.demo")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(DATA_SOURCE_BEAN_NAME)
	public DataSource dataSource() {
		return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
}
