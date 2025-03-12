package com.example.domain.demo.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.domain.Role;
import com.example.domain.demo.user.authority.service.AuthorityQueryService;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.domain.validation.UserValidator;
import com.example.domain.demo.user.repository.UserRepository;
import com.example.domain.demo.user.service.command.CreateUserAuthorityCommand;
import com.example.domain.demo.user.service.command.CreateUserCommand;
import com.example.domain.demo.user.service.command.ReplaceUserCommand;
import com.example.domain.demo.user.service.command.UpdateUserCommand;
import com.example.domain.demo.user.service.validation.CreateUserValidator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class UserCommandService {

	private final PasswordEncoder passwordEncoder;

	private final UserValidator userValidator;
	private final CreateUserValidator createUserValidator;

	private final UserRepository userRepository;
	private final UserQueryService userQueryService;
	private final AuthorityQueryService authorityQueryService;
	private final UserAuthorityCommandService userAuthorityCommandService;

	@Transactional
	public Long create(@Valid CreateUserCommand command) {

		// User 생성 유효성 검증
		this.createUserValidator.validate(command);

		// 비밀번호 암호화
		String encodedPassword = this.passwordEncoder.encode(command.getPassword());

		// User 생성
		User user = User.builder()
			.email(command.getEmail())
			.password(encodedPassword)
			.name(command.getName())
			.age(command.getAge())
			.build();

		// User 도메인 유효성 검증
		this.userValidator.validate(user);

		// User 저장
		this.userRepository.save(user);

		// 권한생성
		Authority authority = this.authorityQueryService.getOrThrowByRole(Role.COMMON);
		CreateUserAuthorityCommand createUserAuthorityCommand = CreateUserAuthorityCommand.builder()
			.userId(user.getId())
			.authorityId(authority.getId())
			.build();
		this.userAuthorityCommandService.create(createUserAuthorityCommand);

		return user.getId();
	}

	@Transactional
	public void replace(Long id, @Valid ReplaceUserCommand command) {
		User user = this.userQueryService.getOrThrowById(id);

		user.updateName(command.getName());
		user.updatePassword(this.passwordEncoder.encode(command.getPassword()));
		user.updateAge(command.getAge());

		// User 도메인 유효성 검증
		this.userValidator.validate(user);

		// 명시적 저장
		this.userRepository.save(user);
	}

	@Transactional
	public void update(Long id, @Valid UpdateUserCommand command) {
		User user = this.userQueryService.getOrThrowById(id);

		if (command.hasName()) {
			user.updateName(command.getName().get());
		}

		if (command.hasPassword()) {
			user.updatePassword(this.passwordEncoder.encode(command.getPassword().get()));
		}

		if (command.hasAge()) {
			user.updateAge(command.getAge().get());
		}

		// User 도메인 유효성 검증
		this.userValidator.validate(user);

		// 명시적 저장
		this.userRepository.save(user);
	}

}
