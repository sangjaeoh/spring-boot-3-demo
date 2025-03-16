package com.example.api.demo.user.contoller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.api.demo.common.BaseControllerIntegrationTest;
import com.example.api.demo.fixture.RestAssuredTestFixture;
import com.example.api.demo.user.contoller.request.CreateUserRequest;
import com.example.api.demo.user.fixture.UserControllerTestFixture;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class UserControllerTest extends BaseControllerIntegrationTest {

	@Test
	void 유저_조회_성공() {
		// given
		Long id = 1L;

		// when
		ExtractableResponse<Response> response = UserControllerTestFixture.유저_조회_요청(id);

		// then
		RestAssuredTestFixture.요청_응답_성공(response);
		Long getId = response.jsonPath().getLong("data.id");
		assertThat(getId).isNotNull();
	}

	@Test
	void 유저_생성_성공() {
		// given
		String email = "userCreate@email.com";
		String password = "userPassword";
		CreateUserRequest request = CreateUserRequest.builder().email(email).password(password).build();

		// when
		ExtractableResponse<Response> response = UserControllerTestFixture.유저_생성_요청(request);

		// then
		RestAssuredTestFixture.요청_응답_성공(response);
		Long id = response.jsonPath().getLong("data.id");
		assertThat(id).isNotNull();
	}
}