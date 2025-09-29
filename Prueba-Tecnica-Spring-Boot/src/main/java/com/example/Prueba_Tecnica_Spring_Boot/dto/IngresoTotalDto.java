package com.example.Prueba_Tecnica_Spring_Boot.dto;

public record IngresoTotalDto(
        Long sucursalId,
        String sucursalNombre,
        Double ingresos
) {}
