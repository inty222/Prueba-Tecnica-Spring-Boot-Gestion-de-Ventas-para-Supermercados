package com.example.Prueba_Tecnica_Spring_Boot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponseDto {
    private Long id;
    private LocalDate fecha;
    private Boolean anulada;
    private List<VentaItemResponseDto> detalle;
}
