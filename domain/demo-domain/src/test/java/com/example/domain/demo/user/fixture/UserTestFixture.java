package com.example.domain.demo.user.fixture;

import com.example.domain.demo.user.domain.User;

public class UserTestFixture {

	public static final String EMAIL = "test@email.com";
	public static final String PASSWORD = "test12345";
	public static final String NAME = "테스터";
	public static final Integer AGE = 20;

	public static User createDefault() {
		return User.builder().email(EMAIL).password(PASSWORD).name(NAME).age(AGE).build();
	}

	public static User createDefaultWithId(Long id) {
		return User.builder().id(id).email(EMAIL).password(PASSWORD).name(NAME).age(AGE).build();
	}
}