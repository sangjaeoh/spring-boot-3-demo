package com.example.domain.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.demo.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
