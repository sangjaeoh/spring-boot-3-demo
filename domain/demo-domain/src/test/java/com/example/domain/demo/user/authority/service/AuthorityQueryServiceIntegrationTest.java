package com.example.domain.demo.user.authority.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import com.example.domain.demo.common.BaseServiceIntegrationTest;
import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.exception.AuthorityException;

@Import({AuthorityQueryService.class})
@DisplayName("AuthorityQueryServiceIntegration 테스트")
class AuthorityQueryServiceIntegrationTest extends BaseServiceIntegrationTest {

	@Autowired
	private AuthorityQueryService authorityQueryService;

	@Test
	@DisplayName("getOrThrowById_에러발생")
	void getOrThrowById_에러발생() {
		// given
		Long id = Long.MAX_VALUE;

		// when
		Throwable thrown = catchThrowable(() -> {
			Authority authority = authorityQueryService.getOrThrowById(id);
		});

		// then
		assertThat(thrown).isInstanceOf(AuthorityException.class);
	}

}