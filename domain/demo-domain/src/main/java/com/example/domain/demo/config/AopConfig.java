// package com.example.domain.demo.config;
//
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.EnableAspectJAutoProxy;
// import org.springframework.validation.SmartValidator;
// import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
// import com.example.domain.demo.validation.JsonNullableAwareValidator;
// import com.example.domain.demo.validation.JsonNullableValidationAspect;
//
// @Configuration(BeanName.PREFIX + "AopConfig")
// @EnableAspectJAutoProxy
// public class AopConfig {
//
// 	private static final String LOCAL_VALIDATOR_FACTORY_BEAN_NAME = BeanName.PREFIX + "LocalValidatorFactoryBean";
// 	private static final String JSON_NULLABLE_AWARE_VALIDATOR_BEAN_NAME =
// 		BeanName.PREFIX + "JsonNullableAwareValidator";
// 	private static final String JSON_NULLABLE_VALIDATION_ASPECT_BEAN_NAME =
// 		BeanName.PREFIX + "JsonNullableValidationAspect";
//
// 	@Bean(LOCAL_VALIDATOR_FACTORY_BEAN_NAME)
// 	public LocalValidatorFactoryBean localValidatorFactoryBean() {
// 		return new LocalValidatorFactoryBean();
// 	}
//
// 	@Bean(JSON_NULLABLE_AWARE_VALIDATOR_BEAN_NAME)
// 	public JsonNullableAwareValidator jsonNullableAwareValidator(
// 		@Qualifier(LOCAL_VALIDATOR_FACTORY_BEAN_NAME) SmartValidator defaultValidator) {
// 		return new JsonNullableAwareValidator(defaultValidator);
// 	}
//
// 	@Bean(JSON_NULLABLE_VALIDATION_ASPECT_BEAN_NAME)
// 	public JsonNullableValidationAspect jsonNullableValidationAspect(
// 		@Qualifier(JSON_NULLABLE_AWARE_VALIDATOR_BEAN_NAME) JsonNullableAwareValidator validator) {
// 		return new JsonNullableValidationAspect(validator);
// 	}
// }
