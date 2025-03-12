package com.example.domain.demo.user.exception;

import org.springframework.http.HttpStatus;

import com.example.domain.demo.exception.DemoDomainException;

public class UserAuthorityException extends DemoDomainException {

	public UserAuthorityException(UserAuthorityError error) {
		super(error);
	}

	public UserAuthorityException(UserAuthorityError error, Throwable cause) {
		super(error, cause);
	}

	public UserAuthorityException(UserAuthorityCustomError error) {
		super(error);
	}
}