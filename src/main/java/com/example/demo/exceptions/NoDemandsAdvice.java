package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NoDemandsAdvice {
    @ResponseBody
    @ExceptionHandler(NoDemandException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String employeeNotFoundHandler(NoDemandException ex) {
        return ex.getMessage();
    }
}
