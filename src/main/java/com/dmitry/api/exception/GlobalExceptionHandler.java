package com.dmitry.api.exception;

import com.dmitry.api.error.ErrorResponse;
import com.dmitry.exception.DuplicateEmailException;
import com.dmitry.exception.DuplicatePhoneException;
import com.dmitry.exception.ForbiddenOperationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
 *     <li>{@link MissingServletRequestParameterException} - отсутствие обязательных параметров</li>
 *     <li>{@link DuplicateEmailException} - обработчик email</li>
 *     <li>{@link DuplicatePhoneException} - обработчик phone</li>
 *     <li>{@link ForbiddenOperationException} - запрещенная операция</li>
 * </ul>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.warn("Ошибка валидации тела запроса: {}", errors);

        return ResponseEntity.badRequest().body(new ErrorResponse("Ошибка валидации данных ", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("Ошибка валидации параметров запроса: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.warn("Сущность не найдена: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Ошибка бизнес-логики: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.warn("Отсутствует обязательный параметр запроса: {}", ex.getParameterName());
        return ResponseEntity.badRequest().body(new ErrorResponse("Отсутствует обязательный параметр запроса: " + ex.getParameterName()));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException ex) {
        log.warn("Email уже существует: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(DuplicatePhoneException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatePhoneException(DuplicatePhoneException ex) {
        log.warn("Телефон уже существует: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenOperationException(ForbiddenOperationException ex) {
        log.warn("Запрещенная операция: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
