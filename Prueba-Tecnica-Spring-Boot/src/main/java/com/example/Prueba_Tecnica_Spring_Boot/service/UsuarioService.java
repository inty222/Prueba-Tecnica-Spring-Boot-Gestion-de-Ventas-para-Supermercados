package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.model.AuthRequest;
import com.example.Prueba_Tecnica_Spring_Boot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AuthRequest findByUsername(String username){
        return usuarioRepository.findAll()
                .stream().filter(u->u.getUsername().equals(username))
                .findFirst().orElse(null);
    }
    public String save(AuthRequest authRequest){
        String username = authRequest.getUsername();
        if (username.equals(findByUsername(username).getUsername())){
            return "Nombre de usuario ya registrado, elija otro.";
        }
        usuarioRepository.save(authRequest);
        return "Usuario registrado";
    }
}
