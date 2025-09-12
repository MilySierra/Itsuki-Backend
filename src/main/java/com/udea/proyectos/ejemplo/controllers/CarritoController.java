package com.udea.proyectos.ejemplo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.proyectos.ejemplo.dto.CarritoDTO;
import com.udea.proyectos.ejemplo.services.CarritoService;

@RestController
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;

    @PostMapping("/carrito/{id_usuario}/{id_producto}")
    public ResponseEntity<CarritoDTO> guardarProducto(@PathVariable long id_usuario, @PathVariable long id_producto){
        CarritoDTO guardado = carritoService.guardarProducto(id_usuario, id_producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping("/carrito/{id_usuario}")
    public ResponseEntity<List<CarritoDTO>> traerCarrito(@PathVariable long id_usuario){
        List<CarritoDTO> carrito = carritoService.obtenerCarrito(id_usuario);
        return ResponseEntity.ok(carrito);
    }
    
    @PutMapping("/carrito/{id}")
    public ResponseEntity<CarritoDTO> editarCarrito(@PathVariable long id){
        CarritoDTO carritoDTO = carritoService.eliminarProducto(id);
        return ResponseEntity.ok(carritoDTO);
    }

    @DeleteMapping("/carrito/{id}")
    public ResponseEntity<Boolean> eliminarCarrito(@PathVariable long id){
        Boolean eliminado = carritoService.eliminar(id);

        if (eliminado){
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
