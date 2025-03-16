package com.example.api.logging.interceptor;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.example.api.logging.config.LoggingProperties;
import com.example.api.logging.filter.RepeatableHttpServletRequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("loggingInterceptor")
public class LoggingInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	private final LoggingProperties loggingProperties;

	public LoggingInterceptor(LoggingProperties loggingProperties) {
		this.loggingProperties = loggingProperties;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		if (loggingProperties.isExcluded(request.getRequestURI())) {
			return true;
		}

		String traceId = UUID.randomUUID().toString();
		request.setAttribute("X-Request-Trace-Id", traceId);
		response.addHeader("X-Request-Trace-Id", traceId);

		request.setAttribute("X-Request-StartTime", System.currentTimeMillis());

		logRequest(request, handler, traceId);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) {
		if (loggingProperties.isExcluded(request.getRequestURI())) {
			return;
		}

		String traceId = (String)request.getAttribute("X-Request-Trace-Id");
		long startTime = (Long)request.getAttribute("X-Request-StartTime");
		long duration = System.currentTimeMillis() - startTime;

		logResponse(request, response, traceId, duration);
	}

	private void logRequest(HttpServletRequest request, Object handler, String traceId) throws IOException {
		Map<String, Object> logData = new HashMap<>();
		logData.put("method", request.getMethod());
		logData.put("uri", request.getRequestURI());
		logData.put("traceId", traceId);

		addPathVariables(request, handler, logData);
		addParameters(request, logData);
		addRequestBody(request, logData);

		logger.info("Request: {}", logData);
	}

	private void addPathVariables(HttpServletRequest request, Object handler, Map<String, Object> logData) {
		if (handler instanceof HandlerMethod) {
			ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			if (attributes != null) {
				HttpServletRequest currentRequest = attributes.getRequest();
				Object pathVariablesObj = currentRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				if (pathVariablesObj instanceof Map<?, ?> pathVariables && !pathVariables.isEmpty()) {
					logData.put("pathVariables", pathVariablesObj);
				}
			}
		}
	}

	private void addParameters(HttpServletRequest request, Map<String, Object> logData) {
		if (request.getParameterNames().hasMoreElements()) {
			logData.put("parameters", getRequestParameters(request));
		}
	}

	private void addRequestBody(HttpServletRequest request, Map<String, Object> logData) throws IOException {
		if (request instanceof RepeatableHttpServletRequestWrapper requestWrapper) {
			String requestBody = requestWrapper.getContentAsString();
			if (!requestBody.isEmpty()) {
				logData.put("body", requestBody);
			}
		}
	}

	private void logResponse(HttpServletRequest request, HttpServletResponse response, String traceId, long duration) {
		Map<String, Object> logData = new HashMap<>();
		logData.put("status", response.getStatus());
		logData.put("uri", request.getRequestURI());
		logData.put("traceId", traceId);
		logData.put("duration", duration);

		addResponseBody(response, logData);

		logger.info("Response: {}", logData);
	}

	private void addResponseBody(HttpServletResponse response, Map<String, Object> logData) {
		String contentType = response.getContentType();
		if (response instanceof ContentCachingResponseWrapper responseWrapper && contentType != null
			&& contentType.contains("application/json")) {
			byte[] responseContentAsByteArray = responseWrapper.getContentAsByteArray();
			if (responseContentAsByteArray != null && responseContentAsByteArray.length > 0) {
				try {
					logData.put("body", mapper.readValue(responseContentAsByteArray, Object.class));
				} catch (IOException e) {
					logData.put("body", new String(responseContentAsByteArray));
				}
			} else {
				logData.put("body", "Empty");
			}
		}
	}

	private Map<String, String> getRequestParameters(HttpServletRequest request) {
		Map<String, String> parameterMap = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			parameterMap.put(parameterName, request.getParameter(parameterName));
		}
		return parameterMap;
	}
}
