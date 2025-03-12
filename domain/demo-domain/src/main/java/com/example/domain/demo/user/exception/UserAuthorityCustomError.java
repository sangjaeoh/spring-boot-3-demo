package com.example.domain.demo.user.exception;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

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
public class UserAuthorityCustomError implements DemoDomainError {

	private String code;
	private String message;

	public static UserAuthorityCustomError from(Errors errors) {
		String message = errors.getAllErrors()
			.stream()
			.map(ObjectError::getDefaultMessage)
			.filter(Objects::nonNull)
			.collect(Collectors.joining(", "));

		return UserAuthorityCustomError.builder().code(UserAuthorityError.INVALID.getCode()).message(message).build();
	}
}
