package com.example.Prueba_Tecnica_Spring_Boot.repository;

import com.example.Prueba_Tecnica_Spring_Boot.model.AuthRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<AuthRequest, Long> {
}
