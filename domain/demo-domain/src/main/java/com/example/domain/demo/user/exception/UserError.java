package com.example.domain.demo.user.exception;

import com.example.domain.demo.exception.DemoDomainError;

import lombok.Getter;

@Getter
public enum UserError implements DemoDomainError {

	// 기본 에러
	DUPLICATE_EMAIL("URE0001", "이미 존재하는 이메일입니다."), NOT_FOUND("UE0002", "사용자를 찾을 수 없습니다."),
	NOT_FOUNT("URE0002", "신청자를 찾을 수 없습니다."),

	// 유저 도메인 에러
	INVALID("URE1001", "유저가 유효하지 않습니다."),
	INVALID_EMAIL("URE1002", "유저 이메일이 유효하지 않습니다."),
	INVALID_PASSWORD("URE1003", "유저 암호가 유효하지 않습니다."),
	INVALID_NAME("URE1004", "유저 이름이 유효하지 않습니다."),
	INVALID_AGE("URE1005", "유저 패스워드가 유효하지 않습니다.");

	private final String code;
	private final String message;

	UserError(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
