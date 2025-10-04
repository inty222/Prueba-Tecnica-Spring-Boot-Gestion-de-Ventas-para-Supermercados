package com.example.Prueba_Tecnica_Spring_Boot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// DTO para la creación de una sucursal
// Se utiliza para transferir datos al crear una nueva sucursal
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SucursalCreateDTO {

    //asegura que el campo no sea null, ni vacío, ni solo espacios.
    @NotBlank(message = "El nombre de la sucursal no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "La dirección de la sucursal no puede estar vacía")
    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String direccion;
}

