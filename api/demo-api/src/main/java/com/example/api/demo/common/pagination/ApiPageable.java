package com.example.api.demo.common.pagination;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
	@Parameter(name = "page", description = "페이지 번호 (1..N) [기본값: 1]", example = "1", schema = @Schema(type = "integer", defaultValue = "1", nullable = true)),
	@Parameter(name = "size", description = "페이지 사이즈 (0..100) [기본값: 10]", example = "10", schema = @Schema(type = "integer", defaultValue = "10", nullable = true)),
	@Parameter(name = "sort", description = "정렬 (컬럼,asc|desc)", schema = @Schema(type = "array", name = "정렬 (컬럼,asc|desc)")),
	@Parameter(name = "pageable", hidden = true)})
public @interface ApiPageable {
}