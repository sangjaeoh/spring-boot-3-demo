package com.example.api.demo.auth.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.api.demo.auth.provider.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private final JwtTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			// Request Header 에서 토큰 추출
			String jwt = jwtTokenProvider.resolveToken(request);
			if (jwt != null && jwtTokenProvider.validateToken(jwt)) {

				// 유저 조회
				String username = jwtTokenProvider.getUsernameFromToken(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				// 인증 토큰 생성
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

				// 인증 정보에 request 정보 설정
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Security Context 에 인증 정보 저장
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Security Context 에 인증을 설정할 수 없습니다.", e);
		}

		// 다음 필터로 요청 전달
		filterChain.doFilter(request, response);
	}
}