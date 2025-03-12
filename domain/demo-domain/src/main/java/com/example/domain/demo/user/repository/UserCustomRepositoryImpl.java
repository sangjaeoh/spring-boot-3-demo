package com.example.domain.demo.user.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.example.domain.demo.config.QuerydslConfig;
import com.example.domain.demo.user.domain.QUser;
import com.example.domain.demo.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

	private final JPAQueryFactory queryFactory;

	public UserCustomRepositoryImpl(@Qualifier(QuerydslConfig.JPA_QUERY_FACTORY) JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<User> findUsersByName(String name) {
		QUser user = QUser.user;
		return queryFactory.selectFrom(user).where(user.name.eq(name)).fetch();
	}
}
