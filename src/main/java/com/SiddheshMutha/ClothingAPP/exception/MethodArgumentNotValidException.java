package com.SiddheshMutha.ClothingAPP.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

public class MethodArgumentNotValidException extends RuntimeException {
    public MethodArgumentNotValidException(String message) {
        super(message);
    }
}
