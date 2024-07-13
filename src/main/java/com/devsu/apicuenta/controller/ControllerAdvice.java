package com.devsu.apicuenta.controller;

import com.devsu.apicuenta.exception.CuentaNotFoundExcdeption;
import com.devsu.apicuenta.exception.MovimientosException;
import com.devsu.apicuenta.exception.SinSaldoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CuentaNotFoundExcdeption.class)
    protected ResponseEntity<String> userException(CuentaNotFoundExcdeption ex){
        return new ResponseEntity<>("Error: La cuenta que quiere editar o actualizar no existe", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MovimientosException.class)
    protected ResponseEntity<String> userException(MovimientosException ex){
        return new ResponseEntity<>("La cuenta no existe o no tiene movimientos", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SinSaldoException.class)
    protected ResponseEntity<String> userException(SinSaldoException ex){
        return new ResponseEntity<>("No hay saldo disponible para realizar el movimiento", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
