package com.example.domain.demo.user.authority.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;

import com.example.domain.demo.common.BaseServiceTest;
import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.domain.Role;
import com.example.domain.demo.user.authority.exception.AuthorityException;
import com.example.domain.demo.user.authority.repository.AuthorityJpaRepository;

@Import({AuthorityQueryService.class})
@DisplayName("AuthorityQueryService 테스트")
class AuthorityQueryServiceTest extends BaseServiceTest {

	@Mock
	private AuthorityJpaRepository authorityJpaRepository;

	@InjectMocks
	private AuthorityQueryService authorityQueryService;

	@Test
	@DisplayName("getOrThrowById_에러발생")
	void getOrThrowById_에러발생() {
		// given
		Long id = 1L;
		when(authorityJpaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		// when
		Throwable thrown = catchThrowable(() -> {
			Authority authority = authorityQueryService.getOrThrowById(id);
		});

		// then
		assertThat(thrown).isInstanceOf(AuthorityException.class);
	}

	@Test
	@DisplayName("getOrThrowByRole_에러발생")
	void getOrThrowByRole() {
		// given
		Role role = Role.COMMON;
		when(authorityJpaRepository.findByRole(any(Role.class))).thenReturn(Optional.empty());

		// when
		Throwable thrown = catchThrowable(() -> {
			Authority authority = authorityQueryService.getOrThrowByRole(role);
		});

		// then
		assertThat(thrown).isInstanceOf(AuthorityException.class);
	}
}