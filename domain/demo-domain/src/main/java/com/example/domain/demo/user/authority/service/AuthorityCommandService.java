package com.example.domain.demo.user.authority.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.repository.AuthorityJpaRepository;
import com.example.domain.demo.user.authority.service.command.CreateAuthorityCommand;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityCommandService {

	private final AuthorityJpaRepository authorityJpaRepository;

	@Transactional
	public Long create(CreateAuthorityCommand command) {
		Authority authority = Authority.builder().role(command.getRole()).build();
		Authority savedAuthority = authorityJpaRepository.save(authority);
		return savedAuthority.getId();
	}
}
