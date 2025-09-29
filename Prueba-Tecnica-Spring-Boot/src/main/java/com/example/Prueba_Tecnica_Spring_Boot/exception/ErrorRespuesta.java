package com.example.Prueba_Tecnica_Spring_Boot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorRespuesta {
    private int status;
    private String mensaje;
    private long timestamp;
}
