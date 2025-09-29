package com.example.Prueba_Tecnica_Spring_Boot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record VentaItemCreateDto(
        @NotNull Long productoId,
        @NotNull @Min(1) Integer cantidad
) {}
