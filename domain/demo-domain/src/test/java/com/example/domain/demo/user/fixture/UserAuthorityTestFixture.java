package com.example.domain.demo.user.fixture;

import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.fixture.AuthorityTestFixture;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.domain.UserAuthority;

public class UserAuthorityTestFixture {

	public static UserAuthority createDefault() {
		User user = UserTestFixture.createDefault();
		Authority authority = AuthorityTestFixture.createDefault();
		return UserAuthority.builder().user(user).authority(authority).build();
	}

	public static UserAuthority createWithIdAndUserAndAuthority(Long id, User user, Authority authority) {
		return UserAuthority.builder().id(id).user(user).authority(authority).build();
	}

}