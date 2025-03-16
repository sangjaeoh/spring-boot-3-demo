package com.example.api.demo.auth.controller.request;

import com.example.api.demo.auth.service.command.LoginCommand;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

	private String email;
	private String password;

	public LoginCommand toCommand() {
		return LoginCommand.builder()
			.email(this.email)
			.password(this.password)
			.build();
	}

}
