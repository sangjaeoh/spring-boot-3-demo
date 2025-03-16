package com.example.api.demo.user.fixture;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.api.demo.fixture.RestAssuredTestFixture;
import com.example.api.demo.user.contoller.request.CreateUserRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class UserControllerTestFixture {

	public static ExtractableResponse<Response> 유저_생성_요청(CreateUserRequest request) {
		return RestAssured.given()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when()
			.post("/api/users")
			.then()
			.log()
			.all()
			.statusCode(HttpStatus.CREATED.value())
			.extract();
	}

	public static ExtractableResponse<Response> 유저_조회_요청(Long id) {
		return RestAssuredTestFixture.defaultLoginRestAssured()
			.given()
			.pathParam("id", id)
			.when()
			.get("/api/users/{id}")
			.then()
			.log()
			.all()
			.statusCode(HttpStatus.OK.value())
			.extract();
	}

}
