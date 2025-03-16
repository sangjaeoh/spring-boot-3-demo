package com.example.domain.demo.common.pagination;

import com.example.domain.demo.exception.DemoDomainError;

import lombok.Getter;

@Getter
public enum PaginationError implements DemoDomainError {
	// 기본 에러
	INVALID_FORMAT("SRE0001", "정렬 파라미터는 'field,direction' 형식이어야 합니다."),
	INVALID_DIRECTION("SRE0002", "유효하지 않은 정렬 방향입니다."),
	INVALID_FIELD("SRE0003", "유효하지 않은 정렬 필드입니다.");

	private final String code;
	private final String message;

	PaginationError(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
