package com.example.domain.demo.user.authority.exception;

import com.example.domain.demo.exception.DemoDomainError;

import lombok.Getter;

@Getter
public enum AuthorityError implements DemoDomainError {

	// 기본 에러
	NOT_FOUND("ATE0001", "권한을 찾을 수 없습니다."),

	// 권한 도메인 에러
	INVALID("ATE1001", "권한이 유효하지 않습니다.");

	private final String code;
	private final String message;

	AuthorityError(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
