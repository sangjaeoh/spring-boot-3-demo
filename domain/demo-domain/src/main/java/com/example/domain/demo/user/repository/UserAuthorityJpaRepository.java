package com.example.domain.demo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.demo.user.domain.UserAuthority;

public interface UserAuthorityJpaRepository extends JpaRepository<UserAuthority, Long> {

	Optional<UserAuthority> findByUserId(Long userId);

	Optional<UserAuthority> findByAuthorityId(Long authorityId);
}
