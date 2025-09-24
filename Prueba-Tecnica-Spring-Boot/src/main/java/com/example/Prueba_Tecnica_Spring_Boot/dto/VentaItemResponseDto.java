package com.example.Prueba_Tecnica_Spring_Boot.dto;

import java.math.BigDecimal;

// Línea del detalle en la respuesta
public record VentaItemResponseDto(
        Long productoId,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {}
