package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.model.AuthRequest;
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
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        AuthRequest user = usuarioService.findByUsername(username);
        if (user!=null&&password.equals(user.getPassword())){
            String token = jwtService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o contraseña incorrectos");
    }
    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody AuthRequest authRequest){
        if (!authRequest.getUsername().isBlank()&&!authRequest.getUsername().isEmpty()
        &&!authRequest.getPassword().isEmpty()&&!authRequest.getPassword().isBlank()){
            usuarioService.save(authRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
