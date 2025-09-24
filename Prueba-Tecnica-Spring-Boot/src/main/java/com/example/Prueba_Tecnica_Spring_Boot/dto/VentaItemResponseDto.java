package com.example.Prueba_Tecnica_Spring_Boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaItemResponseDto {
    private Long ventaId;
    private String nombreProducto;
    private int cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
