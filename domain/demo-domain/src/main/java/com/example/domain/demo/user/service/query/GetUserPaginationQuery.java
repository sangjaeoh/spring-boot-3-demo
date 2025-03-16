package com.example.domain.demo.user.service.query;

import com.example.domain.demo.common.pagination.CustomPageRequest;
import com.example.domain.demo.common.pagination.SortableField;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUserPaginationQuery {

	private CustomPageRequest<SortField> pageRequest;

	public enum SortField implements SortableField {
		NAME("name", "이름"),
		CREATED_AT("createdAt", "생성날짜"),
		;

		private final String fieldName;
		private final String description;

		SortField(String fieldName, String description) {
			this.fieldName = fieldName;
			this.description = description;
		}

		@Override
		public String getFieldName() {
			return fieldName;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}
}
