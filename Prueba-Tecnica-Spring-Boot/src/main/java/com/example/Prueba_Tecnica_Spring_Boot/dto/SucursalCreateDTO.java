package com.example.Prueba_Tecnica_Spring_Boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para la creación de una sucursal
// Se utiliza para transferir datos al crear una nueva sucursal
@NoArgsConstructor // Constructor sin argumentos generado automáticamente
@AllArgsConstructor // Constructor con todos los campos generado automáticamente
@Data // Genera getters, setters, toString, equals y hashCode automáticamente
public class SucursalCreateDTO {

    private String nombre;
    private String direccion;
}
