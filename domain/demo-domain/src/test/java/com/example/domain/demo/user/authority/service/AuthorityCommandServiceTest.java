package com.example.domain.demo.user.authority.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;

import com.example.domain.demo.common.BaseServiceTest;
import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.domain.Role;
import com.example.domain.demo.user.authority.fixture.AuthorityTestFixture;
import com.example.domain.demo.user.authority.repository.AuthorityJpaRepository;
import com.example.domain.demo.user.authority.service.command.CreateAuthorityCommand;

@Import({AuthorityCommandService.class})
class AuthorityCommandServiceTest extends BaseServiceTest {

	@Mock
	private AuthorityJpaRepository authorityJpaRepository;

	@InjectMocks
	private AuthorityCommandService authorityCommandService;

	@Test
	@DisplayName("create_성공")
	void create() {
		// given
		Long id = 1L;
		Role role = Role.COMMON;
		Authority authority = AuthorityTestFixture.createWithIdAndRole(id, role);
		CreateAuthorityCommand command = CreateAuthorityCommand.builder().role(Role.COMMON).build();
		when(authorityJpaRepository.save(any(Authority.class))).thenReturn(authority);

		// when
		Long createdId = authorityCommandService.create(command);

		// then
		assertThat(createdId).isNotNull();
	}

}