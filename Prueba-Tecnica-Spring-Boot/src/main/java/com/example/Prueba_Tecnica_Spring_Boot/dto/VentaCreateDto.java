package com.example.Prueba_Tecnica_Spring_Boot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record VentaCreateDto(
        LocalDate fecha,
        // Sucursal obligatoria para evitar ventas sin sucursal en BD
        @NotNull Long sucursalId,
        // Debe venir al menos un ítem y cada ítem se valida con @Valid
        @NotNull @Size(min = 1) List<@Valid VentaItemCreateDto> detalle
) {}