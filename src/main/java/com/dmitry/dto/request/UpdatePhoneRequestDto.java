package com.dmitry.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePhoneRequestDto {

    @NotBlank(message = "Новый телефон не должен быть пустым")
    @Pattern(regexp = "\\d{11}", message = "Формат телефона должен быть 11 цифр, например 79207865432")
    private String newPhone;
}
