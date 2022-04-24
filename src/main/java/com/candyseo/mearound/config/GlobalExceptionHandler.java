package com.candyseo.mearound.config;

import com.candyseo.mearound.exception.DataNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String returnBadRequestExceptionHandler(Exception e) {

        return e.getMessage();
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public String returnNoContentExceptionHandler(Exception e) {

        return e.getMessage();
    }
}
