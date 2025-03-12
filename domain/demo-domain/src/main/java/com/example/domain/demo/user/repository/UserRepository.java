package com.example.domain.demo.user.repository;

import java.util.Optional;

import com.example.domain.demo.user.domain.User;

public interface UserRepository extends UserJpaRepository, UserCustomRepository {

	Optional<User> findByEmail(String email);
}
