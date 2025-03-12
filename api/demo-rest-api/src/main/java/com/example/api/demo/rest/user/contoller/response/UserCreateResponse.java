package com.example.api.demo.rest.user.contoller.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateResponse {
	private Long id;

	public static UserCreateResponse from(Long id) {
		return UserCreateResponse.builder().id(id).build();
	}
}
