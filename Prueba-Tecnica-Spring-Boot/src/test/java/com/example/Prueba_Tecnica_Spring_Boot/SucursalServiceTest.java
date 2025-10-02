package com.example.Prueba_Tecnica_Spring_Boot;

import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalCreateDTO;
import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalDTO;
import com.example.Prueba_Tecnica_Spring_Boot.exception.SucursalNotFoundException;
import com.example.Prueba_Tecnica_Spring_Boot.model.Sucursal;
import com.example.Prueba_Tecnica_Spring_Boot.repository.SucursalRepository;
import com.example.Prueba_Tecnica_Spring_Boot.service.SucursalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para SucursalService.
 * Se verifica el comportamiento de creación, obtención, actualización y eliminación de sucursales.
 */
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository; // Mock del repositorio para simular operaciones en la base de datos

    @InjectMocks
    private SucursalService sucursalService; // Servicio que se va a probar

    /**
     * Inicializa los mocks antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testea la creación de una sucursal.
     * Verifica que el resultado no sea nulo y que los datos coincidan con los esperados.
     */
    @Test
    void testCrearSucursal() {
        SucursalCreateDTO dto = new SucursalCreateDTO("Sucursal 1", "Direccion 1");
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal 1");
        sucursal.setDireccion("Direccion 1");

        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursal); // Simula el guardado en BD

        SucursalDTO result = sucursalService.crearSucursal(dto);

        assertNotNull(result); // Verifica que no sea nulo
        assertEquals("Sucursal 1", result.getNombre()); // Verifica el nombre
        verify(sucursalRepository, times(1)).save(any(Sucursal.class)); // Asegura que se llamó al repositorio
    }

    /**
     * Testea la obtención de una sucursal por ID cuando existe.
     */
    @Test
    void testObtenerSucursalPorIdFound() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Test");
        sucursal.setDireccion("Direccion Test");

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));

        SucursalDTO result = sucursalService.obtenerSucursalPorId(1L);

        assertNotNull(result);
        assertEquals("Sucursal Test", result.getNombre());
    }

    /**
     * Testea la obtención de una sucursal por ID cuando no existe.
     * Se espera que lance SucursalNotFoundException.
     */
    @Test
    void testObtenerSucursalPorIdNotFound() {
        when(sucursalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(SucursalNotFoundException.class, () -> sucursalService.obtenerSucursalPorId(99L));
    }

    /**
     * Testea la obtención de todas las sucursales.
     * Verifica que el tamaño de la lista y los datos sean correctos.
     */
    @Test
    void testListarSucursales() {
        Sucursal sucursal1 = new Sucursal();
        sucursal1.setId(1L);
        sucursal1.setNombre("Sucursal A");
        sucursal1.setDireccion("Dir A");

        Sucursal sucursal2 = new Sucursal();
        sucursal2.setId(2L);
        sucursal2.setNombre("Sucursal B");
        sucursal2.setDireccion("Dir B");

        when(sucursalRepository.findAll()).thenReturn(Arrays.asList(sucursal1, sucursal2));

        var result = sucursalService.listarSucursales();

        assertEquals(2, result.size());
        assertEquals("Sucursal A", result.get(0).getNombre());
    }

    /**
     * Testea la actualización de una sucursal existente.
     * Verifica que el nombre y la dirección sean actualizados correctamente.
     */
    @Test
    void testActualizarSucursal() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Vieja");
        sucursal.setDireccion("Dir Vieja");

        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursal);

        SucursalCreateDTO dto = new SucursalCreateDTO("Sucursal Nueva", "Dir Nueva");
        SucursalDTO result = sucursalService.actualizarSucursal(1L, dto);

        assertEquals("Sucursal Nueva", result.getNombre());
        assertEquals("Dir Nueva", result.getDireccion());
    }

    /**
     * Testea la eliminación de una sucursal que no existe.
     * Se espera que lance SucursalNotFoundException.
     */
    @Test
    void testEliminarSucursalNotFound() {
        when(sucursalRepository.existsById(99L)).thenReturn(false);

        assertThrows(SucursalNotFoundException.class, () -> sucursalService.eliminarSucursal(99L));
    }
}

