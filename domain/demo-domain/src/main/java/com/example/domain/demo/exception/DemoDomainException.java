package com.example.domain.demo.exception;

import lombok.Getter;

@Getter
public abstract class DemoDomainException extends RuntimeException {
	private DemoDomainError error;

	public DemoDomainException(DemoDomainError error) {
		super(error.getMessage());
		this.error = error;
	}

	public DemoDomainException(DemoDomainError error, Throwable cause) {
		super(error.getMessage(), cause);
		this.error = error;
	}

}
