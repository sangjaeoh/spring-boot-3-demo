package com.example.domain.demo.user.service.query;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;

import com.example.domain.demo.common.pagination.CustomPageRequest;
import com.example.domain.demo.common.pagination.SortableField;
import com.example.domain.demo.common.pagination.ValidSortableField;
import com.example.domain.demo.common.search.FieldCondition;
import com.example.domain.demo.common.search.ValidFieldCondition;
import com.example.domain.demo.common.search.Operator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchUserPaginationCriteria {

	@ValidFieldCondition(allowedOperators = {Operator.EQUALS, Operator.GT, Operator.LT, Operator.GTE, Operator.LTE,
		Operator.BETWEEN})
	private FieldCondition<LocalDateTime> createdAt;

	@ValidFieldCondition(allowedOperators = {Operator.EQUALS, Operator.LIKE})
	private FieldCondition<String> email;

	@ValidFieldCondition(allowedOperators = {Operator.EQUALS, Operator.LIKE})
	private FieldCondition<String> name;

	@ValidFieldCondition(allowedOperators = {Operator.EQUALS, Operator.GT, Operator.LT, Operator.GTE, Operator.LTE,
		Operator.BETWEEN})
	private FieldCondition<Integer> age;

	@ValidSortableField(enumClass = SortField.class)
	private CustomPageRequest<SortField> pageRequest;

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private FieldCondition<LocalDateTime> createdAt;
		private FieldCondition<String> email;
		private FieldCondition<String> name;
		private FieldCondition<Integer> age;
		private CustomPageRequest<SortField> pageRequest;

		public Builder createdAt(String rawValue) {
			if (rawValue != null) {
				this.createdAt = new FieldCondition<>(rawValue, LocalDateTime::parse);
			}
			return this;
		}

		public Builder email(String rawValue) {
			if (rawValue != null) {
				this.email = new FieldCondition<>(rawValue, s -> s);
			}
			return this;
		}

		public Builder name(String rawValue) {
			if (rawValue != null) {
				this.name = new FieldCondition<>(rawValue, s -> s);
			}
			return this;
		}

		public Builder age(String rawValue) {
			if (rawValue != null) {
				this.age = new FieldCondition<>(rawValue, Integer::parseInt);
			}
			return this;
		}

		public Builder pageRequest(CustomPageRequest<SortField> pageRequest) {
			this.pageRequest = pageRequest;
			return this;
		}

		public Builder createdAt(FieldCondition<LocalDateTime> fieldCondition) {
			this.createdAt = fieldCondition;
			return this;
		}

		public Builder email(FieldCondition<String> fieldCondition) {
			this.email = fieldCondition;
			return this;
		}

		public Builder name(FieldCondition<String> fieldCondition) {
			this.name = fieldCondition;
			return this;
		}

		public Builder age(FieldCondition<Integer> fieldCondition) {
			this.age = fieldCondition;
			return this;
		}

		public Builder pageRequest(Pageable pageable) {
			this.pageRequest = CustomPageRequest.of(pageable);
			return this;
		}

		public SearchUserPaginationCriteria build() {
			SearchUserPaginationCriteria criteria = new SearchUserPaginationCriteria();
			criteria.createdAt = this.createdAt;
			criteria.email = this.email;
			criteria.name = this.name;
			criteria.age = this.age;
			criteria.pageRequest = this.pageRequest;
			return criteria;
		}
	}

	@Getter
	public enum SortField implements SortableField {
		AGE("age", "나이"),
		NAME("name", "이름"),
		CREATED_AT("createdAt", "생성날짜"),
		;

		private final String fieldName;
		private final String description;

		SortField(String fieldName, String description) {
			this.fieldName = fieldName;
			this.description = description;
		}
	}

}
