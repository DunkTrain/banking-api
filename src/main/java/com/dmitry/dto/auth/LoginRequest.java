package com.dmitry.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    // TODO: реализовать (пока черновик)

    @NotBlank
    private String login; // email или phone

    @NotBlank
    @Size(min = 8, max = 500)
    private String password;
}
