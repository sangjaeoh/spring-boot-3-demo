// package com.example.domain.demo.validation;
//
// import java.lang.annotation.Annotation;
// import java.lang.reflect.Method;
// import java.lang.reflect.Parameter;
// import java.util.HashSet;
//
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.aspectj.lang.reflect.MethodSignature;
// import org.springframework.core.annotation.AnnotationUtils;
// import org.springframework.validation.BeanPropertyBindingResult;
// import org.springframework.validation.Errors;
// import org.springframework.validation.FieldError;
//
// import jakarta.validation.ConstraintViolationException;
// import jakarta.validation.Valid;
//
// /**
//  * 도메인 모듈에서 메서드레벨 파라미터 DTO 의 유효성 검증을 하기 위해 Aspect 정의
//  * 해당 도메인 모듈의 패키지에서만 동작하도록 정의한다.
//  */
// @Aspect
// public class JsonNullableValidationAspect {
//
// 	private final JsonNullableAwareValidator validator;
//
// 	public JsonNullableValidationAspect(JsonNullableAwareValidator validator) {
// 		this.validator = validator;
// 	}
//
// 	/**
// 	 * DemoDomain 패키지 내의 @Validated 어노테이션이 붙은 클래스의 메서드 대상
// 	 */
// 	@Pointcut("within(com.example.domain.demo..*) && " + "@within(org.springframework.validation.annotation.Validated)")
// 	public void demoDomainValidatedClass() {
// 	}
//
// 	/**
// 	 * @Valid 어노테이션이 붙은 파라미터가 있는 메서드 대상
// 	 */
// 	@Pointcut("execution(* *(..)) && @args(jakarta.validation.Valid)")
// 	public void methodWithValidParameters() {
// 	}
//
// 	/**
// 	 * 위 두 포인트컷의 교집합 대상
// 	 */
// 	@Around("demoDomainValidatedClass() && methodWithValidParameters()")
// 	public Object validateJsonNullableFields(ProceedingJoinPoint joinPoint) throws Throwable {
// 		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
// 		Method method = signature.getMethod();
// 		Object[] args = joinPoint.getArgs();
// 		Parameter[] parameters = method.getParameters();
//
// 		// 에러 메시지 수집
// 		StringBuilder errorMessages = new StringBuilder();
// 		boolean hasErrors = false;
//
// 		// @Valid 어노테이션이 있는 파라미터 검증
// 		for (int i = 0; i < parameters.length; i++) {
// 			if (hasValidAnnotation(parameters[i])) {
// 				Object arg = args[i];
// 				if (arg != null) {
// 					// BeanPropertyBindingResult 생성
// 					Errors errors = new BeanPropertyBindingResult(arg, arg.getClass().getSimpleName());
//
// 					// 검증
// 					validator.validate(arg, errors);
//
// 					// 오류가 있으면 오류메시지 생성
// 					if (errors.hasErrors()) {
// 						hasErrors = true;
//
// 						for (FieldError error : errors.getFieldErrors()) {
// 							if (errorMessages.length() > 0) {
// 								errorMessages.append("; ");
// 							}
//
// 							// "필드: 메시지" 형식으로 출력
// 							errorMessages.append(error.getField()).append(": ").append(error.getDefaultMessage());
// 						}
// 					}
// 				}
// 			}
// 		}
//
// 		if (hasErrors) {
// 			throw new ConstraintViolationException(errorMessages.toString(), new HashSet<>());
// 		}
//
// 		// 원래 메서드 실행
// 		return joinPoint.proceed();
// 	}
//
// 	/**
// 	 * 파라미터에 @Valid 어노테이션이 있는지 확인
// 	 */
// 	private boolean hasValidAnnotation(Parameter parameter) {
// 		for (Annotation annotation : parameter.getAnnotations()) {
// 			if (annotation instanceof Valid
// 				|| AnnotationUtils.findAnnotation(annotation.annotationType(), Valid.class) != null) {
// 				return true;
// 			}
// 		}
// 		return false;
// 	}
// }