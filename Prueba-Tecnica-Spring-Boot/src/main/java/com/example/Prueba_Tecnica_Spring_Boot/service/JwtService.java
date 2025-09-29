package com.example.Prueba_Tecnica_Spring_Boot.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    String key = "HolaMuyBuenasTardesEstoEsUnaClaveSegurisima1234567890";
    SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());
    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(secretKey,Jwts.SIG.HS256)
                .compact();
    }

    public String validateToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();

    }
}
