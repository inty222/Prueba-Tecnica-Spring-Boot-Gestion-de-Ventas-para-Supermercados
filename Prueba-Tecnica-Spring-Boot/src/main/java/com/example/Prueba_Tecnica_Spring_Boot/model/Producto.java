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
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique= true, nullable = false)
    private String nombreProducto;
    private String categoria;
    private double precio;
    private int stock;
    @OneToMany(mappedBy = "producto")
    private List<VentaItems> ventaItems = new ArrayList<>();
}
