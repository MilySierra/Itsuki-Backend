package com.udea.proyectos.ejemplo.services.Impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udea.proyectos.ejemplo.dto.ProductoDTO;
import com.udea.proyectos.ejemplo.entities.Producto;
import com.udea.proyectos.ejemplo.repositories.ProductoRepository;
import com.udea.proyectos.ejemplo.services.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoDAO;

    public static ProductoDTO convertToDto(Producto producto){
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setTipo(producto.getTipo());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setImagen(producto.getImagen());
        return productoDTO;
    }

    @Override
    public List<ProductoDTO> obtenerPorTipo(String tipo) {
        List<ProductoDTO> productos = new ArrayList<>();
        for (Producto p :  productoDAO.findByTipo(tipo)){
            productos.add(convertToDto(p));
        } 
        return productos;
    }

    @Override
    public List<ProductoDTO> obtenerTodo() {
        List<ProductoDTO> productos = new ArrayList<>();
        for (Producto p : productoDAO.findAll()){
            productos.add(convertToDto(p));
        } 
        return productos;
    }
    
}
