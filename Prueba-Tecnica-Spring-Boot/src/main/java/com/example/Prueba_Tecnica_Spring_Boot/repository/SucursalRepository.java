package com.example.Prueba_Tecnica_Spring_Boot.repository;

import com.example.Prueba_Tecnica_Spring_Boot.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    boolean existsByNombre(String nombre);

}

