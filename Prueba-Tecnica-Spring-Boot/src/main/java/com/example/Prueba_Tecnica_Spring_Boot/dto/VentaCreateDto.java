package com.example.Prueba_Tecnica_Spring_Boot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record VentaCreateDto(
        LocalDate fecha,
        @NotNull @Size(min = 1) List<@Valid VentaItemCreateDto> detalle
) {}
