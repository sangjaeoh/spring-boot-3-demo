package com.example.api.demo.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.api.demo.auth.exception.AuthError;
import com.example.api.demo.auth.exception.AuthException;
import com.example.api.demo.auth.provider.JwtTokenProvider;
import com.example.api.demo.auth.service.command.LoginCommand;
import com.example.api.demo.auth.service.command.ReissueAccessTokenCommand;
import com.example.api.demo.auth.service.dto.LoginDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;

	// 로그인 및 토큰 발급
	public LoginDto login(LoginCommand loginCommand) {
		// 1. 사용자 인증 (AuthenticationManager 사용, UsernamePasswordAuthenticationToken 사용)
		String username = loginCommand.getEmail();
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, loginCommand.getPassword()));

		// 2. 인증 성공 시, JWT 토큰 발급
		// 인증된 사용자 정보 획득
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		// 엑세스 토큰 생성
		String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
		// 리프레시 토큰 생성
		String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

		// 3. 토큰 및 만료 시간 반환
		return LoginDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.accessTokenExpiration(jwtTokenProvider.getExpiration(accessToken))
			.refreshTokenExpiration(jwtTokenProvider.getExpiration(refreshToken))
			.build();
	}

	public LoginDto reissueAccessToken(ReissueAccessTokenCommand reissueAccessTokenCommand) {
		String refreshToken = reissueAccessTokenCommand.getRefreshToken();

		// 1. Refresh Token 유효성 검증
		if (!jwtTokenProvider.validateToken(refreshToken)) {
			throw new AuthException(AuthError.JWT_INVALID);
		}

		// 2. Refresh Token 에서 사용자 이름 추출
		String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

		// 3. 사용자 정보 로드
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		// 4. 새로운 Access Token 발급
		String newAccessToken = jwtTokenProvider.generateAccessToken(userDetails);

		// 5. 재발급된 Access Token 정보 담아서 응답 DTO 반환
		return LoginDto.builder()
			.accessToken(newAccessToken)
			.refreshToken(refreshToken)
			.accessTokenExpiration(jwtTokenProvider.getExpiration(newAccessToken))
			.refreshTokenExpiration(jwtTokenProvider.getExpiration(refreshToken))
			.build();
	}

}