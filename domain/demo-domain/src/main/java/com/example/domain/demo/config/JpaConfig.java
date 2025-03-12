package com.example.domain.demo.config;

import static com.example.domain.demo.config.JpaConfig.*;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration(BeanName.PREFIX + "JpaConfig")
@EnableJpaRepositories(basePackages = "com.example.domain.demo", entityManagerFactoryRef = ENTITY_MANAGER_FACTORY_BEAN_NAME, transactionManagerRef = TRANSACTION_MANAGER_BEAN_NAME)
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig {

	public static final String DATA_SOURCE_PROPERTIES_BEAN_NAME = BeanName.PREFIX + "DataSourceProperties";
	public static final String DATA_SOURCE_BEAN_NAME = BeanName.PREFIX + "DataSource";
	public static final String JPA_PROPERTIES_BEAN_NAME = BeanName.PREFIX + "JpaProperties";
	public static final String JPA_VENDOR_ADAPTER = BeanName.PREFIX + "JpaVendorAdapter";
	public static final String ENTITY_MANAGER_FACTORY_BEAN_NAME = BeanName.PREFIX + "EntityManagerFactory";
	public static final String PERSISTENCE_UNIT_NAME = BeanName.PREFIX + "PersistenceUnit";
	public static final String TRANSACTION_MANAGER_BEAN_NAME = BeanName.PREFIX + "TransactionManager";

	@Bean(DATA_SOURCE_PROPERTIES_BEAN_NAME)
	@ConfigurationProperties("spring.datasource.demo")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(DATA_SOURCE_BEAN_NAME)
	public DataSource dataSource() {
		return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}

	@Bean(JPA_PROPERTIES_BEAN_NAME)
	@ConfigurationProperties("spring.jpa.demo")
	public CustomJpaProperties jpaProperties() {
		return new CustomJpaProperties();
	}

	@Bean(JPA_VENDOR_ADAPTER)
	@ConfigurationProperties("spring.jpa.demo")
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean(ENTITY_MANAGER_FACTORY_BEAN_NAME)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setJpaVendorAdapter(jpaVendorAdapter());
		em.setJpaPropertyMap(jpaProperties().getProperties());
		em.setPackagesToScan("com.example.domain.demo");
		em.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		return em;
	}

	@Bean(TRANSACTION_MANAGER_BEAN_NAME)
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}

}
