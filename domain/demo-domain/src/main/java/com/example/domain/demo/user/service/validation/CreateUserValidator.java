package com.example.domain.demo.user.service.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.example.domain.demo.user.exception.UserError;
import com.example.domain.demo.user.exception.UserException;
import com.example.domain.demo.user.repository.UserRepository;
import com.example.domain.demo.user.service.command.CreateUserCommand;
import com.example.domain.demo.validation.AbstractValidator;

@Component
public class CreateUserValidator extends AbstractValidator<CreateUserCommand> {

	private final UserRepository userRepository;

	public CreateUserValidator(UserRepository userRepository) {
		super(CreateUserCommand.class);
		this.userRepository = userRepository;
	}

	@Override
	protected void validateTarget(CreateUserCommand command, Errors errors) {

		// 사용자 이름 중복 확인
		if (this.userRepository.findByEmail(command.getEmail()).isPresent()) {
			throw new UserException(UserError.DUPLICATE_EMAIL);
		}

	}

	@Override
	protected void validateTarget(CreateUserCommand command) {
		Errors errors = new BeanPropertyBindingResult(command, "command");
		this.validate(command, errors);
	}
}
