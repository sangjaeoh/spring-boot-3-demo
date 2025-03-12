package com.example.api.demo.rest.auth.exception;

import com.example.api.demo.rest.exception.DemoRestApiException;

public class AuthException extends DemoRestApiException {

	public AuthException(AuthError error) {
		super(error);
	}

	public AuthException(AuthError error, Throwable cause) {
		super(error, cause);
	}

}