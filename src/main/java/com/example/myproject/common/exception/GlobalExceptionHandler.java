package com.example.myproject.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // valid 에러를 잡아줌
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        errors.put("error", errorMessage);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // PreAuthorize 에러를 잡아줌
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> accessDeniedExceptionHandler(AccessDeniedException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("error", "권한이 없습니다.");
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }


}
