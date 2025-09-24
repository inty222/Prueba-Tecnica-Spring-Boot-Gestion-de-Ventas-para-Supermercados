package com.example.Prueba_Tecnica_Spring_Boot.exception;

public class SucursalNotFoundException extends RuntimeException {
    public SucursalNotFoundException(Long id) {
        super("Sucursal con id " + id + " no encontrada");
    }
}
