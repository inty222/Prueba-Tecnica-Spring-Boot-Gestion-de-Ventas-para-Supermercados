package com.example.Prueba_Tecnica_Spring_Boot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class AuthRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
