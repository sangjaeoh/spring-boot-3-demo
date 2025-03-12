package com.example.domain.demo.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.demo.user.domain.UserAuthority;
import com.example.domain.demo.user.exception.UserAuthorityError;
import com.example.domain.demo.user.exception.UserAuthorityException;
import com.example.domain.demo.user.repository.UserAuthorityJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAuthorityQueryService {

	private final UserAuthorityJpaRepository userAuthorityJpaRepository;

	public Optional<UserAuthority> getById(Long id) {
		return userAuthorityJpaRepository.findById(id);
	}

	public UserAuthority getOrThrowById(Long id) {
		Optional<UserAuthority> optionalUserAuthority = userAuthorityJpaRepository.findById(id);
		return optionalUserAuthority.orElseThrow(() -> new UserAuthorityException(UserAuthorityError.NOT_FOUND));
	}

	public Optional<UserAuthority> getByUserId(Long userId) {
		return userAuthorityJpaRepository.findByUserId(userId);
	}

	public UserAuthority getOrThrowByUserId(Long userId) {
		Optional<UserAuthority> optionalUserAuthority = userAuthorityJpaRepository.findByUserId(userId);
		return optionalUserAuthority.orElseThrow(() -> new UserAuthorityException(UserAuthorityError.NOT_FOUND));
	}

	public Optional<UserAuthority> getByAuthorityId(Long authorityId) {
		return userAuthorityJpaRepository.findByAuthorityId(authorityId);
	}

	public UserAuthority getOrThrowByAuthorityId(Long authorityId) {
		Optional<UserAuthority> optionalUserAuthority = userAuthorityJpaRepository.findByAuthorityId(authorityId);
		return optionalUserAuthority.orElseThrow(() -> new UserAuthorityException(UserAuthorityError.NOT_FOUND));
	}
}
