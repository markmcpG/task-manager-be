package com.example.taskmanager.exceptions.categories;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CategoryManagerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CategoryManagerException> handleAllException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        CategoryManagerException exception = new CategoryManagerException(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CategoryManagerEntityNotFoundException.class)
    public ResponseEntity<CategoryManagerException> handleNotFoundException(Exception ex, WebRequest request) {
        CategoryManagerException exception = new CategoryManagerException(new Date(), ex.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ex.printStackTrace();
        CategoryManagerException exception = new CategoryManagerException(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            validationErrors.put(fieldName, message);
        });

        CategoryManagerFieldValidationException sisExceptionResponse = new CategoryManagerFieldValidationException(new Date(),
                "ArgumentValidationFailed", request.getDescription(false), HttpStatus.BAD_REQUEST.value(), validationErrors);
        return new ResponseEntity<>(sisExceptionResponse, HttpStatus.BAD_REQUEST);
    }


}