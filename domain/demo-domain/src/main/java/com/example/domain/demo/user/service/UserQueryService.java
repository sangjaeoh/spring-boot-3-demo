package com.example.domain.demo.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.demo.common.pagination.PageResponse;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.exception.UserError;
import com.example.domain.demo.user.exception.UserException;
import com.example.domain.demo.user.repository.UserRepository;
import com.example.domain.demo.user.service.query.SearchUserCriteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

	private final UserRepository userRepository;

	public Optional<User> getById(Long id) {
		return userRepository.findById(id);
	}

	public User getOrThrowById(Long id) {
		Optional<User> user = this.getById(id);
		return user.orElseThrow(() -> new UserException(UserError.NOT_FOUND));
	}

	public Optional<User> getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User getOrThrowByEmail(String email) {
		Optional<User> user = this.getByEmail(email);
		return user.orElseThrow(() -> new UserException(UserError.NOT_FOUND));
	}

	public PageResponse<User> search(SearchUserCriteria criteria) {
		return userRepository.searchPagination(criteria);
	}
}
