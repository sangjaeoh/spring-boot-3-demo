package com.example.domain.demo.user.repository;

import java.util.List;

import com.example.domain.demo.common.pagination.PageResponse;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.service.query.SearchUserCriteria;

public interface UserCustomRepository {

	List<User> findUsersByName(String name);

	PageResponse<User> searchPagination(SearchUserCriteria criteria);
}
