package com.example.domain.demo.user.authority.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.domain.Role;
import com.example.domain.demo.user.authority.exception.AuthorityError;
import com.example.domain.demo.user.authority.exception.AuthorityException;
import com.example.domain.demo.user.authority.repository.AuthorityJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityQueryService {

	private final AuthorityJpaRepository authorityJpaRepository;

	public Optional<Authority> getById(Long id) {
		return authorityJpaRepository.findById(id);
	}

	public Authority getOrThrowById(Long id) {
		Optional<Authority> optionalAuthority = this.getById(id);
		return optionalAuthority.orElseThrow(() -> new AuthorityException(AuthorityError.NOT_FOUND));
	}

	public Optional<Authority> getByRole(Role role) {
		return authorityJpaRepository.findByRole(role);
	}

	public Authority getOrThrowByRole(Role role) {
		Optional<Authority> optionalAuthority = this.getByRole(role);
		return optionalAuthority.orElseThrow(() -> new AuthorityException(AuthorityError.NOT_FOUND));
	}
}
