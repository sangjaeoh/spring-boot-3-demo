package com.example.api.demo.exception;

import lombok.Getter;

@Getter
public abstract class DemoRestApiException extends RuntimeException {
	private DemoRestApiError error;

	public DemoRestApiException(DemoRestApiError error) {
		super(error.getMessage());
		this.error = error;
	}

	public DemoRestApiException(DemoRestApiError error, Throwable cause) {
		super(error.getMessage(), cause);
		this.error = error;
	}

}
