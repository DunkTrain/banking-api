package com.dmitry.api.error;

import com.dmitry.api.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * Класс для предоставления ошибок в ответе API
 * <p>
 * Используется в глобальном обработчике ошибок {@link GlobalExceptionHandler}
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final String message;
    private final Map<String, String> fieldErrors;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(String message) {
        this(message, Collections.emptyMap());
    }
}
