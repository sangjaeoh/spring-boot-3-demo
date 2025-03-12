package com.example.domain.demo.user.service.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserCommand {

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "이메일 형식이 아닙니다.")
	private String email;

	@NotBlank(message = "암호는 필수입니다.")
	@Size(min = 8, max = 30, message = "암호는 8자 이상 30자 이하여야 합니다.")
	private String password;

	private String name;
	private Integer age;

}
