package com.udea.proyectos.ejemplo.services.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udea.proyectos.ejemplo.dto.CarritoDTO;
import com.udea.proyectos.ejemplo.entities.Carrito;
import com.udea.proyectos.ejemplo.entities.Producto;
import com.udea.proyectos.ejemplo.entities.Usuario;
import com.udea.proyectos.ejemplo.repositories.CarritoRepository;
import com.udea.proyectos.ejemplo.repositories.ProductoRepository;
import com.udea.proyectos.ejemplo.repositories.UsuarioRepository;
import com.udea.proyectos.ejemplo.services.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public static CarritoDTO convertToDto(Carrito carrito){
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setId(carrito.getId());
        carritoDTO.setCantidad(carrito.getCantidad());
        carritoDTO.setId_usuario(carrito.getUsuario().getId());
        carritoDTO.setId_producto(carrito.getProducto().getId());
        carritoDTO.setNombre_producto(carrito.getProducto().getNombre());
        carritoDTO.setPrecio_producto(carrito.getProducto().getPrecio());
        carritoDTO.setSubtotal(carrito.getProducto().getPrecio().multiply(BigDecimal.valueOf(carrito.getCantidad())));

        return carritoDTO;
    }

    @Override
    public CarritoDTO guardarProducto(long id_usuario, long id_producto) {
        Usuario usuario = usuarioRepository.findById(id_usuario)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(id_producto)
        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Optional<Carrito> carroExistente = carritoRepository.findByUsuarioAndProducto(usuario, producto);

        Carrito carrito;
        if (carroExistente.isPresent()){
            carrito = carroExistente.get();
            carrito.setCantidad(carrito.getCantidad()+1);
        } else {
            carrito = new Carrito();
            carrito.setUsuario(usuario);
            carrito.setProducto(producto);
            carrito.setCantidad(1);
        }

        Carrito guardado = carritoRepository.save(carrito);

        return convertToDto(guardado);
    }

    @Override
    public List<CarritoDTO> obtenerCarrito(long id_usuario) {
        if (usuarioRepository.findById(id_usuario).isPresent()){
            List<CarritoDTO> carrito = new ArrayList<>();
            for (Carrito c: carritoRepository.findByUsuario(usuarioRepository.findById(id_usuario).get())){
                carrito.add(convertToDto(c));
            }
            return carrito;
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
    }
    
}
