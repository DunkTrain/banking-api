package com.dmitry.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для обновления существующего email-адреса.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmailRequestDto {

    @Schema(description = "ID email-а для обновления", example = "123")
    private Long emailId;

    @Schema(description = "Новый email-адрес", example = "new@example.com")
    @NotBlank(message = "Новый email не может быть пустым")
    @Email(message = "Email должен быть корректным")
    @Size(max = 200, message = "Email не должен превышать 200 символов")
    private String newEmail;
}
