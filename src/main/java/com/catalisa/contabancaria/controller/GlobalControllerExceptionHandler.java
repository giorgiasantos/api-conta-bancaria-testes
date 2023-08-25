package com.catalisa.contabancaria.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException exception){
        String msgException = "Conflito de integridade de dados: " + exception.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(msgException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler (MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException exception){
        String msgException = "Parâmetro inválido: " + exception.getName() + ". Esperado: " + exception.getRequiredType().getSimpleName();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable (HttpMessageNotReadableException exception){
        String msgException = "O corpo da requisição não está legível. " + exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgException);
    }

}
