package com.example.domain.demo.config;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomFlywayProperties {

	private boolean enabled = true;
	private String url;
	private String user;
	private String password;
	private String locations = "classpath:db/migration";
	private List<String> schemas;
	private boolean baselineOnMigrate = false;
	private boolean outOfOrder = false;
	private boolean validateOnMigrate = true;

}
