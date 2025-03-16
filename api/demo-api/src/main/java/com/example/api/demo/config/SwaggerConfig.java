package com.example.api.demo.config;

import java.util.Collections;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import com.example.api.demo.common.DisableSwaggerSecurity;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration("DemoRestApiSwaggerConfig")
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {

		final String securitySchemeName = "bearerAuth";
		Components components = new Components();

		// SecurityScheme
		SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP) // 인증 방식: HTTP
			.scheme("bearer") // HTTP 스키마: bearer (Bearer Token)
			.bearerFormat("JWT") // Bearer Token 포맷: JWT (Optional)
			.in(SecurityScheme.In.HEADER).name("Authorization");
		components.addSecuritySchemes(securitySchemeName, securityScheme);

		return new OpenAPI().components(components)
			// 전역 Security Requirement 설정 추가 (모든 API 에 Bearer 인증 적용)
			.security(Collections.singletonList(new SecurityRequirement().addList(securitySchemeName)))
			.info(new Info().title("Demo").description("Spring Boot Demo").version("0.0.1"));
	}

	@Bean
	public OperationCustomizer customize() {
		return (Operation operation, HandlerMethod handlerMethod) -> {
			DisableSwaggerSecurity disableSwaggerSecurity = handlerMethod.getMethodAnnotation(
				DisableSwaggerSecurity.class);

			// DisableSecurity 어노테이션있을시 스웨거 시큐리티 설정 제거
			if (disableSwaggerSecurity != null) {
				operation.setSecurity(Collections.emptyList());
			}

			return operation;
		};
	}

}
