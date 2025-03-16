package com.example.domain.demo.common.pagination;

import com.example.domain.demo.exception.DemoDomainError;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomPaginationError  implements DemoDomainError {

	private String code;
	private String message;

	public static CustomPaginationError invalidFormat(String param) {
		return CustomPaginationError.builder()
			.code(PaginationError.INVALID_FORMAT.getCode())
			.message(PaginationError.INVALID_FORMAT.getMessage() + " 입력값: " + param)
			.build();
	}

	public static CustomPaginationError invalidDirection(String direction, String validDirections) {
		return CustomPaginationError.builder()
			.code(PaginationError.INVALID_DIRECTION.getCode())
			.message(
				PaginationError.INVALID_DIRECTION.getMessage() + " 입력값: '" + direction + "', 유효한 값: " + validDirections)
			.build();
	}

	public static CustomPaginationError invalidField(String fieldName, String enumClassName) {
		return CustomPaginationError.builder()
			.code(PaginationError.INVALID_FIELD.getCode())
			.message(PaginationError.INVALID_FIELD.getMessage() + " 입력값: '" + fieldName + "', 클래스: " + enumClassName)
			.build();
	}

}
