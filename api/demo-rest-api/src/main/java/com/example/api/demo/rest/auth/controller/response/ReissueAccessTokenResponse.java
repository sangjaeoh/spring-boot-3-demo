package com.example.api.demo.rest.auth.controller.response;

import com.example.api.demo.rest.auth.service.dto.LoginDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReissueAccessTokenResponse {

	private String accessToken;
	private String refreshToken;
	private long accessTokenExpiration;
	private long refreshTokenExpiration;

	public static ReissueAccessTokenResponse from(LoginDto loginDto) {
		return ReissueAccessTokenResponse.builder()
			.accessToken(loginDto.getAccessToken())
			.refreshToken(loginDto.getRefreshToken())
			.accessTokenExpiration(loginDto.getAccessTokenExpiration())
			.refreshTokenExpiration(loginDto.getRefreshTokenExpiration())
			.build();
	}

}
