package com.example.domain.demo.user.service.command;

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
public class ReplaceUserCommand {

	@NotBlank(message = "암호는 필수입니다.")
	@Size(min = 8, max = 30, message = "암호는 8자 이상 30자 이하여야 합니다.")
	private String password;
	
	private String name;
	private int age;

}
