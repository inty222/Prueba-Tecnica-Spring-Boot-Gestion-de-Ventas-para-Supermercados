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
    private List<Venta> ventas = new ArrayList<>();
}
