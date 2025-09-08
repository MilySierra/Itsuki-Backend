package com.udea.proyectos.ejemplo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.udea.proyectos.ejemplo.dto.ProductoDTO;

import com.udea.proyectos.ejemplo.services.ProductoService;

@RestController
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping("/producto/{tipo}")
    public ResponseEntity<List<ProductoDTO>> obtenerPorTipo(@PathVariable String tipo){
        List<ProductoDTO> productos = productoService.obtenerPorTipo(tipo);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/producto")
    public ResponseEntity<List<ProductoDTO>> obtenerTodo(){
        List<ProductoDTO> productos = productoService.obtenerTodo();
        return ResponseEntity.ok(productos);
    }
}
