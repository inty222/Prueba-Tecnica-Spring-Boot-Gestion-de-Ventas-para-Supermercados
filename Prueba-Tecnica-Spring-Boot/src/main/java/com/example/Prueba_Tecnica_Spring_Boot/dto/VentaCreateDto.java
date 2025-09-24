package com.example.Prueba_Tecnica_Spring_Boot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaCreateDto {
    private LocalDate fecha;

    @NotNull
    @Size(min = 1)
    private List<@Valid VentaItemCreateDto> detalle;
}
