package com.craft.actormanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler — centralized error handling for the entire application.
 *
 * Catches:
 *  1. MethodArgumentNotValidException — triggered by @Valid failures (field-level errors)
 *  2. ResponseStatusException         — thrown manually in the service layer (e.g. 404)
 *  3. Exception                       — catch-all for unexpected errors (500)
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles @Valid / @Validated bean validation failures.
     * Returns HTTP 400 with a map of { fieldName → errorMessage }.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }

    /**
     * Handles ResponseStatusException (e.g. 404 NOT_FOUND thrown in service layer).
     * Preserves the original HTTP status and reason phrase.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatus(ResponseStatusException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("status", String.valueOf(ex.getStatusCode().value()));
        body.put("error", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    /**
     * Catch-all handler for any unexpected runtime exceptions.
     * Returns HTTP 500 with a generic message.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleGenericException(Exception ex) {
        Map<String, String> body = new HashMap<>();
        body.put("status", "500");
        body.put("error", "An unexpected error occurred");
        body.put("message", ex.getMessage());
        return body;
    }
}

