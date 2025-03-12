package com.example.api.demo.rest.user.contoller.response;

import com.example.domain.demo.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {

	private Long id;
	private String email;
	private String name;
	private int age;

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.age(user.getAge())
			.build();
	}

}
