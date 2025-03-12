package com.example.domain.demo.user.exception;

import com.example.domain.demo.exception.DemoDomainException;

public class UserException extends DemoDomainException {

	public UserException(UserError error) {
		super(error);
	}

	public UserException(UserError error, Throwable cause) {
		super(error, cause);
	}

	public UserException(UserCustomError error) {
		super(error);
	}

}