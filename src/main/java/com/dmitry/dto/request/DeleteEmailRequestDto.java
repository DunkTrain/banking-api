package com.dmitry.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для удаления Email
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEmailRequestDto {

    @Schema(description = "ID email-а для удаления", example = "123")
    @NotNull(message = "ID email-а обязателен для удаления")
    private Long emailId;
}
