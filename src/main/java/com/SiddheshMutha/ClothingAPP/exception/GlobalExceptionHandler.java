package com.SiddheshMutha.ClothingAPP.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgumentValidation(MethodArgumentNotValidException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
    }


    @ExceptionHandler(UserDoesNotExistsException.class)
    public ResponseEntity<?> handleUserNotFound(UserDoesNotExistsException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @ExceptionHandler(ModelNotExistsException.class)
    public ResponseEntity<?> handleModelNotFound(ModelNotExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }





}
