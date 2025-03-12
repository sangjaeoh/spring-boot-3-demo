package com.example.domain.demo.user.authority.domain;

public enum Role {
	COMMON("COMMON"),
	ADMIN("ADMIN");

	private final String authority;

	Role(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}
}
