package com.example.domain.demo.common.pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

public class SortUtils {

	private SortUtils() {
	}

	public static <T extends Enum<T> & SortableField> Sort parseSortParams(List<String> sortParams,
		Class<T> enumClass) {
		if (sortParams == null || sortParams.isEmpty()) {
			return Sort.unsorted();
		}

		List<Sort.Order> orders = new ArrayList<>();
		for (String param : sortParams) {
			String[] parts = param.split(",", 2);
			if (parts.length != 2) {
				throw new PaginationException(PaginationError.INVALID_FORMAT);
			}

			String fieldName = parts[0].trim().toUpperCase();
			Sort.Direction direction;
			try {
				direction = Sort.Direction.fromString(parts[1].trim());
			} catch (IllegalArgumentException e) {
				throw new PaginationException(PaginationError.INVALID_DIRECTION);
			}


			T field = validateAndGetField(fieldName, enumClass);
			orders.add(new Sort.Order(direction, field.getFieldName()));
		}
		return Sort.by(orders);
	}

	public static <T extends Enum<T> & SortableField> void validateSort(Sort sort, Class<T> enumClass) {
		if (sort == null || sort.isUnsorted()) {
			return;
		}
		for (Sort.Order order : sort) {
			boolean isValid = false;
			for (T field : enumClass.getEnumConstants()) {
				if (field.getFieldName().equals(order.getProperty())) {
					isValid = true;
					break;
				}
			}
			if (!isValid) {
				throw new PaginationException(CustomPaginationError.invalidField(order.getProperty(), enumClass.getSimpleName()));
			}
		}
	}

	private static <T extends Enum<T> & SortableField> T validateAndGetField(String fieldName, Class<T> enumClass) {
		try {
			return Enum.valueOf(enumClass, fieldName);
		} catch (IllegalArgumentException e) {
			throw new PaginationException(CustomPaginationError.invalidField(fieldName, enumClass.getSimpleName()));
		}
	}
}
