package com.example.api.demo.fixture;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredTestFixture {

	public static final String TEST_USER_EMAIL = "test@email.com";
	public static final String TEST_USER_PASSWORD = "test_password";

	public static RequestSpecification defaultLoginRestAssured() {
		유저_생성(TEST_USER_EMAIL, TEST_USER_PASSWORD);
		String accessToken = 유저_로그인(TEST_USER_EMAIL, TEST_USER_PASSWORD);

		return oauthRestAssured(accessToken);
	}

	public static RequestSpecification loginRestAssured(String email, String password) {
		유저_생성(email, password);
		String accessToken = 유저_로그인(email, password);

		return oauthRestAssured(accessToken);
	}

	private static RequestSpecification oauthRestAssured(String accessToken) {
		return RestAssured.with()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header("Authorization", "Bearer " + accessToken);
	}

	private static void 유저_생성(String email, String password) {
		Map<String, String> body = new HashMap<>();
		body.put("email", email);
		body.put("password", password);

		ExtractableResponse<Response> response = RestAssured
			.given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(body)
			.when()
			.post("/api/users")
			.then().log().all()
			.statusCode(HttpStatus.CREATED.value())
			.extract();
	}

	private static String 유저_로그인(String email, String password) {
		Map<String, String> body = new HashMap<>();
		body.put("email", email);
		body.put("password", password);

		ExtractableResponse<Response> response = RestAssured
			.given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(body)
			.when()
			.post("/api/auth/login")
			.then().log().all()
			.statusCode(HttpStatus.OK.value())
			.extract();

		return response.jsonPath().getString("data.accessToken");
	}

	public static void 요청_응답_성공(ExtractableResponse<Response> response) {
		Boolean success = response.jsonPath().getBoolean("success");
		assertThat(success).isTrue();
	}

	public static void 요청_응답_실패(ExtractableResponse<Response> response) {
		Boolean success = response.jsonPath().getBoolean("success");
		assertThat(success).isFalse();
	}

}
