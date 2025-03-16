package com.example.domain.demo.common.pagination;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import lombok.Getter;

@Getter
public class CursorPageRequest<T extends Enum<T> & SortableField> {
	private static final int DEFAULT_SIZE = 10;
	private static final int MAX_SIZE = 1000;

	private final String cursor;
	private final int size;
	private final Sort sort;

	private CursorPageRequest(String cursor, int size, Sort sort) {
		if (size < 1) {
			size = DEFAULT_SIZE;
		} else if (size > MAX_SIZE) {
			size = MAX_SIZE;
		}

		this.cursor = cursor;
		this.size = size;
		this.sort = sort != null ? sort : Sort.unsorted();
	}

	public static <T extends Enum<T> & SortableField> CursorPageRequest<T> ofDefaults(String cursor) {
		return new CursorPageRequest<>(cursor, DEFAULT_SIZE, Sort.unsorted());
	}

	public static <T extends Enum<T> & SortableField> CursorPageRequest<T> ofFirstPage() {
		return new CursorPageRequest<>(null, DEFAULT_SIZE, Sort.unsorted());
	}

	public static <T extends Enum<T> & SortableField> CursorPageRequest<T> of(int size) {
		return new CursorPageRequest<>(null, size, null);
	}

	public static <T extends Enum<T> & SortableField> CursorPageRequest<T> of(String cursor, int size) {
		return new CursorPageRequest<>(cursor, size, null);
	}

	public static <T extends Enum<T> & SortableField> CursorPageRequest<T> of(String cursor, int size,
		List<String> sortParams, Class<T> enumClass) {
		Sort sort = SortUtils.parseSortParams(sortParams, enumClass);
		return new CursorPageRequest<>(cursor, size, sort);
	}

	public static <T extends Enum<T> & SortableField> CursorPageRequest<T> of(String cursor, int size, Sort sort,
		Class<T> enumClass) {
		SortUtils.validateSort(sort, enumClass);
		return new CursorPageRequest<>(cursor, size, sort);
	}

	public boolean isFirstPage() {
		return cursor == null;
	}

	// 커서 Order 추출
	public Optional<Sort.Order> getCursorOrder() {
		if (sort == null || sort.isUnsorted()) {
			return Optional.empty();
		}
		return Optional.of(sort.iterator().next());
	}
}
