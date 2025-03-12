package com.example.domain.demo.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.jpa.vendor.Database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomJpaProperties {

	private Map<String, String> properties = new HashMap<>();
	private final List<String> mappingResources = new ArrayList<>();
	private String databasePlatform;
	private Database database;
	private boolean generateDdl = false;
	private boolean showSql = false;
	private Boolean openInView;
}
