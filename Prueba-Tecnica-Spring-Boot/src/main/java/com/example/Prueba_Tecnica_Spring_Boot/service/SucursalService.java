package com.example.Prueba_Tecnica_Spring_Boot.service;

import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalCreateDTO;
import com.example.Prueba_Tecnica_Spring_Boot.dto.SucursalDTO;
import com.example.Prueba_Tecnica_Spring_Boot.exception.SucursalNotFoundException;
import com.example.Prueba_Tecnica_Spring_Boot.model.Sucursal;
import com.example.Prueba_Tecnica_Spring_Boot.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SucursalService {

    // Repositorio para acceder a la base de datos de sucursales
    private final SucursalRepository sucursalRepository;

    // Convierte un objeto Sucursal a un DTO
    private SucursalDTO mapToDTO(Sucursal sucursal) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(sucursal.getId());       // Asigna el ID
        dto.setNombre(sucursal.getNombre());   // Asigna el nombre
        dto.setDireccion(sucursal.getDireccion()); // Asigna la dirección
        return dto;
    }

    // Retorna la lista de todas las sucursales en formato DTO
    public List<SucursalDTO> listarSucursales() {
        return sucursalRepository.findAll()
                .stream()
                .map(this::mapToDTO)   // Convierte cada entidad a DTO
                .collect(Collectors.toList());
    }

    // Obtiene una sucursal por su ID
    public SucursalDTO obtenerSucursalPorId(Long id) {
        return sucursalRepository.findById(id)
                .map(this::mapToDTO)  // Convierte a DTO si se encuentra
                .orElseThrow(() -> new SucursalNotFoundException(id)); // Lanza excepción si no existe
    }

    // Crea una nueva sucursal a partir de un DTO de creación
    public SucursalDTO crearSucursal(SucursalCreateDTO dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());   // Asigna nombre
        sucursal.setDireccion(dto.getDireccion()); // Asigna dirección
        return mapToDTO(sucursalRepository.save(sucursal)); // Guarda y devuelve DTO
    }

    // Actualiza una sucursal existente por ID
    public SucursalDTO actualizarSucursal(Long id, SucursalCreateDTO dto) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNotFoundException(id)); // Lanza excepción si no existe
        sucursal.setNombre(dto.getNombre());       // Actualiza nombre
        sucursal.setDireccion(dto.getDireccion()); // Actualiza dirección
        return mapToDTO(sucursalRepository.save(sucursal)); // Guarda y devuelve DTO actualizado
    }

    // Elimina una sucursal por su ID
    public void eliminarSucursal(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new SucursalNotFoundException(id); // Lanza excepción si no existe
        }
        sucursalRepository.deleteById(id); // Elimina la sucursal
    }
}
