package com.example.api.demo.user.contoller.request;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

import com.example.domain.demo.common.pagination.CustomPageRequest;
import com.example.domain.demo.user.service.query.SearchUserPaginationCriteria;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ParameterObject
public class SearchUserRequest {

	@Parameter(name = "createdAt", description = "EQUALS,GT,LT,GTE,LTE,BETWEEN", example = "2023-01-01T00:00:00,GT")
	private String createdAt;

	@Parameter(name = "email", description = "EQUALS,LIKE", example = "email,LIKE")
	private String email;

	@Parameter(name = "name", description = "EQUALS,LIKE", example = "killdong,LIKE")
	private String name;

	@Parameter(name = "age", description = "EQUALS,GT,LT,GTE,LTE,BETWEEN", example = "30,50,BETWEEN")
	private String age;

	public SearchUserPaginationCriteria toCriteria(Pageable pageable) {
		return SearchUserPaginationCriteria.builder()
			.createdAt(createdAt)
			.email(email)
			.name(name)
			.age(age)
			.pageRequest(pageable)
			.build();

	}
}
