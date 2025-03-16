package com.example.api.logging.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import lombok.Getter;
import lombok.Setter;

@Component(BeanName.PREFIX + "LoggingProperties")
@ConfigurationProperties(prefix = "logging.request")
@Getter
@Setter
public class LoggingProperties {

	private final AntPathMatcher pathMatcher = new AntPathMatcher();
	private Exclude exclude = new Exclude();

	public boolean isExcluded(String path) {
		if (exclude.path.isEmpty()) {
			return false;
		}
		return exclude.path.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}

	@Getter
	@Setter
	public static class Exclude {
		private List<String> path = new ArrayList<>();
	}
}
