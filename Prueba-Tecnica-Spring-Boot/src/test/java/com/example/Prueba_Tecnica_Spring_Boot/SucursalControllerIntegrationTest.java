package com.example.Prueba_Tecnica_Spring_Boot;

import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalCreateDTO;
import com.example.Prueba_Tecnica_Spring_Boot.repository.SucursalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración para el controlador de Sucursal.
 * Verifica la creación y la consulta de sucursales a través de la API REST.
 * Se ejecutan dentro de una transacción que se revierte automáticamente.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SucursalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Objeto para simular solicitudes HTTP a los endpoints

    @Autowired
    private SucursalRepository sucursalRepository; // Repositorio para interacción directa con la base de datos

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos Java a JSON y viceversa

    /**
     * Test de integración que crea una sucursal y luego la lista.
     * Verifica que:
     * 1. La sucursal se crea correctamente y retorna un ID.
     * 2. La sucursal recién creada aparece en la lista de todas las sucursales.
     */
    @Test
    void crearYListarSucursal() throws Exception {
        SucursalCreateDTO dto = new SucursalCreateDTO();
        dto.setNombre("Sucursal Centro");
        dto.setDireccion("Av. Principal 123");

        // Realiza POST para crear la sucursal y valida la respuesta
        mockMvc.perform(post("/api/sucursales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()) // Verifica que el estado HTTP sea 200 OK
                .andExpect(jsonPath("$.id").exists()) // Verifica que el ID exista
                .andExpect(jsonPath("$.nombre").value("Sucursal Centro")); // Verifica que el nombre sea correcto

        // Realiza GET para listar sucursales y valida que la creada esté presente
        mockMvc.perform(get("/api/sucursales"))
                .andExpect(status().isOk()) // Verifica estado HTTP 200 OK
                .andExpect(jsonPath("$[0].nombre").value("Sucursal Centro")); // Verifica el nombre de la primera sucursal
    }
}

