package com.example.domain.demo.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractValidator<T> implements Validator {

	private final Class<T> supportedClass;

	public AbstractValidator(Class<T> clazz) {
		this.supportedClass = clazz;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return supportedClass.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// 지원하지 않는 타입이면 검증하지 않음
		if (!supports(target.getClass())) {
			return;
		}

		validateTarget(supportedClass.cast(target), errors);
	}

	public void validate(Object target) {
		if (!supports(target.getClass())) {
			return;
		}

		validateTarget(supportedClass.cast(target));
	}

	protected abstract void validateTarget(T target, Errors errors);

	protected abstract void validateTarget(T target);

}
