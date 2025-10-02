package com.example.Prueba_Tecnica_Spring_Boot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity // Marca la clase como una entidad JPA que se mapeará a una tabla de base de datos
@AllArgsConstructor // Genera un constructor con todos los atributos
@NoArgsConstructor // Genera un constructor sin argumentos
@Data // Genera getters, setters, toString, equals y hashCode automáticamente
@Table(name = "sucursales") // Especifica el nombre de la tabla en la base de datos
public class Sucursal {

    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento de la clave primaria
    private Long id;

    private String nombre; // Nombre de la sucursal

    private String direccion; // Dirección de la sucursal

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL)
    // Relación uno a muchos con la entidad Venta
    // "mappedBy" indica que el lado inverso de la relación es la propiedad "sucursal" en Venta
    // cascade = CascadeType.ALL asegura que las operaciones en Sucursal se propaguen a las ventas
    private List<Venta> ventas = new ArrayList<>(); // Lista de ventas asociadas a la sucursal
}
