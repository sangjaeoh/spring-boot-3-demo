package com.example.api.demo.rest.user.contoller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.rest.common.ApiResult;
import com.example.api.demo.rest.common.DisableSwaggerSecurity;
import com.example.api.demo.rest.common.RequestUser;
import com.example.api.demo.rest.user.contoller.request.CreateUserRequest;
import com.example.api.demo.rest.user.contoller.request.ReplaceUserRequest;
import com.example.api.demo.rest.user.contoller.request.UpdateUserRequest;
import com.example.api.demo.rest.user.contoller.response.UserCreateResponse;
import com.example.api.demo.rest.user.contoller.response.UserResponse;
import com.example.domain.demo.common.pagination.CustomPageRequest;
import com.example.domain.demo.common.pagination.PageResponse;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.service.UserCommandService;
import com.example.domain.demo.user.service.UserQueryService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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

	// todo
	// SearchUserCriteria 를 직접 쓰는것보다 원하는 파라미터만 string 필드으로 받고 페이지도 pageable 이나 page, size, List<String> sort 로 받는 객체 정의하는게 좋을듯
	// 아래와 같이 사용하면 되긴 하지만 좋은 방법인지는 의문이 듬
	// 따로 request 객체를 정의해서 사용할때는 필드와 sort 를 어떻게 validate 할지는 고민 필요
	@Operation(summary = "Search users with conditions and pagination",
		parameters = {
			@Parameter(name = "createdAt", description = "Created date condition (e.g., '2023-01-01T00:00:00,GT')", example = "2023-01-01T00:00:00,GT"),
			@Parameter(name = "age", description = "Age condition (e.g., '30,GT' or '20,30,BETWEEN')", example = "30,GT"),
			@Parameter(name = "page", description = "Page number", example = "0", schema = @Schema(type = "integer")),
			@Parameter(name = "size", description = "Page size", example = "20", schema = @Schema(type = "integer")),
			@Parameter(name = "sort", description = "Sort field and direction (e.g., 'age,asc')", example = "age,asc")
		})
	@GetMapping("/tt")
	public ResponseEntity<ApiResult<PageResponse<UserResponse>>> getUsers(
		@Parameter(hidden = true) @ModelAttribute SearchUserCriteria criteria
	) {

		SearchUserCriteria build = SearchUserCriteria.builder()
			.age("25,GT")
			.pageRequest(CustomPageRequest.of(1, 10, Arrays.asList("age,asc"), SearchUserCriteria.SortField.class))
			.build();

		return null;
		// PageResponse<User> search = userQueryService.search(build);
		// List<UserResponse> list = search.getItems().stream().map(UserResponse::from).toList();
		// PageResponse<UserResponse> userResponsePageResponse = PageResponse.of(list, 1, 10, 1);
		// return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(userResponsePageResponse));
	}

}
