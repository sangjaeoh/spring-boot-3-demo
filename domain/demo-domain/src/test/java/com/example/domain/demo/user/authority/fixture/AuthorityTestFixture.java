package com.example.domain.demo.user.authority.fixture;

import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.domain.Role;

public class AuthorityTestFixture {

	private static final Role ROLE = Role.COMMON;

	public static Authority createDefault() {
		return Authority.builder().role(ROLE).build();
	}

	public static Authority createDefaultWithId(Long id) {
		return Authority.builder().id(id).role(ROLE).build();
	}

	public static Authority createWithRole(Role role) {
		return Authority.builder().role(role).build();
	}

	public static Authority createWithIdAndRole(Long id, Role role) {
		return Authority.builder().id(id).role(role).build();
	}
}