package com.example.domain.demo.common.pagination;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;

@Getter
public class CustomPageRequest<T extends Enum<T> & SortableField> implements Pageable {
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;
	private static final int MAX_SIZE = 1000;

	private final int page; // 1-based
	private final int size;
	private final Sort sort;

	private CustomPageRequest(int page, int size, Sort sort) {
		if (page < 1) {
			page = DEFAULT_PAGE;
		}

		if (size < 1) {
			size = DEFAULT_SIZE;
		} else if (size > MAX_SIZE) {
			size = MAX_SIZE;
		}

		this.page = page;
		this.size = size;
		this.sort = sort != null ? sort : Sort.unsorted();
	}

	public static <T extends Enum<T> & SortableField> CustomPageRequest<T> ofDefaults() {
		return new CustomPageRequest<>(DEFAULT_PAGE, DEFAULT_SIZE, Sort.unsorted());
	}

	public static <T extends Enum<T> & SortableField> CustomPageRequest<T> of(int page, int size) {
		return new CustomPageRequest<>(page, size, Sort.unsorted());
	}

	public static <T extends Enum<T> & SortableField> CustomPageRequest<T> of(int page, int size, Sort sort,
		Class<T> enumClass) {
		SortUtils.validateSort(sort, enumClass);
		return new CustomPageRequest<>(page, size, sort);
	}

	public static <T extends Enum<T> & SortableField> CustomPageRequest<T> of(int page, int size,
		List<String> sortParams, Class<T> enumClass) {
		return new CustomPageRequest<>(page, size, SortUtils.parseSortParams(sortParams, enumClass));
	}

	public static <T extends Enum<T> & SortableField> CustomPageRequest<T> of(Pageable pageable) {
		return new CustomPageRequest<>(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
	}

	@Override
	public int getPageNumber() {
		return page - 1;
	}

	@Override
	public int getPageSize() {
		return size;
	}

	@Override
	public long getOffset() {
		return (long)(page - 1) * (long)size;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		return new CustomPageRequest<>(page + 1, size, sort);
	}

	@Override
	public Pageable previousOrFirst() {
		return page > 1 ? new CustomPageRequest<>(page - 1, size, sort) : this;
	}

	@Override
	public Pageable first() {
		return new CustomPageRequest<>(1, size, sort);
	}

	@Override
	public Pageable withPage(int pageNumber) {
		return new CustomPageRequest<>(pageNumber, size, sort);
	}

	@Override
	public boolean hasPrevious() {
		return page > 1;
	}
}