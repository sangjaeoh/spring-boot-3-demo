package com.example.api.demo.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.api.demo.auth.domain.CustomUserDetails;
import com.example.api.demo.auth.exception.AuthError;
import com.example.api.demo.auth.exception.AuthException;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.service.UserQueryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserQueryService userQueryService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userQueryService.getByEmail(username);
		User user = optionalUser.orElseThrow(() -> new AuthException(AuthError.UNAUTHORIZED));

		return CustomUserDetails.from(user);
	}
}
