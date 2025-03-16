package com.example.domain.demo.common.pagination;

import com.example.domain.demo.exception.DemoDomainException;

public class PaginationException extends DemoDomainException {

	public PaginationException(PaginationError error) {
		super(error);
	}

	public PaginationException(PaginationError error, Throwable cause) {
		super(error, cause);
	}

	public PaginationException(CustomPaginationError error) {
		super(error);
	}
}
