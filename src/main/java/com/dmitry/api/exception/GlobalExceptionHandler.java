package com.dmitry.api.exception;

import com.dmitry.api.error.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений для всех REST-контроллеров приложения.
 *
 * <p>Обрабатывает стандартные ошибки валидации, отсутствия сущностей и бизнес-ошибки.</p>
 *
 * <p>Возвращает унифицированный формат ошибки в виде {@link ErrorResponse},
 * содержащий сообщение, дату и дополнительные ошибки полей (если применимо).</p>
 *
 * <p>Перехватываемые ошибки:</p>
 * <ul>
 *     <li>{@link MethodArgumentNotValidException} — ошибка валидации тела запроса (@Valid)</li>
 *     <li>{@link ConstraintViolationException} — ошибка валидации параметров запроса (@RequestParam, @PathVariable)</li>
 *     <li>{@link EntityNotFoundException} — сущность не найдена в БД</li>
 *     <li>{@link IllegalArgumentException} — ошибка бизнес-логики (например, запрет перевода себе)</li>
 * </ul>
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(new ErrorResponse("Ошибка валидации данных ", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}
