package com.example.domain.demo.user.service.query;

import java.time.LocalDateTime;

import com.example.domain.demo.common.search.FieldCondition;
import com.example.domain.demo.user.domain.QUser;
import com.querydsl.core.BooleanBuilder;

public class SearchUserQueryBuilder {
	public static BooleanBuilder build(SearchUserPaginationCriteria criteria) {
		BooleanBuilder builder = new BooleanBuilder();
		QUser user = QUser.user;

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
				default:
					throw new IllegalArgumentException("Invalid operator for createdDate");
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
				default:
					throw new IllegalArgumentException("Invalid operator for age");
			}
		}

		return builder;
	}
}
