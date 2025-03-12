package com.example.api.demo.rest.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

	private LocalDateTime timestamp; // 오류 발생 시간
	private int status; // HTTP 상태 코드
	private String error; // HTTP 상태 텍스트 (예: "Bad Request")
	private String errorCode; // 에러 코드
	private String message; // 오류 메시지
	private String path; // 요청 경로
	private List<String> details; // 오류 상세 정보
	private Map<String, Object> additionalInfo; // 추가 정보

	public static class ErrorResponseBuilder {

		public ErrorResponseBuilder detail(String detail) {
			if (this.details == null) {
				this.details = new java.util.ArrayList<>();
			}
			this.details.add(detail);
			return this;
		}

		public ErrorResponseBuilder additionalInfo(String key, Object value) {
			if (this.additionalInfo == null) {
				this.additionalInfo = new java.util.HashMap<>();
			}
			this.additionalInfo.put(key, value);
			return this;
		}
	}
}