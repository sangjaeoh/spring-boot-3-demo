package com.example.api.demo.user.contoller.request;

import com.example.domain.demo.user.service.command.ReplaceUserCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
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
public class ReplaceUserRequest {

	@NotBlank
	@Size(min = 8, message = "비밀번호는 8글자 이상이여야 합니다.")
	private String password;

	private String name;

	@PositiveOrZero
	private Integer age;

	public ReplaceUserCommand toCommand() {
		return ReplaceUserCommand.builder().password(this.password).name(this.name).age(this.age).build();
	}
}
