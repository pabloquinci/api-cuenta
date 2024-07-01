package com.devsu.apicuenta.controller;

import com.devsu.apicuenta.exception.CuentaNotFoundExcdeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CuentaNotFoundExcdeption.class)
    protected ResponseEntity<String> userException(CuentaNotFoundExcdeption ex){
        return new ResponseEntity<String>("Error: La cuenta que quiere editar o actualizar no existe", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
