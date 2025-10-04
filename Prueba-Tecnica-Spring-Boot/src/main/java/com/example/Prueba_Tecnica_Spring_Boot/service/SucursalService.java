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
        dto.setId(sucursal.getId());
        dto.setNombre(sucursal.getNombre());
        dto.setDireccion(sucursal.getDireccion());
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
        if (sucursalRepository.existsByNombre(dto.getNombre())) {
            throw new RuntimeException("Ya existe una sucursal con ese nombre");
        }
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        return mapToDTO(sucursalRepository.save(sucursal));
    }

    // Actualiza una sucursal existente por ID
    public SucursalDTO actualizarSucursal(Long id, SucursalCreateDTO dto) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNotFoundException(id));
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        return mapToDTO(sucursalRepository.save(sucursal));
    }

    // Elimina una sucursal por su ID
    public void eliminarSucursal(Long id) {
        if (!sucursalRepository.existsById(id)) {
            throw new SucursalNotFoundException(id);
        }
        sucursalRepository.deleteById(id);
    }
}
