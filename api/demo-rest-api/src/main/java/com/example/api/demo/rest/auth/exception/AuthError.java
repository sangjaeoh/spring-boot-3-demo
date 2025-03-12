package com.example.api.demo.rest.auth.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import com.example.api.demo.rest.exception.DemoRestApiError;

@Getter
public enum AuthError implements DemoRestApiError {

	// 일반 인증 에러
	UNAUTHORIZED("ATE0001", "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
	FORBIDDEN("ATE0002", "유저의 권한이 부족합니다.", HttpStatus.FORBIDDEN),

	// JWT Provider 관련 에러
	JWT_INVALID("ATE0101", "JWT 가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
	JWT_MALFORMED("ATE0102", "유효하지 않은 JWT 토큰 형식입니다.", HttpStatus.BAD_REQUEST),
	JWT_UNSUPPORTED("ATE0103", "지원되지 않는 JWT 토큰 형식입니다.", HttpStatus.BAD_REQUEST),
	JWT_SIGNATURE_INVALID("ATE0104", "JWT 서명이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
	JWT_EXPIRED("ATE0105", "JWT 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
	JWT_CLAIMS_INVALID("ATE0106", "JWT Claims 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);

	private final String code;
	private final String message;
	private final HttpStatus status;

	AuthError(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status;
	}
}
