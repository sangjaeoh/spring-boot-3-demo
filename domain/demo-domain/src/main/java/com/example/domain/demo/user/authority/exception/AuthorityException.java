package com.example.domain.demo.user.authority.exception;

import org.springframework.http.HttpStatus;

import com.example.domain.demo.exception.DemoDomainException;
import com.example.domain.demo.user.exception.UserCustomError;
import com.example.domain.demo.user.exception.UserError;

public class AuthorityException extends DemoDomainException {

	public AuthorityException(AuthorityError error) {
		super(error);
	}

	public AuthorityException(AuthorityError error, Throwable cause) {
		super(error, cause);
	}

	public AuthorityException(UserCustomError error) {
		super(error);
	}

}