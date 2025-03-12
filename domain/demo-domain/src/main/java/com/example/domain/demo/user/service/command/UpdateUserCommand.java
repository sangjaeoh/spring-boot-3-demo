package com.example.domain.demo.user.service.command;

import org.openapitools.jackson.nullable.JsonNullable;

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
public class UpdateUserCommand {

	@Builder.Default
	@NotBlank(message = "암호는 필수입니다.")
	@Size(min = 8, max = 30, message = "암호는 8자 이상 30자 이하여야 합니다.")
	private JsonNullable<String> password = JsonNullable.undefined();

	@Builder.Default
	private JsonNullable<String> name = JsonNullable.undefined();

	@Builder.Default
	private JsonNullable<Integer> age = JsonNullable.undefined();

	public boolean hasPassword() {
		return password != null && password.isPresent();
	}

	public boolean hasName() {
		return name != null && name.isPresent();
	}

	public boolean hasAge() {
		return age != null && age.isPresent();
	}

}
