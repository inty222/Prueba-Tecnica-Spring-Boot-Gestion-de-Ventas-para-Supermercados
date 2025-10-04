package com.example.Prueba_Tecnica_Spring_Boot.dto;
import lombok.Data;

// DTO  para representar una sucursal al transferir datos
// Se utiliza para enviar información de la sucursal entre capas de la aplicación
@Data
public class SucursalDTO {

    private Long id;
    private String nombre;
    private String direccion;
}


