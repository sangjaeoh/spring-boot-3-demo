package com.example.domain.demo.user.repository.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.domain.demo.common.search.FieldCondition;
import com.example.domain.demo.user.domain.QUser;
import com.example.domain.demo.user.service.query.SearchUserPaginationCriteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

public class SearchPaginationUserQueryBuilder {

	private static QUser user = QUser.user;

	public static BooleanBuilder getBooleanBuilder(SearchUserPaginationCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();

		if (criteria.getCreatedAt() != null) {
			FieldCondition<LocalDateTime> cond = criteria.getCreatedAt();
			LocalDateTime value = cond.getValue();
			switch (cond.getOperator()) {
				case EQUALS:
					builder.and(user.createdAt.eq(value));
					break;
				case GT:
					builder.and(user.createdAt.gt(value));
					break;
				case LT:
					builder.and(user.createdAt.lt(value));
					break;
				case GTE:
					builder.and(user.createdAt.goe(value));
					break;
				case LTE:
					builder.and(user.createdAt.loe(value));
					break;
				case BETWEEN:
					builder.and(user.createdAt.between(value, cond.getValueTo()));
					break;
			}
		}

		if (criteria.getEmail() != null) {
			FieldCondition<String> cond = criteria.getEmail();
			String value = cond.getValue();
			switch (cond.getOperator()) {
				case EQUALS:
					builder.and(user.email.eq(value));
					break;
				case LIKE:
					builder.and(user.email.like("%" + value + "%"));
					break;
			}
		}

		if (criteria.getName() != null) {
			FieldCondition<String> cond = criteria.getName();
			String value = cond.getValue();
			switch (cond.getOperator()) {
				case EQUALS:
					builder.and(user.name.eq(value));
					break;
				case LIKE:
					builder.and(user.name.like("%" + value + "%"));
					break;
			}
		}

		if (criteria.getAge() != null) {
			FieldCondition<Integer> cond = criteria.getAge();
			Integer value = cond.getValue();
			switch (cond.getOperator()) {
				case EQUALS:
					builder.and(user.age.eq(value));
					break;
				case GT:
					builder.and(user.age.gt(value));
					break;
				case LT:
					builder.and(user.age.lt(value));
					break;
				case GTE:
					builder.and(user.age.goe(value));
					break;
				case LTE:
					builder.and(user.age.loe(value));
					break;
				case BETWEEN:
					builder.and(user.age.between(value, cond.getValueTo()));
					break;
			}
		}

		return builder;
	}

	public static OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
		List<OrderSpecifier<?>> specifiers = new ArrayList<>();

		if (!pageable.getSort().isEmpty()) {
			for (Sort.Order order : pageable.getSort()) {
				String property = order.getProperty();
				Order direction = order.isAscending() ? Order.ASC : Order.DESC;

				switch (property) {
					case "createdAt":
						specifiers.add(new OrderSpecifier<>(direction, user.createdAt));
						break;
					case "email":
						specifiers.add(new OrderSpecifier<>(direction, user.email));
						break;
					case "name":
						specifiers.add(new OrderSpecifier<>(direction, user.name));
						break;
					case "age":
						specifiers.add(new OrderSpecifier<>(direction, user.age));
						break;
				}
			}
		}

		return specifiers.toArray(new OrderSpecifier[0]);
	}

}
