package com.example.api.demo.exception;

import org.springframework.http.HttpStatus;

public interface DemoRestApiError {

	String getCode();

	String getMessage();

	HttpStatus getStatus();

}
