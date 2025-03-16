package com.example.api.logging.filter;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.api.logging.config.BeanName;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter
@Component(BeanName.PREFIX + "RequestWrapperFilter")
@Order(1)
public class RequestWrapperFilter extends OncePerRequestFilter {

	/**
	 * RequestBody 는 스트림으로 기본적으로 한번만 읽을 수 있다.
	 * RepeatableHttpServletRequestWrapper 로 감싸 RequestBody 를 여러번 읽을 수 있도록 변경.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		if (request.getRequestURI().startsWith("/h2-console")) {
			filterChain.doFilter(request, response);
			return;
		}

		RepeatableHttpServletRequestWrapper wrapper = new RepeatableHttpServletRequestWrapper(request);
		filterChain.doFilter(wrapper, response);
	}
}
