package com.example.domain.demo.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
import com.example.domain.demo.user.authority.service.AuthorityCommandService;
import com.example.domain.demo.user.authority.service.AuthorityQueryService;
import com.example.domain.demo.user.authority.service.command.CreateAuthorityCommand;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.domain.UserAuthority;
import com.example.domain.demo.user.fixture.UserAuthorityTestFixture;
import com.example.domain.demo.user.fixture.UserTestFixture;
import com.example.domain.demo.user.repository.UserAuthorityJpaRepository;
import com.example.domain.demo.user.service.command.CreateUserAuthorityCommand;

@Import({UserAuthorityCommandService.class})
class UserAuthorityCommandServiceTest extends BaseServiceTest {

	@Mock
	private UserAuthorityJpaRepository userAuthorityJpaRepository;

	@Mock
	private UserQueryService userQueryService;

	@Mock
	private AuthorityQueryService authorityQueryService;

	@InjectMocks
	private UserAuthorityCommandService userAuthorityCommandService;

	@Test
	@DisplayName("create_성공")
	void create() {
		// given
		Long userId = 1L;
		Long authorityId = 1L;
		Long userAuthorityId = 1L;
		User user = UserTestFixture.createDefaultWithId(userId);
		Authority authority = AuthorityTestFixture.createDefaultWithId(authorityId);
		UserAuthority userAuthority = UserAuthorityTestFixture.createWithIdAndUserAndAuthority(userAuthorityId, user,
			authority);
		CreateUserAuthorityCommand command = CreateUserAuthorityCommand.builder()
			.userId(userId)
			.authorityId(authorityId)
			.build();

		when(userQueryService.getOrThrowById(any(Long.class))).thenReturn(user);
		when(authorityQueryService.getOrThrowById(any(Long.class))).thenReturn(authority);
		when(userAuthorityJpaRepository.save(any(UserAuthority.class))).thenReturn(userAuthority);

		// when
		Long createdId = userAuthorityCommandService.create(command);

		// then
		assertThat(createdId).isNotNull();
	}

}