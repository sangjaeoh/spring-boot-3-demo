package com.example.domain.demo.user.domain.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.exception.UserCustomError;
import com.example.domain.demo.user.exception.UserError;
import com.example.domain.demo.user.exception.UserException;
import com.example.domain.demo.validation.AbstractValidator;

@Component
public class UserValidator extends AbstractValidator<User> {

	public UserValidator() {
		super(User.class);
	}

	@Override
	protected void validateTarget(User user, Errors errors) {

		// 이메일
		if (user.getEmail() == null || !user.getEmail().contains("@")) {
			errors.rejectValue("email", UserError.INVALID_EMAIL.getCode(), UserError.INVALID_EMAIL.getMessage());
		}

		// 암호
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			errors.rejectValue("password", UserError.INVALID_PASSWORD.getCode(),
				UserError.INVALID_PASSWORD.getMessage());
		}

		// 유저 이름
		if (user.getName() != null && user.getName().isEmpty()) {
			errors.rejectValue("name", UserError.INVALID_NAME.getCode(), UserError.INVALID_NAME.getMessage());
		}

		// 유저 나이
		if (user.getAge() != null && user.getAge() < 0) {
			errors.rejectValue("age", UserError.INVALID_AGE.getCode(), UserError.INVALID_AGE.getMessage());
		}

	}

	@Override
	protected void validateTarget(User user) {
		Errors errors = new BeanPropertyBindingResult(user, "user");
		this.validate(user, errors);
		if (errors.hasErrors()) {
			throw new UserException(UserCustomError.from(errors));
		}
	}
}
