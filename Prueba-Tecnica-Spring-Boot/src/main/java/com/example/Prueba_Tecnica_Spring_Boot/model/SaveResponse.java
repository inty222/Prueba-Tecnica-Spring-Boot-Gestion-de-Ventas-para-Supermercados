package com.example.Prueba_Tecnica_Spring_Boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveResponse {
    private HttpStatus status;
    private String message;
}
