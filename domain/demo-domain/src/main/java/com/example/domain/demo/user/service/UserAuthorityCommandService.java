package com.example.domain.demo.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.service.AuthorityQueryService;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.domain.UserAuthority;
import com.example.domain.demo.user.repository.UserAuthorityJpaRepository;
import com.example.domain.demo.user.service.command.CreateUserAuthorityCommand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthorityCommandService {

	private final UserAuthorityJpaRepository userAuthorityJpaRepository;
	private final UserQueryService userQueryService;
	private final AuthorityQueryService authorityQueryService;

	@Transactional
	public Long create(CreateUserAuthorityCommand command) {

		User user = userQueryService.getOrThrowById(command.getUserId());
		Authority authority = authorityQueryService.getOrThrowById(command.getAuthorityId());

		UserAuthority userAuthority = UserAuthority.builder().user(user).authority(authority).build();

		userAuthorityJpaRepository.save(userAuthority);
		return userAuthority.getId();
	}
}
