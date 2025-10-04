package com.example.Prueba_Tecnica_Spring_Boot.repository;

import com.example.Prueba_Tecnica_Spring_Boot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
