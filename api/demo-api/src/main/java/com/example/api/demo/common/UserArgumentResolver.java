package com.example.api.demo.common;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.api.demo.auth.provider.JwtTokenProvider;
import com.example.domain.demo.config.BeanName;
import com.example.domain.demo.user.domain.User;
import com.example.domain.demo.user.service.UserQueryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component(BeanName.PREFIX + "UserArgumentResolver")
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserQueryService userQueryService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasLoginUserAnnotation = parameter.hasParameterAnnotation(RequestUser.class);
		boolean isUserType = User.class.isAssignableFrom(parameter.getParameterType());
		return hasLoginUserAnnotation && isUserType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		String jwt = jwtTokenProvider.resolveToken(request);

		String username = jwtTokenProvider.getUsernameFromToken(jwt);
		return userQueryService.getOrThrowByEmail(username);
	}
}