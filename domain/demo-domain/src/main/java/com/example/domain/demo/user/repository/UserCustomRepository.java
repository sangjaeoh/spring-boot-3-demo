package com.example.domain.demo.user.repository;

import java.util.List;

import com.example.domain.demo.user.domain.User;

public interface UserCustomRepository {

	List<User> findUsersByName(String name);
}
