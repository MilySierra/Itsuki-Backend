package com.udea.proyectos.ejemplo.services;

import java.util.List;

import com.udea.proyectos.ejemplo.dto.ProductoDTO;

public interface ProductoService {

    List<ProductoDTO> obtenerPorTipo(String tipo);
    List<ProductoDTO> obtenerTodo();
    ProductoDTO obtenerPorId(long id);
    
}
