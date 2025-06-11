package com.user.registrtn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAgeOrCountryException extends RuntimeException{

    public InvalidAgeOrCountryException(String message)
    {
        super(message);
    }
}
