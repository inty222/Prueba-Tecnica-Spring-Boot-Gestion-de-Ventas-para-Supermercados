package com.example.Prueba_Tecnica_Spring_Boot.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SucursalNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSucursalNotFound(SucursalNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", "Sucursal no encontrada");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRespuesta> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Tomamos el primer error de validación
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage) // <-- se usa el message de la anotación
                .findFirst()
                .orElse("Error de validación");

        ErrorRespuesta error = new ErrorRespuesta(
                HttpStatus.BAD_REQUEST.value(),
                mensaje,
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
