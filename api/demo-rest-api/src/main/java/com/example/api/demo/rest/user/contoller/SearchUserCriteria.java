package com.example.api.demo.rest.user.contoller;

import java.time.LocalDateTime;

import com.example.domain.demo.common.pagination.CustomPageRequest;
import com.example.domain.demo.common.pagination.SortableField;
import com.example.domain.demo.common.search.FieldCondition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchUserCriteria {

	// description = "Created date condition (e.g., '2023-01-01T00:00:00,GT' or '2023-01-01T00:00:00,2023-12-31T23:59:59,BETWEEN')",example = "2023-01-01T00:00:00,GT"
	private FieldCondition<LocalDateTime> createdAt;

	// description = "Age condition (e.g., '30,GT' or '20,30,BETWEEN')", example = "30,GT"
	private FieldCondition<Integer> age;

	private CustomPageRequest<SortField> pageRequest;

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private FieldCondition<LocalDateTime> createdAt;
		private FieldCondition<Integer> age;
		private CustomPageRequest<SortField> pageRequest;

		public Builder createdAt(String rawValue) {
			if (rawValue != null) {
				this.createdAt = new FieldCondition<>(rawValue, LocalDateTime::parse);
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

		public SearchUserCriteria build() {
			SearchUserCriteria criteria = new SearchUserCriteria();
			criteria.createdAt = this.createdAt;
			criteria.age = this.age;
			criteria.pageRequest = this.pageRequest;
			return criteria;
		}
	}

	@Schema(description = "Age condition (e.g., '30,GT' or '20,30,BETWEEN')", example = "30,GT")
	public void setAge(String age) {
		if (age != null) {
			this.age = new FieldCondition<>(age, Integer::parseInt);
		}
	}

	@Getter
	public enum SortField implements SortableField {
		AGE("age", "나이"),
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
