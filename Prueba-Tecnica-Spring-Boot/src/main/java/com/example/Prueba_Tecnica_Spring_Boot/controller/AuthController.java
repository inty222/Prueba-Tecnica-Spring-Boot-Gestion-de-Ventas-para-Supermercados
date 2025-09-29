package com.example.Prueba_Tecnica_Spring_Boot.controller;

import com.example.Prueba_Tecnica_Spring_Boot.model.AuthRequest;
import com.example.Prueba_Tecnica_Spring_Boot.service.JwtService;
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
    private JwtService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        if ("user".equals(authRequest.getUsername())&&"1234".equals(authRequest.getPassword())){
            String token = service.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
