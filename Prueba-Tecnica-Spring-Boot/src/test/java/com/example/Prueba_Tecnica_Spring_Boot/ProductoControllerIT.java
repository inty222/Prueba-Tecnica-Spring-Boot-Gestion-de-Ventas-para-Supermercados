package com.example.Prueba_Tecnica_Spring_Boot;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PruebaTecnicaSpringBootApplication.class) // Apunta a tu clase main
@AutoConfigureMockMvc
@Transactional
public class ProductoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSaveProducto() throws Exception {
        String json = "{"
                + "\"nombreProducto\":\"Manzana Roja\","
                + "\"precio\":1.2,"
                + "\"categoria\":\"Frutas\","
                + "\"stock\":150"
                + "}";

        mockMvc.perform(post("/api/productos/nuevoproducto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Producto registrado correctamente")); // Ajusta según tu respuesta real
    }

    @Test
    public void testListarProductos() throws Exception {
        String json = "{"
                + "\"nombreProducto\":\"Leche Entera\","
                + "\"precio\":0.95,"
                + "\"categoria\":\"Lácteos\","
                + "\"stock\":80"
                + "}";

        mockMvc.perform(post("/api/productos/nuevoproducto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreProducto").value("Leche Entera"));
    }
}
