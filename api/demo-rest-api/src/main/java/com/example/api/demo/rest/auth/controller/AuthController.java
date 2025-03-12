package com.example.api.demo.rest.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.rest.auth.controller.request.LoginRequest;
import com.example.api.demo.rest.auth.controller.request.ReissueAccessTokenRequest;
import com.example.api.demo.rest.auth.controller.response.LoginResponse;
import com.example.api.demo.rest.auth.controller.response.ReissueAccessTokenResponse;
import com.example.api.demo.rest.auth.service.AuthService;
import com.example.api.demo.rest.auth.service.dto.LoginDto;
import com.example.api.demo.rest.common.ApiResult;
import com.example.api.demo.rest.common.DisableSwaggerSecurity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "인증", description = "인증 컨트롤러")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "로그인", description = "로그인한다.")
	@ApiResponse(responseCode = "200", description = "로그인 성공")
	@DisableSwaggerSecurity
	@PostMapping("/login")
	public ResponseEntity<ApiResult<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
		LoginDto loginDto = authService.login(loginRequest.toCommand());
		LoginResponse loginResponse = LoginResponse.from(loginDto);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(loginResponse));
	}

	@Operation(summary = "인증 토큰 재발급", description = "인증 토큰을 재발급한다.")
	@ApiResponse(responseCode = "200", description = "인증 토큰 재발급 성공")
	@DisableSwaggerSecurity
	@PostMapping("/reissue-token")
	public ResponseEntity<ApiResult<ReissueAccessTokenResponse>> reissueAccessToken(
		@Valid @RequestBody ReissueAccessTokenRequest reissueAccessTokenRequest) {
		LoginDto loginDto = authService.reissueAccessToken(reissueAccessTokenRequest.toCommand());
		ReissueAccessTokenResponse reissueAccessTokenResponse = ReissueAccessTokenResponse.from(loginDto);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResult.success(reissueAccessTokenResponse));
	}
}