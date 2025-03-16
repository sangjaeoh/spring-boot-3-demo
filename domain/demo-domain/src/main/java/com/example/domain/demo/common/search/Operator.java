package com.example.domain.demo.common.search;

public enum Operator {
	EQUALS,
	GT,
	LT,
	GTE,
	LTE,
	BETWEEN,
	LIKE;

	public static Operator fromString(String value) {
		return valueOf(value.trim().toUpperCase());
	}
}
