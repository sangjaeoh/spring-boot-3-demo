package com.example.api.logging.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.api.logging.interceptor.LoggingInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration(BeanName.PREFIX + "WebMvcConfig")
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoggingInterceptor loggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 모든 경로에 loggingInterceptor 적용
		registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
	}

}
