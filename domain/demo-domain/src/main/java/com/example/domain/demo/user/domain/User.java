package com.example.domain.demo.user.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.domain.demo.domain.BaseEntity;
import com.example.domain.demo.user.authority.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private Integer age;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<UserAuthority> userAuthorities;

	public List<Role> getRoles() {
		return userAuthorities.stream().map(UserAuthority::getRole).collect(Collectors.toList());
	}

	public void updatePassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("암호는 필수입니다.");
		}
		this.password = password;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateAge(int age) {
		if (age < 0) {
			throw new IllegalArgumentException("나이는 0살 이상이어야 합니다.");
		}
		this.age = age;
	}

}
