package com.example.api.demo.common;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import com.example.domain.demo.test.DemoDomainDatabaseCleaner;

import io.restassured.RestAssured;

// import static org.assertj.core.api.Assertions.*;
// import static org.mockito.Mockito.*;
// import static io.restassured.RestAssured.*;
// import static io.restassured.matcher.RestAssuredMatchers.*;
// import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerIntegrationTest {

	@LocalServerPort
	protected int port;

	@Autowired
	private DemoDomainDatabaseCleaner demoDomainDatabaseCleaner;

	@BeforeEach
	protected void beforeEach() {
		RestAssured.port = port;
		this.demoDomainDatabaseCleaner.clean();

	}

	@AfterEach
	protected void afterEach() {
		this.demoDomainDatabaseCleaner.clean();
	}

}
