package com.example.Prueba_Tecnica_Spring_Boot.exception;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(String message){
        super(message);
    }
}
