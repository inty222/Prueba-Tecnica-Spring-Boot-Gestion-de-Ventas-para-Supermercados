package com.example.Prueba_Tecnica_Spring_Boot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

// Payload para POST /api/ventas
public record VentaCreateDto(
        LocalDate fecha, // opcional: si es null, el Service pondrá LocalDate.now()
        @NotNull @Size(min = 1) List<@Valid VentaItemCreateDto> detalle
) {}
