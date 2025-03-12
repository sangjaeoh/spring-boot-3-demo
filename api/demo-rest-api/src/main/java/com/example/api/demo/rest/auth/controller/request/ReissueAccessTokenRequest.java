package com.example.api.demo.rest.auth.controller.request;

import com.example.api.demo.rest.auth.service.command.ReissueAccessTokenCommand;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReissueAccessTokenRequest {

	private String refreshToken;

	public ReissueAccessTokenCommand toCommand() {
		return ReissueAccessTokenCommand.builder().refreshToken(this.refreshToken).build();
	}

}
