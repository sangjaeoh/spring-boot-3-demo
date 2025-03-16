package com.example.api.demo.auth.exception;

import com.example.api.demo.exception.DemoRestApiException;

public class AuthException extends DemoRestApiException {

	public AuthException(AuthError error) {
		super(error);
	}

	public AuthException(AuthError error, Throwable cause) {
		super(error, cause);
	}

}