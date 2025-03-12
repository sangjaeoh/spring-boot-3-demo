package com.example.api.demo.rest.auth.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class CustomUserDetails implements UserDetails {

	private User user;

	public static CustomUserDetails from(User user) {
		return CustomUserDetails.builder().user(user).build();
	}

	@Override
	public String getUsername() {
		return this.user.getEmail();
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.user.getRoles()
			.stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
			.collect(Collectors.toList());
	}
}
