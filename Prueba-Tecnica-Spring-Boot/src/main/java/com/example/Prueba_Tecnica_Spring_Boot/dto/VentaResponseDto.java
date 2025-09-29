package com.example.Prueba_Tecnica_Spring_Boot.dto;

import java.time.LocalDate;
import java.util.List;

public record VentaResponseDto(
        Long id,
        LocalDate fecha,
        Boolean anulada,
        List<VentaItemResponseDto> detalle
) {}
