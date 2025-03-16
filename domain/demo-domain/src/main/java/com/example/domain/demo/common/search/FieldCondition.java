package com.example.domain.demo.common.search;

import java.util.function.Function;

import lombok.Getter;

@Getter
public class FieldCondition<T> {
	private T value;
	private T valueTo; // BETWEEN 용
	private Operator operator;

	public FieldCondition(String rawValue, Function<String, T> valueConverter) {
		String[] parts = rawValue.split(",");
		if (parts.length < 2) {
			throw new IllegalArgumentException(
				"Invalid format: " + rawValue + ". Expected 'value,operator' or 'value1,value2,BETWEEN'");
		}

		try {
			if (parts.length == 3) { // BETWEEN 연산자
				this.value = valueConverter.apply(parts[0].trim());
				this.valueTo = valueConverter.apply(parts[1].trim());
				this.operator = Operator.fromString(parts[2].trim());
				if (operator != Operator.BETWEEN) {
					throw new IllegalArgumentException("Three parts must end with 'BETWEEN': " + rawValue);
				}
			} else if (parts.length == 2) { // 단일 값 연산자
				this.value = valueConverter.apply(parts[0].trim());
				this.operator = Operator.fromString(parts[1].trim());
				if (operator == Operator.BETWEEN) {
					throw new IllegalArgumentException("BETWEEN requires 'value1,value2,operator': " + rawValue);
				}
			} else {
				throw new IllegalArgumentException("Invalid number of parts: " + parts.length + " in " + rawValue);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to parse '" + rawValue + "': " + e.getMessage(), e);
		}
	}
}