package com.example.Prueba_Tecnica_Spring_Boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoDto {
    private Long id;
    private String producto;
    private Double precio;
    private String categoria;
    private int stock;
}

