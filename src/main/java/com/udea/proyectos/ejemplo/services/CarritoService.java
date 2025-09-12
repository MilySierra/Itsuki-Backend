package com.udea.proyectos.ejemplo.services;

import java.util.List;

import com.udea.proyectos.ejemplo.dto.CarritoDTO;

public interface CarritoService {
    
    CarritoDTO guardarProducto(long id_usuario, long id_producto);
    List<CarritoDTO> obtenerCarrito(long id_usuario);
    CarritoDTO eliminarProducto(long id);
    boolean eliminar(long id);
}
