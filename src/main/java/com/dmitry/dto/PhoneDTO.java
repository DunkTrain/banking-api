package com.dmitry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {

    @Schema(description = "Номер телефона в формате 79XXXXXXXXX. От 11 до 13 символов.", example = "79201112233")
    @NotBlank(message = "Телефон не может быть пустым")
    @Size(min = 11, max = 13, message = "Телефон должен содержать от 11 до 13 символов")
    @Pattern(regexp = "79\\d{9}", message = "Телефон должен начинаться с 79 и содержать 11 цифр")
    private String phone;
}
