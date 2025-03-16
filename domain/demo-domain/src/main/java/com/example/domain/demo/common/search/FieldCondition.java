package com.example.domain.demo.common.search;

import java.util.function.Function;

import lombok.Getter;

@Getter
public class FieldCondition<T> {
	private T value;
	private T valueTo; // BETWEEN 용
	private Operator operator;

	public FieldCondition(String rawValue, Function<String, T> converter) {
		String[] parts = rawValue.split(",");
		if (parts.length < 2) {
			throw new IllegalArgumentException("Invalid format: " + rawValue + ". Expected 'value,operator'");
		}
		this.value = converter.apply(parts[0]);
		this.operator = Operator.fromString(parts[1]);
		if (operator == Operator.BETWEEN) {
			if (parts.length != 3) {
				throw new IllegalArgumentException("BETWEEN requires 'value1,value2,operator': " + rawValue);
			}
			this.valueTo = converter.apply(parts[2]);
		}
	}
}