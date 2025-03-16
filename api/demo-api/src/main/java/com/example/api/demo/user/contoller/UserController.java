package com.example.api.demo.user.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.common.ApiPageResponse;
import com.example.api.demo.common.ApiResult;
import com.example.api.demo.common.DisableSwaggerSecurity;
import com.example.api.demo.common.RequestUser;
import com.example.api.demo.common.pagination.ApiPageable;
import com.example.api.demo.user.contoller.request.CreateUserRequest;
import com.example.api.demo.user.contoller.request.ReplaceUserRequest;
import com.example.api.demo.user.contoller.request.SearchUserRequest;
import com.example.api.demo.user.contoller.request.UpdateUserRequest;
import com.example.api.demo.user.contoller.response.UserCreateResponse;
import com.example.api.demo.user.contoller.response.UserResponse;
import com.example.domain.demo.common.pagination.PageResponse;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.service.UserCommandService;
import com.example.domain.demo.user.service.UserQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저", description = "유저 컨트롤러")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;

	@Operation(summary = "유저 조회", description = "유저를 조회한다.")
	@ApiResponse(responseCode = "200", description = "유저 조회 성공")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResult<UserResponse>> get(@PathVariable Long id) {
		User user = userQueryService.getOrThrowById(id);
		UserResponse userResponse = UserResponse.from(user);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(userResponse));
	}

	@Operation(summary = "유저 생성", description = "유저를 생성한다.")
	@ApiResponse(responseCode = "201", description = "유저 생성 성공")
	@DisableSwaggerSecurity
	@PostMapping
	public ResponseEntity<ApiResult<UserCreateResponse>> create(@Valid @RequestBody CreateUserRequest request) {
		Long id = userCommandService.create(request.toCommand());
		UserCreateResponse userCreateResponse = UserCreateResponse.from(id);

		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResult.success(userCreateResponse));
	}

	@Operation(summary = "유저 전체 수정", description = "유저를 전체 수정한다.")
	@ApiResponse(responseCode = "204", description = "유저 전체 수정 성공")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResult<Void>> replace(@PathVariable Long id,
		@Valid @RequestBody ReplaceUserRequest request) {
		userCommandService.replace(id, request.toCommand());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResult.success());
	}

	@Operation(summary = "유저 수정", description = "유저를 수정한다.")
	@ApiResponse(responseCode = "204", description = "유저 수정 성공")
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResult<Void>> update(@PathVariable Long id,
		@Valid @RequestBody UpdateUserRequest request) {
		userCommandService.update(id, request.toCommand());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResult.success());
	}

	@Operation(summary = "내 정보 조회", description = "내 정보를 조회한다.")
	@ApiResponse(responseCode = "200", description = "내 정보 조회 성공")
	@GetMapping("/me")
	public ResponseEntity<ApiResult<UserResponse>> getMe(@Parameter(hidden = true) @RequestUser User user) {
		UserResponse userResponse = UserResponse.from(user);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(userResponse));
	}

	@ApiPageable
	@Operation(summary = "유저 검색", description = "유저를 검색한다..", parameters = {
		@Parameter(name = "sort", description = "정렬 (컬럼,asc|desc), name, email, age, createdAt"),
	})
	@GetMapping("/search")
	public ResponseEntity<ApiResult<ApiPageResponse<UserResponse>>> search(SearchUserRequest request,
		Pageable pageable) {

		PageResponse<User> searchResult = userQueryService.searchPagination(request.toCriteria(pageable));
		ApiPageResponse<UserResponse> response = ApiPageResponse.of(
			searchResult.getItems().stream().map(UserResponse::from).toList(),
			searchResult.getMeta().getPage(),
			searchResult.getMeta().getSize(),
			searchResult.getMeta().getTotalCount()
		);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(response));

	}

}
