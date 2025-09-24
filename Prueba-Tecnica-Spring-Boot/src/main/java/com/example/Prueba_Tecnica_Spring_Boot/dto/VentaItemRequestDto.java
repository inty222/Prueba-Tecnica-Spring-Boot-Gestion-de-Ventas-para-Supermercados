package com.example.Prueba_Tecnica_Spring_Boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VentaItemRequestDto {
    private Long productoId;
    private Integer cantidad;
}
