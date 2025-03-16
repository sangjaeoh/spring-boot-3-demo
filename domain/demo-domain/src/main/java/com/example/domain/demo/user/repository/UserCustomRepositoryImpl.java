package com.example.domain.demo.user.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.example.domain.demo.common.pagination.PageResponse;
import com.example.domain.demo.config.QuerydslConfig;
import com.example.domain.demo.user.domain.QUser;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.repository.builder.SearchPaginationUserQueryBuilder;
import com.example.domain.demo.user.service.query.SearchUserPaginationCriteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

	private final JPAQueryFactory queryFactory;
	private final QUser user = QUser.user;

	public UserCustomRepositoryImpl(@Qualifier(QuerydslConfig.JPA_QUERY_FACTORY) JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<User> findUsersByName(String name) {
		return queryFactory.selectFrom(user).where(user.name.eq(name)).fetch();
	}

	public PageResponse<User> searchUserPagination(SearchUserPaginationCriteria criteria) {
		BooleanBuilder where = SearchPaginationUserQueryBuilder.getBooleanBuilder(criteria);

		JPAQuery<User> query = queryFactory.selectFrom(user)
			.where(where)
			.offset(criteria.getPageRequest().getOffset())
			.limit(criteria.getPageRequest().getPageSize());

		OrderSpecifier<?>[] orderBy = SearchPaginationUserQueryBuilder.getOrderSpecifiers(criteria.getPageRequest());
		if (orderBy.length > 0) {
			query.orderBy(orderBy);
		}

		List<User> content = query.fetch();
		Long total = queryFactory.select(user.count())
			.from(user)
			.where(where)
			.fetchOne();

		PageImpl<User> page = new PageImpl<>(content, criteria.getPageRequest(), total);
		return PageResponse.of(page);
	}

	private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
		List<OrderSpecifier<?>> specifiers = new ArrayList<>();

		if (!pageable.getSort().isEmpty()) {
			for (Sort.Order order : pageable.getSort()) {
				String property = order.getProperty();
				Order direction = order.isAscending() ? Order.ASC : Order.DESC;

				switch (property) {
					case "createdAt":
						specifiers.add(new OrderSpecifier<>(direction, user.createdAt));
						break;
					case "age":
						specifiers.add(new OrderSpecifier<>(direction, user.age));
						break;
					default:
						throw new IllegalArgumentException("Unsupported sort property: " + property);
				}
			}
		}

		return specifiers.toArray(new OrderSpecifier[0]);
	}

}
