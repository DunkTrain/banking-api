package com.dmitry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {

    private Long id;

    @Schema(description = "Имя пользователя. Обязательно. Максимум 500 символов.")
    @NotBlank(message = "Имя не может быть пусты")
    @Size(max = 500, message = "Имя не должно превышать 500 символов")
    private String name;

    @Schema(description = "Дата рождения пользователя. Обязательная. Формат: yyyy-MM-dd.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Дата рождения должна быть в прошлом")
    @NotNull(message = "Дата рождения обязательна")
    private LocalDate dateOfBirth;

    @Schema(description = "Список email-адресов пользователя. Должен содержать хотя бы один элемент.")
    @NotNull(message = "Email-адреса обязательны")
    private List<EmailDTO> emails;

    @Schema(description = "Список телефонов пользователя. Должен содержать хотя бы один элемент.")
    @NotNull(message = "Телефоны обязательны")
    private List<PhoneDTO> phones;
}
