package com.example.domain.demo.user.exception;

import com.example.domain.demo.exception.DemoDomainError;

import lombok.Getter;

@Getter
public enum UserAuthorityError implements DemoDomainError {

	// 기본 에러
	NOT_FOUND("UAT0001", "유저권한을 찾을 수 없습니다."),

	// 유저권한 도메인 에러
	INVALID("UAT1001", "유저권한이 유효하지 않습니다.");

	private final String code;
	private final String message;

	UserAuthorityError(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
