package com.example.api.demo.rest.common;

import com.example.api.demo.rest.exception.ErrorResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResult<T> {

	private boolean success; // 성공 여부
	private T data; // 응답 데이터
	private ErrorResponse error; // 에러

	public static <T> ApiResult<T> success(T data) {
		return new ApiResult<>(true, data, null);
	}

	public static ApiResult<Void> success() {
		return new ApiResult<>(true, null, null);
	}

	public static <T> ApiResult<T> error(ErrorResponse error) {
		return new ApiResult<>(false, null, error);
	}
}
