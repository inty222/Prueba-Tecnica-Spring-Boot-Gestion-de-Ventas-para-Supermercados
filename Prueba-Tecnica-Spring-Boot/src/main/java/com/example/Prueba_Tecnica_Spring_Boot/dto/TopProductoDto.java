package com.example.Prueba_Tecnica_Spring_Boot.dto;

public record TopProductoDto(
        Long productoId,
        String productoNombre,
        Long unidadesVendidas,
        Double ingresosGenerados
) {}
