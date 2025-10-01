package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.model.AuthRequest;
import com.example.Prueba_Tecnica_Spring_Boot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AuthRequest findByUsername(String username){
        return usuarioRepository.findAll()
                .stream().filter(u->u.getUsername().equals(username))
                .findFirst().orElse(null);
    }
    public void save(AuthRequest authRequest){
        usuarioRepository.save(authRequest);
    }
}
