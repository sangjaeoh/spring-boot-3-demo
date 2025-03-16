package com.example.api.demo.common.pagination;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ParameterObject
public abstract class AbstractPageRequest {

	@Parameter(name = "page", description = "페이지 번호", example = "1")
	private Integer page;

	@Parameter(name = "size", description = "페이지 사이즈", example = "10")
	private Integer size;

}
