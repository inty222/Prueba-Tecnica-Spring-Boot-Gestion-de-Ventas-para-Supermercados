package com.example.Prueba_Tecnica_Spring_Boot.exception;

import com.example.Prueba_Tecnica_Spring_Boot.model.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ErrorRespuesta> manejarLibroNoEncontrado(ProductoNoEncontradoException ex) {
        ErrorRespuesta error = new ErrorRespuesta(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRespuesta> manejarErroresGenerales(Exception ex) {
        ErrorRespuesta error = new ErrorRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error inesperado: " + ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


