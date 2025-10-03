package com.example.Prueba_Tecnica_Spring_Boot.exception;

// Excepción personalizada para indicar que una sucursal no fue encontrada en la base de datos
public class SucursalNotFoundException extends RuntimeException {

    // Constructor que recibe el ID de la sucursal que no se encontró
    public SucursalNotFoundException(Long id) {
        // Llama al constructor de RuntimeException con un mensaje descriptivo
        super("Sucursal con id " + id + " no encontrada");
    }
}
