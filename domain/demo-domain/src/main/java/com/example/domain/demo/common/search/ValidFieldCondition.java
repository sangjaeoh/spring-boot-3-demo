package com.example.domain.demo.common.search;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldConditionValidator.class)
@Documented
public @interface ValidFieldCondition {
	String message() default "Invalid field condition";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Operator[] allowedOperators();
}