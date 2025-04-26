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
 * DTO для добавления нового email-адреса.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddEmailRequestDto {

    @Schema(description = "Email-адрес для добавления", example = "user@example.com")
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть корректным")
    @Size(max = 200, message = "Email не должен превышать 200 символов")
    private String email;
}
