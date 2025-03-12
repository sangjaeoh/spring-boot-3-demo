// package com.example.domain.demo.validation;
//
// import java.lang.reflect.Field;
//
// import org.openapitools.jackson.nullable.JsonNullable;
// import org.springframework.validation.Errors;
// import org.springframework.validation.SmartValidator;
//
// /**
//  * JsonNullable 객체를 유효성검사를 하기 위한 클래스
//  * JsonNullable 객체이고, 값이 존재하면 값을 얻어서 기본 Validator 로 유효성 검사를 한다.
//  */
// public class JsonNullableAwareValidator implements SmartValidator {
//
// 	private final SmartValidator defaultValidator;
//
// 	public JsonNullableAwareValidator(SmartValidator defaultValidator) {
// 		this.defaultValidator = defaultValidator;
// 	}
//
// 	@Override
// 	public boolean supports(Class<?> clazz) {
// 		return defaultValidator.supports(clazz);
// 	}
//
// 	@Override
// 	public void validate(Object target, Errors errors) {
// 		Object unwrappedTarget = unwrapJsonNullableFields(target);
// 		defaultValidator.validate(unwrappedTarget, errors);
// 	}
//
// 	@Override
// 	public void validate(Object target, Errors errors, Object... validationHints) {
// 		Object unwrappedTarget = unwrapJsonNullableFields(target);
// 		defaultValidator.validate(unwrappedTarget, errors, validationHints);
// 	}
//
// 	@Override
// 	public void validateValue(Class<?> targetType, String fieldName, Object value, Errors errors,
// 		Object... validationHints) {
// 		if (value instanceof JsonNullable) {
// 			JsonNullable<?> jsonNullable = (JsonNullable<?>)value;
// 			value = jsonNullable.isPresent() ? jsonNullable.get() : null;
// 		}
//
// 		defaultValidator.validateValue(targetType, fieldName, value, errors, validationHints);
// 	}
//
// 	// JsonNullable 필드 값을 추출하는 메서드
// 	private Object unwrapJsonNullableFields(Object target) {
// 		if (target == null) {
// 			return null;
// 		}
//
// 		try {
// 			// 원본 객체와 동일한 클래스의 새 인스턴스 생성
// 			Object unwrappedObject = target.getClass().getDeclaredConstructor().newInstance();
//
// 			// 모든 필드 복사하면서 JsonNullable 타입은 값을 추출
// 			for (Field field : target.getClass().getDeclaredFields()) {
// 				field.setAccessible(true);
// 				Object value = field.get(target);
//
// 				if (value instanceof JsonNullable) {
// 					JsonNullable<?> jsonNullable = (JsonNullable<?>)value;
// 					field.set(unwrappedObject, jsonNullable.isPresent() ? jsonNullable.get() : null);
// 				} else {
// 					field.set(unwrappedObject, value);
// 				}
// 			}
//
// 			return unwrappedObject;
// 		} catch (Exception e) {
// 			// 변환 실패 시 원본 반환
// 			return target;
// 		}
// 	}
// }