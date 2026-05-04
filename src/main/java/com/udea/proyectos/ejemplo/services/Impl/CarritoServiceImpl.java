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

    static final String RESPUESTA = "Producto no encontrado";

    public static CarritoDTO convertToDto(Carrito carrito){
        CarritoDTO carritoDTO = new CarritoDTO();
        carritoDTO.setId(carrito.getId());
        carritoDTO.setCantidad(carrito.getCantidad());
        carritoDTO.setId_usuario(carrito.getUsuario().getId());
        carritoDTO.setId_producto(carrito.getProducto().getId());
        carritoDTO.setNombre_producto(carrito.getProducto().getNombre());
        carritoDTO.setPrecio_producto(carrito.getProducto().getPrecio());
        carritoDTO.setImagen(carrito.getProducto().getImagen());
        carritoDTO.setDescripcion(carrito.getProducto().getDescripcion());
        carritoDTO.setSubtotal(carrito.getProducto().getPrecio().multiply(BigDecimal.valueOf(carrito.getCantidad())));

        return carritoDTO;
    }

    @Override
    public CarritoDTO guardarProducto(long idUsuario, long idProducto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(idProducto)
        .orElseThrow(() -> new RuntimeException(RESPUESTA));

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
    public List<CarritoDTO> obtenerCarrito(long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new NoSuchElementException("El usuario no existe"));

        List<CarritoDTO> carrito = new ArrayList<>();
        for (Carrito c: carritoRepository.findByUsuario(usuario)){
            carrito.add(convertToDto(c));
        }
        return carrito;
    }

    @Override
    public CarritoDTO eliminarProducto(long id) {
        Carrito producto = carritoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(RESPUESTA));

        if (producto.getCantidad()>1){
            producto.setCantidad(producto.getCantidad()-1);
            carritoRepository.save(producto);
            return convertToDto(producto);
        } else{
            carritoRepository.delete(producto);
            return convertToDto(producto);
        }
    }
 
    @Override
    public boolean eliminar(long id) {
        Carrito producto = carritoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(RESPUESTA));
        carritoRepository.delete(producto);
        return true;
    }
    
}
