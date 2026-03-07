package com.udea.proyectos.ejemplo.services.Impl;

import com.udea.proyectos.ejemplo.dto.CarritoDTO;
import com.udea.proyectos.ejemplo.entities.Carrito;
import com.udea.proyectos.ejemplo.entities.Producto;
import com.udea.proyectos.ejemplo.entities.Usuario;
import com.udea.proyectos.ejemplo.repositories.CarritoRepository;
import com.udea.proyectos.ejemplo.repositories.ProductoRepository;
import com.udea.proyectos.ejemplo.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
//import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CarritoServiceImplTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private CarritoServiceImpl carritoService;

    // Datos reutilizables en todos los tests
    private Usuario usuario;
    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana");
        usuario.setEmail("ana@mail.com");

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop");
        producto.setPrecio(new BigDecimal("1500.00"));
    }

    @Test
    void testGuardarProducto_nuevoEnCarrito() {
        // ARRANGE — producto que aún no está en el carrito
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

        // ACT
        CarritoDTO resultado = carritoService.guardarProducto(1L, 1L);

        // ASSERT
        assertEquals(1, resultado.getCantidad());
        assertEquals("Laptop", resultado.getNombre_producto());
        assertEquals(new BigDecimal("1500.00"), resultado.getSubtotal());
    }

    @Test
    void testGuardarProducto_incrementaCantidadSiYaExiste() {
        // ARRANGE — producto que YA está en el carrito con cantidad 2
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

        // ACT
        CarritoDTO resultado = carritoService.guardarProducto(1L, 1L);

        // ASSERT — debe ser 3 (2 + 1)
        assertEquals(3, resultado.getCantidad());
    }

    @Test
    void testGuardarProducto_lanzaExcepcionSiUsuarioNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
            () -> carritoService.guardarProducto(99L, 1L));
    }

    @Test
    void testObtenerCarrito_retornaListaDTO() {
        // ARRANGE
        Carrito c = new Carrito();
        c.setId(1L);
        c.setUsuario(usuario);
        c.setProducto(producto);
        c.setCantidad(2);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(carritoRepository.findByUsuario(usuario)).thenReturn(Arrays.asList(c));

        // ACT
        List<CarritoDTO> resultado = carritoService.obtenerCarrito(1L);

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(2, resultado.get(0).getCantidad());
        assertEquals("Laptop", resultado.get(0).getNombre_producto());
    }

    @Test
    void testObtenerCarrito_lanzaExcepcionSiUsuarioNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
            () -> carritoService.obtenerCarrito(99L));
    }

    @Test
    void testEliminarProducto_decrementaCantidadSiEsMayorA1() {
        // ARRANGE — cantidad 3, debe quedar en 2
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuario(usuario);
        carrito.setProducto(producto);
        carrito.setCantidad(3);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        // ACT
        CarritoDTO resultado = carritoService.eliminarProducto(1L);

        // ASSERT
        assertEquals(2, resultado.getCantidad());
        verify(carritoRepository).save(carrito);
    }

    @Test
    void testEliminarProducto_eliminaRegistroSiCantidadEs1() {
        // ARRANGE — cantidad 1, debe borrarse del carrito
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuario(usuario);
        carrito.setProducto(producto);
        carrito.setCantidad(1);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));

        // ACT
        CarritoDTO resultado = carritoService.eliminarProducto(1L);

        // ASSERT
        assertEquals(1, resultado.getCantidad());
        verify(carritoRepository).delete(carrito); // se borró, no se guardó
    }

    @Test
    void testEliminarProducto_lanzaExcepcionSiNoExiste() {
        // ARRANGE — el repositorio no encuentra nada con ese ID
        when(carritoRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT — verificamos que lanza la excepción
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