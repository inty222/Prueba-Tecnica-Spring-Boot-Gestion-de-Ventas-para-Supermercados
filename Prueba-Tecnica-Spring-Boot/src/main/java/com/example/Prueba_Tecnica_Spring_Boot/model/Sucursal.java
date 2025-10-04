package com.example.Prueba_Tecnica_Spring_Boot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String direccion;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL)
    // Relación uno a muchos con la entidad Venta
    // "mappedBy" indica que el lado inverso de la relación es la propiedad "sucursal" en Venta
    // cascade = CascadeType.ALL asegura que las operaciones en Sucursal se propaguen a las ventas
    private List<Venta> ventas = new ArrayList<>(); // Lista de ventas asociadas a la sucursal
}
