package com.example.api.demo.rest.config;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration("DemoRestApiJacksonConfig")
public class JacksonConfig {

	// @Bean
	// @ConditionalOnMissingBean(Jackson2ObjectMapperBuilderCustomizer.class)
	// public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
	// 	return builder -> {
	// 		builder.serializationInclusion(JsonInclude.Include.ALWAYS);
	// 		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	// 		builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	// 		builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	// 		builder.modules(new JsonNullableModule());
	// 		builder.modules(new JavaTimeModule());
	// 	};
	// }

	@Bean
	@ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.registerModule(new JsonNullableModule());
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

}
