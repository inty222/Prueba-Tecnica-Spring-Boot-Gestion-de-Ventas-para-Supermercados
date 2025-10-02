package com.example.Prueba_Tecnica_Spring_Boot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoDto {
    private Long id;

    @NotBlank(message = "Introduzca un nombre completo o un nuevo producto que no esté registrado")
    @Size(min = 2, message = "Necesita completar el nombre del producto")
    private String nombreProducto;

    @NotNull(message = "Introduzca el precio del producto")
    @Positive(message = "Introduzca un precio válido")
    private Double precio;

    @NotBlank(message = "Seleccione una categoría")
    @Size(min = 2, message = "Complete el nombre de la categoría")
    private String categoria;

    @NotNull(message = "Introduzca el stock disponible")
    @PositiveOrZero(message = "No puede introducir un stock menor a 0")
    private Integer stock;
}

