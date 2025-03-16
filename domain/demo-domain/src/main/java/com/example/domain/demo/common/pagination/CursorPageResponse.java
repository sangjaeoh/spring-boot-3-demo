package com.example.domain.demo.common.pagination;

import java.util.Collections;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CursorPageResponse<T> {
	private final List<T> items;
	private final Meta meta;

	public static <T, E extends Enum<E> & SortableField> CursorPageResponse<T> of(List<T> items, int size,
		String nextCursor) {
		if (items == null) {
			items = Collections.emptyList();
		}

		boolean hasNext = nextCursor != null && !nextCursor.isEmpty();
		return CursorPageResponse.<T>builder()
			.items(items)
			.meta(Meta.builder()
				.itemCount(items.size())
				.size(size)
				.nextCursor(nextCursor)
				.hasNext(hasNext)
				.build())
			.build();
	}

	public static <T> CursorPageResponse<T> empty() {
		return CursorPageResponse.<T>builder()
			.items(Collections.emptyList())
			.meta(Meta.builder()
				.itemCount(0)
				.size(0)
				.nextCursor(null)
				.hasNext(false)
				.build())
			.build();
	}

	@Getter
	@Builder
	public static class Meta {
		private final int itemCount;
		private final int size;
		private final String nextCursor;
		private final boolean hasNext;
	}
}