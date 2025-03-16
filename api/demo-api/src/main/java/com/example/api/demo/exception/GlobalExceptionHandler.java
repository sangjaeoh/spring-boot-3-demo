package com.example.api.demo.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.api.demo.auth.exception.AuthError;
import com.example.api.demo.common.ApiResult;
import com.example.domain.demo.exception.DemoDomainException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// api 예외 처리
	@ExceptionHandler(DemoRestApiException.class)
	protected ResponseEntity<ApiResult<Void>> handleCustomException(DemoRestApiException ex,
		HttpServletRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(ex.getError().getStatus().value())
			.error(ex.getError().getStatus().getReasonPhrase())
			.message(ex.getError().getMessage())
			.path(request.getRequestURI())
			.errorCode(ex.getError().getCode())
			.additionalInfo("User-Agent", request.getHeader("User-Agent"))
			.build();

		if (ex.getCause() != null) {
			errorResponse.getDetails().add("Cause: " + ex.getCause().getMessage());
		}

		ApiResult<Void> apiResult = ApiResult.error(errorResponse);
		return ResponseEntity.status(ex.getError().getStatus()).body(apiResult);
	}

	// 데모 도메인 예외 처리
	@ExceptionHandler(DemoDomainException.class)
	protected ResponseEntity<ApiResult<Void>> handleCustomException(DemoDomainException ex,
		HttpServletRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.BAD_REQUEST.value())
			.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
			.message(ex.getError().getMessage())
			.path(request.getRequestURI())
			.errorCode(ex.getError().getCode())
			.additionalInfo("User-Agent", request.getHeader("User-Agent"))
			.build();

		if (ex.getCause() != null) {
			errorResponse.getDetails().add("Cause: " + ex.getCause().getMessage());
		}

		ApiResult<Void> apiResult = ApiResult.error(errorResponse);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResult);
	}

	// Validation 예외 처리
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResult<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpServletRequest request) {
		List<String> details = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(error -> error.getField() + ": " + error.getDefaultMessage())
			.collect(Collectors.toList());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.BAD_REQUEST.value())
			.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
			.errorCode("NVE0001")
			.message("요청 형식이 올바르지 않습니다.")
			.path(request.getRequestURI())
			.details(details)
			.additionalInfo("User-Agent", request.getHeader("User-Agent"))
			.build();

		ApiResult<Void> apiResult = ApiResult.error(errorResponse);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResult);
	}

	// Validation 예외 처리
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResult<Void>> handleMethodArgumentNotValid(ConstraintViolationException ex,
		HttpServletRequest request) {

		List<String> details = ex.getConstraintViolations().stream().map(violation -> {
			String path = violation.getPropertyPath().toString();
			String field = path.substring(path.lastIndexOf(".") + 1);
			String message = violation.getMessage();
			return field + ": " + message;
		}).collect(Collectors.toList());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.BAD_REQUEST.value())
			.error(HttpStatus.BAD_REQUEST.getReasonPhrase())
			.errorCode("NVE0001")
			.message("요청 형식이 올바르지 않습니다.")
			.path(request.getRequestURI())
			.details(details)
			.additionalInfo("User-Agent", request.getHeader("User-Agent"))
			.build();

		ApiResult<Void> apiResult = ApiResult.error(errorResponse);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResult);
	}

	// 잘못된 인증 예외 처리
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResult<Void>> handleBadCredentialsException(BadCredentialsException ex,
		HttpServletRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(AuthError.UNAUTHORIZED.getStatus().value())
			.error(AuthError.UNAUTHORIZED.getStatus().getReasonPhrase())
			.errorCode(AuthError.UNAUTHORIZED.getCode())
			.message(AuthError.UNAUTHORIZED.getMessage())
			.path(request.getRequestURI())
			.additionalInfo("User-Agent", request.getHeader("User-Agent"))
			.build();

		ApiResult<Void> apiResult = ApiResult.error(errorResponse);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResult);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<ApiResult<Void>> handleInternalAuthenticationServiceException(
		InternalAuthenticationServiceException ex, HttpServletRequest request) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(AuthError.UNAUTHORIZED.getStatus().value())
			.error(AuthError.UNAUTHORIZED.getStatus().getReasonPhrase())
			.errorCode(AuthError.UNAUTHORIZED.getCode())
			.message(AuthError.UNAUTHORIZED.getMessage())
			.path(request.getRequestURI())
			.additionalInfo("User-Agent", request.getHeader("User-Agent"))
			.build();

		ApiResult<Void> apiResult = ApiResult.error(errorResponse);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResult);
	}

	// 접근 불가 예외 처리
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResult<Void>> handleAccessDeniedException(AccessDeniedException ex,
		HttpServletRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(AuthError.FORBIDDEN.getStatus().value())
			.error(AuthError.FORBIDDEN.getStatus().getReasonPhrase())
			.errorCode(AuthError.FORBIDDEN.getCode())
			.message(AuthError.FORBIDDEN.getMessage())
			.path(request.getRequestURI())
			.additionalInfo("User-Agent", request.getHeader("User-Agent"))
			.build();

		ApiResult<Void> apiResult = ApiResult.error(errorResponse);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResult);
	}

	// 모든 예외 처리
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiResult<Void>> handleUnexpectedException(Exception ex, HttpServletRequest request) {

		ErrorResponse errorResponse = ErrorResponse.builder()
			.timestamp(LocalDateTime.now())
			.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
			.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
			.errorCode("ISE0001")
			.message(ex.getMessage())
			.path(request.getRequestURI())
			.build();

		ApiResult<Void> apiResult = ApiResult.error(errorResponse);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResult);
	}
}