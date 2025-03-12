package com.example.api.demo.rest.auth.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {

	private String accessToken;
	private String refreshToken;
	private long accessTokenExpiration;
	private long refreshTokenExpiration;

}
