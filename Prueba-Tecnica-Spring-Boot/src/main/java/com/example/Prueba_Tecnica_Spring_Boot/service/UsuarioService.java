package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.model.Usuario;
import com.example.Prueba_Tecnica_Spring_Boot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //buscar un usuario por su nombre
    public Usuario findByUsername(String username){
        return usuarioRepository.findAll()
                .stream().filter(u->u.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    //registrar un usuario
    public String save(Usuario usuario){
        String username = usuario.getUsername();
        if (findByUsername(username)!=null){
            return "Nombre de usuario ya registrado, elija otro.";
        }
        usuarioRepository.save(usuario);
        return "Usuario registration";
    }
}
