package com.example.domain.demo.common.pagination;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageResponse<T> {
	private final List<T> items;
	private final Meta meta;

	public static <T> PageResponse<T> of(Page<T> page) {
		if (page == null) {
			return empty();
		}

		return PageResponse.<T>builder()
			.items(page.getContent())
			.meta(Meta.builder()
				.itemCount(page.getNumberOfElements())
				.totalCount(page.getTotalElements())
				.totalPages(page.getTotalPages())
				.page(page.getNumber() + 1) // 1-based
				.size(page.getSize())
				.hasNext(page.hasNext())
				.hasPrevious(page.hasPrevious())
				.build())
			.build();
	}

	public static <T> PageResponse<T> of(Slice<T> slice) {
		if (slice == null) {
			return empty();
		}

		return PageResponse.<T>builder()
			.items(slice.getContent())
			.meta(Meta.builder()
				.itemCount(slice.getNumberOfElements())
				.totalCount(-1)
				.totalPages(-1)
				.page(slice.getNumber() + 1) // 1-based
				.size(slice.getSize())
				.hasNext(slice.hasNext())
				.hasPrevious(slice.hasPrevious())
				.build())
			.build();
	}

	/**
	 * 리스트와 페이지 정보로부터 페이지 응답을 생성합니다.
	 * @param items 항목 리스트
	 * @param page 페이지 번호 (1-based)
	 * @param size 페이지 크기
	 * @param totalCount 총 항목 수
	 * @return 페이지 응답
	 */
	public static <T> PageResponse<T> of(List<T> items, int page, int size, long totalCount) {
		if (items == null) {
			items = Collections.emptyList();
		}

		int totalPages = size > 0 ? (int)Math.ceil((double)totalCount / size) : 0;
		boolean hasNext = page < totalPages;
		boolean hasPrevious = page > 1;

		return PageResponse.<T>builder()
			.items(items)
			.meta(Meta.builder()
				.itemCount(items.size())
				.totalCount(totalCount)
				.totalPages(totalPages)
				.page(page)
				.size(size)
				.hasNext(hasNext)
				.hasPrevious(hasPrevious)
				.build())
			.build();
	}

	public static <T> PageResponse<T> empty() {
		return PageResponse.<T>builder()
			.items(Collections.emptyList())
			.meta(Meta.builder()
				.itemCount(0)
				.totalCount(0)
				.totalPages(0)
				.page(1)
				.size(0)
				.hasNext(false)
				.hasPrevious(false)
				.build())
			.build();
	}

	@Getter
	@Builder
	public static class Meta {
		private final int itemCount;
		private final long totalCount;
		private final int totalPages;
		private final int page;
		private final int size;
		private final boolean hasNext;
		private final boolean hasPrevious;
	}
}
