package com.example.domain.demo.user.domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.domain.demo.user.authority.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column
	private String name;

	@Column
	private Integer age;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<UserAuthority> userAuthorities;

	public List<Role> getRoles() {
		return userAuthorities.stream()
			.map(UserAuthority::getRole)
			.collect(Collectors.toList());
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
