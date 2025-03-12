package com.example.api.demo.rest.user.contoller.request;

import org.openapitools.jackson.nullable.JsonNullable;

import com.example.domain.demo.user.service.command.UpdateUserCommand;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class UpdateUserRequest {

	@Schema(type = "string", description = "비밀번호", nullable = true)
	@Size(min = 8, message = "비밀번호는 8글자 이상이여야 합니다.")
	@Builder.Default
	private JsonNullable<String> password = JsonNullable.undefined();

	@Schema(type = "string", description = "이름", nullable = true)
	@Builder.Default
	private JsonNullable<String> name = JsonNullable.undefined();

	@Schema(type = "integer", description = "나이", nullable = true)
	@PositiveOrZero
	@Builder.Default
	private JsonNullable<Integer> age = JsonNullable.undefined();

	public UpdateUserCommand toCommand() {
		return UpdateUserCommand.builder().password(this.password).name(this.name).age(this.age).build();
	}
}
