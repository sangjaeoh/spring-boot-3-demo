package com.example.domain.demo.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.example.domain.demo.config.DataSourceConfig;
import com.example.domain.demo.config.FlywayConfig;
import com.example.domain.demo.config.JpaConfig;
import com.example.domain.demo.config.QuerydslConfig;
import com.example.domain.demo.test.DemoDomainDatabaseCleaner;

// import static org.assertj.core.api.Assertions.*;
// import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {DataSourceConfig.class, JpaConfig.class, QuerydslConfig.class, FlywayConfig.class,
	MethodValidationPostProcessor.class})
@Import({DemoDomainDatabaseCleaner.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class BaseServiceIntegrationTest {

	@Autowired
	private DemoDomainDatabaseCleaner demoDomainDatabaseCleaner;

	@BeforeEach
	protected void beforeSetUp() {
		this.demoDomainDatabaseCleaner.clean();
	}

	@AfterEach
	protected void afterSetup() {
		this.demoDomainDatabaseCleaner.clean();
	}

}