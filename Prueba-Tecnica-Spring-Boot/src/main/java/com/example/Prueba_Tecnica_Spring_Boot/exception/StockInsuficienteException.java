package com.example.Prueba_Tecnica_Spring_Boot.exception;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String message) {
        super(message);
    }
}
