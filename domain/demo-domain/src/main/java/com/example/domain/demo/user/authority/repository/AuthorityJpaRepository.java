package com.example.domain.demo.user.authority.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.demo.user.authority.domain.Authority;
import com.example.domain.demo.user.authority.domain.Role;

public interface AuthorityJpaRepository extends JpaRepository<Authority, Long> {

	Optional<Authority> findByRole(Role role);
}
