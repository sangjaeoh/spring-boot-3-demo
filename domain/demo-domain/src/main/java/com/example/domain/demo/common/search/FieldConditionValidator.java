package com.example.domain.demo.common.search;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldConditionValidator implements ConstraintValidator<ValidFieldCondition, FieldCondition<?>> {
	private Operator[] allowedOperators;

	@Override
	public void initialize(ValidFieldCondition constraintAnnotation) {
		this.allowedOperators = constraintAnnotation.allowedOperators();
	}

	@Override
	public boolean isValid(FieldCondition<?> value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		Operator operator = value.getOperator();
		return operator != null && Arrays.asList(allowedOperators).contains(operator);
	}
}
