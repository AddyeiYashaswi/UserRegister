package com.user.registrtn.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return new ResponseEntity<Object>(ErrorDetails.builder().timestamp(LocalDateTime.now()).message("Total Errors :"
        +ex.getErrorCount()+ ", First Error: "+ex.getFieldError().getDefaultMessage()).details(request.getDescription(false)).build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { InvalidAgeOrCountryException.class })
    public ResponseEntity<ErrorDetails>handleInvalidCountryOrAge(Exception ex, WebRequest request) {

        return new ResponseEntity<ErrorDetails>(ErrorDetails.builder().timestamp(LocalDateTime.now()).message(ex.getMessage()).details(request.getDescription(false)).build(),
                HttpStatus.BAD_REQUEST);
    }
    }

