package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.model.SaveResponse;
import com.example.Prueba_Tecnica_Spring_Boot.model.Usuario;
import com.example.Prueba_Tecnica_Spring_Boot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public SaveResponse save(Usuario usuario){
        String username = usuario.getUsername();
        if (findByUsername(username)!=null){
            return new SaveResponse(HttpStatus.CONFLICT,"El nombre de usuario ya esta registrado, pruebe con otro nombre.");
        }
        usuarioRepository.save(usuario);
        return new SaveResponse(HttpStatus.CREATED,"Usuario registrado exitosamente");
    }
}
