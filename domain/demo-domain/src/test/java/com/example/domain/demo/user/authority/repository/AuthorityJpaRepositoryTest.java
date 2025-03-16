package com.example.domain.demo.user.authority.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.domain.demo.common.BaseRepositoryTest;
import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.domain.Role;

class AuthorityJpaRepositoryTest extends BaseRepositoryTest {

	@Autowired
	private AuthorityJpaRepository authorityJpaRepository;

	@Test
	@DisplayName("findByRole_조회 성공")
	void findByRole() {
		// given
		Role role = Role.COMMON;

		// when
		Optional<Authority> optionalAuthority = authorityJpaRepository.findByRole(role);

		// then
		assertThat(optionalAuthority).isPresent();
	}

}