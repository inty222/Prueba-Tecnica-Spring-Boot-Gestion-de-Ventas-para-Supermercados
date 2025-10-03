package com.example.Prueba_Tecnica_Spring_Boot.repository;

import com.example.Prueba_Tecnica_Spring_Boot.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    // Interfaz que extiende JpaRepository para permitir operaciones CRUD
    // JpaRepository<Sucursal, Long> indica que la entidad es Sucursal y la clave primaria es Long
    // Spring Data JPA generará automáticamente métodos como save, findById, findAll, deleteById, etc.
}

