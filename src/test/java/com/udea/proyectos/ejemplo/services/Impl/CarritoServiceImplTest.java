package com.udea.proyectos.ejemplo.services.Impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.udea.proyectos.ejemplo.dto.CarritoDTO;
import com.udea.proyectos.ejemplo.entities.Carrito;
import com.udea.proyectos.ejemplo.entities.Producto;
import com.udea.proyectos.ejemplo.entities.Usuario;
import com.udea.proyectos.ejemplo.repositories.CarritoRepository;
import com.udea.proyectos.ejemplo.repositories.ProductoRepository;
import com.udea.proyectos.ejemplo.repositories.UsuarioRepository;

public class CarritoServiceImplTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private CarritoServiceImpl carritoService;


    private Usuario usuario;
    private Producto producto;

    static final String PRODUCTO = "Laptop";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana");
        usuario.setEmail("ana@mail.com");

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre(PRODUCTO);
        producto.setPrecio(new BigDecimal("1500.00"));
    }

    @Test
    void testGuardarProducto_nuevoEnCarrito() {

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(carritoRepository.findByUsuarioAndProducto(usuario, producto))
            .thenReturn(Optional.empty());

        Carrito carritoGuardado = new Carrito();
        carritoGuardado.setId(1L);  
        carritoGuardado.setUsuario(usuario);
        carritoGuardado.setProducto(producto);
        carritoGuardado.setCantidad(1);

        when(carritoRepository.save(any(Carrito.class))).thenReturn(carritoGuardado);

        
        CarritoDTO resultado = carritoService.guardarProducto(1L, 1L);


        assertEquals(1, resultado.getCantidad());
        assertEquals(PRODUCTO, resultado.getNombre_producto());
        assertEquals(new BigDecimal("1500.00"), resultado.getSubtotal());
    }

    @Test
    void testGuardarProducto_incrementaCantidadSiYaExiste() {

        Carrito carritoExistente = new Carrito();
        carritoExistente.setId(1L);
        carritoExistente.setUsuario(usuario);
        carritoExistente.setProducto(producto);
        carritoExistente.setCantidad(2);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(carritoRepository.findByUsuarioAndProducto(usuario, producto))
            .thenReturn(Optional.of(carritoExistente));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carritoExistente);


        CarritoDTO resultado = carritoService.guardarProducto(1L, 1L);


        assertEquals(3, resultado.getCantidad());
    }

    @Test
    void testGuardarProducto_lanzaExcepcionSiUsuarioNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> carritoService.guardarProducto(99L, 1L));
    }

    @Test
    void testGuardarProducto_lanzaExcepcionSiProductoNoExiste() {
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> carritoService.guardarProducto(1L, 99L));
    }

    @Test
    void testObtenerCarrito_retornaListaDTO() {

        Carrito c = new Carrito();
        c.setId(1L);
        c.setUsuario(usuario);
        c.setProducto(producto);
        c.setCantidad(2);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(carritoRepository.findByUsuario(usuario)).thenReturn(Arrays.asList(c));


        List<CarritoDTO> resultado = carritoService.obtenerCarrito(1L);


        assertEquals(1, resultado.size());
        assertEquals(2, resultado.get(0).getCantidad());
        assertEquals(PRODUCTO, resultado.get(0).getNombre_producto());
    }

    @Test
    void testObtenerCarrito_lanzaExcepcionSiUsuarioNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
            () -> carritoService.obtenerCarrito(99L));
    }

    @Test
    void testEliminarProducto_decrementaCantidadSiEsMayorA1() {

        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuario(usuario);
        carrito.setProducto(producto);
        carrito.setCantidad(3);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);


        CarritoDTO resultado = carritoService.eliminarProducto(1L);


        assertEquals(2, resultado.getCantidad());
        verify(carritoRepository).save(carrito);
    }

    @Test
    void testEliminarProducto_eliminaRegistroSiCantidadEs1() {
       
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuario(usuario);
        carrito.setProducto(producto);
        carrito.setCantidad(1);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));


        CarritoDTO resultado = carritoService.eliminarProducto(1L);
        
        assertEquals(1, resultado.getCantidad());
        verify(carritoRepository).delete(carrito); 
    }

    @Test
    void testEliminarProducto_lanzaExcepcionSiNoExiste() {
        
        when(carritoRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class,
            () -> carritoService.eliminarProducto(99L));
    }

    @Test
    void testEliminar_eliminaCorrectamente() {
        // ARRANGE
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuario(usuario);
        carrito.setProducto(producto);
        carrito.setCantidad(1);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));

        // ACT
        boolean resultado = carritoService.eliminar(1L);

        // ASSERT
        assertTrue(resultado);
        verify(carritoRepository).delete(carrito);
    }
}