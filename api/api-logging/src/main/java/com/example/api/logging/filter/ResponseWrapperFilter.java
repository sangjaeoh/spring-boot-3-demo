package com.example.api.logging.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.example.api.logging.config.BeanName;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter
@Component(BeanName.PREFIX + "ResponseWrapperFilter")
@Order(1)
public class ResponseWrapperFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
		filterChain.doFilter(request, contentCachingResponseWrapper);

		// 응답 데이터가 비어있지 않은 경우에만 클라이언트로 출력
		byte[] responseData = contentCachingResponseWrapper.getContentAsByteArray();
		if (responseData.length > 0) {
			contentCachingResponseWrapper.copyBodyToResponse();
		}

	}
}
