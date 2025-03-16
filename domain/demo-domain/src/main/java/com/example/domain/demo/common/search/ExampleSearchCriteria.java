package com.example.domain.demo.common.search;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExampleSearchCriteria {

	// description = "Name condition (e.g., 'John,EQUALS' or 'son,LIKE')", example = "John,EQUALS"
	@ValidFieldCondition(allowedOperators = {Operator.EQUALS, Operator.LIKE})
	private FieldCondition<String> name;

	// description = "Created date condition (e.g., '2023-01-01T00:00:00,GT' or '2023-01-01T00:00:00,2023-12-31T23:59:59,BETWEEN')", example = "2023-01-01T00:00:00,GT"
	@ValidFieldCondition(allowedOperators = {Operator.EQUALS, Operator.GT, Operator.LT, Operator.GTE, Operator.LTE,
		Operator.BETWEEN})
	private FieldCondition<LocalDateTime> createdDate;

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private FieldCondition<String> name;
		private FieldCondition<LocalDateTime> createdDate;

		public Builder name(String rawValue) {
			if (rawValue != null) {
				this.name = new FieldCondition<>(rawValue, s -> s);
			}
			return this;
		}

		public Builder createdDate(String rawValue) {
			if (rawValue != null) {
				this.createdDate = new FieldCondition<>(rawValue, LocalDateTime::parse);
			}
			return this;
		}

		public ExampleSearchCriteria build() {
			ExampleSearchCriteria criteria = new ExampleSearchCriteria();
			criteria.name = this.name;
			criteria.createdDate = this.createdDate;
			return criteria;
		}
	}

	// 컨트롤에서 @ModelAttribute 로 바인딩 하려면 커스텀 setter 필요.
	public void setName(String name) {
		if (name != null) {
			this.name = new FieldCondition<>(name, s -> s);
		}
	}

	public void setCreatedDate(String createdDate) {
		if (createdDate != null) {
			this.createdDate = new FieldCondition<>(createdDate, LocalDateTime::parse);
		}
	}

}
