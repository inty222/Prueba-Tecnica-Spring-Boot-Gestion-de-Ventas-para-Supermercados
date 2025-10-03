package com.example.Prueba_Tecnica_Spring_Boot;

import com.example.Prueba_Tecnica_Spring_Boot.controller.ProductoController;
import com.example.Prueba_Tecnica_Spring_Boot.dto.ProductoDto;
import com.example.Prueba_Tecnica_Spring_Boot.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {
//test unitario
    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private List<ProductoDto> productos;

    @BeforeEach
    public void setUp() {
        productos = List.of(
                new ProductoDto(1L, "Manzana Roja", 1.2, "Frutas", 150),
                new ProductoDto(2L, "Leche Entera", 0.95, "Lácteos", 80),
                new ProductoDto(3L, "Pan Integral", 1.5, "Panadería", 50),
                new ProductoDto(4L, "Queso Gouda", 2.75, "Lácteos", 40),
                new ProductoDto(5L, "Tomate Cherry", 2.0, "Verduras", 120)
        );
    }

    @Test
    public void testListarProductos() {
        when(productoService.listarProductos()).thenReturn(productos);

        ResponseEntity<List<ProductoDto>> respuesta = productoController.listarProductos();

        assertNotNull(respuesta);
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(5, respuesta.getBody().size());
        verify(productoService, times(1)).listarProductos();
    }
}
