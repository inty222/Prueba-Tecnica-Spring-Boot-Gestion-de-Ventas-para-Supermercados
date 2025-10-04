package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.model.SaveResponse;
import com.example.Prueba_Tecnica_Spring_Boot.model.Usuario;
import com.example.Prueba_Tecnica_Spring_Boot.service.JwtService;
import com.example.Prueba_Tecnica_Spring_Boot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario){
        String username = usuario.getUsername();
        String password = usuario.getPassword();
        Usuario user = usuarioService.findByUsername(username);
        if (user!=null&&password.equals(user.getPassword())){
            String token = jwtService.generateToken(usuario.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario){
        SaveResponse response;
        if (!usuario.getUsername().isBlank()&&!usuario.getUsername().isEmpty()
        &&!usuario.getPassword().isEmpty()&&!usuario.getPassword().isBlank()){
            response = usuarioService.save(usuario);
            return ResponseEntity.status(response.getStatus()).body(response.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
