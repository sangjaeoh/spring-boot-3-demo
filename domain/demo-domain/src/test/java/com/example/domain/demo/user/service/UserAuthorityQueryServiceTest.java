package com.example.domain.demo.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.domain.demo.user.domain.UserAuthority;
import com.example.domain.demo.user.fixture.UserAuthorityTestFixture;
import com.example.domain.demo.user.repository.UserAuthorityJpaRepository;

@ExtendWith(MockitoExtension.class)
class UserAuthorityQueryServiceTest {

	@Mock
	private UserAuthorityJpaRepository userAuthorityJpaRepository;

	@InjectMocks
	private UserAuthorityQueryService userAuthorityQueryService;

	@Test
	void getById() {
		// Given
		Long id = 1L;
		Optional<UserAuthority> givenOptionalUserAuthority = Optional.of(UserAuthorityTestFixture.createDefault());
		when(userAuthorityJpaRepository.findById(any(Long.class))).thenReturn(givenOptionalUserAuthority);

		// When
		Optional<UserAuthority> optionalUserAuthority = userAuthorityQueryService.getById(id);

		// Then
		assertThat(optionalUserAuthority).isPresent();
	}

}