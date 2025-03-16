package com.example.domain.demo.common.pagination;

import org.springframework.data.domain.Sort;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SortableFieldValidator
	implements ConstraintValidator<ValidSortableField, CustomPageRequest<? extends SortableField>> {

	private Class<? extends Enum<? extends SortableField>> enumClass;

	@Override
	public void initialize(ValidSortableField constraintAnnotation) {
		this.enumClass = constraintAnnotation.enumClass();
	}

	@Override
	public boolean isValid(CustomPageRequest<? extends SortableField> pageRequest, ConstraintValidatorContext context) {
		if (pageRequest == null || pageRequest.getSort() == null || pageRequest.getSort().isUnsorted()) {
			return true; // null 이거나 정렬이 없는 경우는 유효하다고 간주
		}

		try {
			for (Sort.Order order : pageRequest.getSort()) {
				boolean isValid = false;
				for (Enum<? extends SortableField> enumValue : enumClass.getEnumConstants()) {
					SortableField field = (SortableField)enumValue;
					if (field.getFieldName().equals(order.getProperty())) {
						isValid = true;
						break;
					}
				}
				if (!isValid) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}