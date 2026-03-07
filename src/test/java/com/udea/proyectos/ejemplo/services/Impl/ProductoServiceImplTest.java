package com.udea.proyectos.ejemplo.services.Impl;

import com.udea.proyectos.ejemplo.dto.ProductoDTO;
import com.udea.proyectos.ejemplo.entities.Producto;
import com.udea.proyectos.ejemplo.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoDAO;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodo_retornaListaDeProductos() {
        // ARRANGE
        Producto p = new Producto();
        p.setId(1L);
        p.setNombre("Camiseta");
        p.setTipo("ropa");
        p.setPrecio(new BigDecimal("29.99"));

        when(productoDAO.findAll()).thenReturn(Arrays.asList(p));

        // ACT
        List<ProductoDTO> resultado = productoService.obtenerTodo();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Camiseta", resultado.get(0).getNombre());
        assertEquals("ropa", resultado.get(0).getTipo());
        verify(productoDAO, times(1)).findAll();
    }

    @Test
    void testObtenerPorTipo_retornaProductosFiltrados() {
        // ARRANGE
        Producto p = new Producto();
        p.setId(2L);
        p.setNombre("Zapatos");
        p.setTipo("calzado");
        p.setPrecio(new BigDecimal("59.99"));

        when(productoDAO.findByTipo("calzado")).thenReturn(Arrays.asList(p));

        // ACT
        List<ProductoDTO> resultado = productoService.obtenerPorTipo("calzado");

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Zapatos", resultado.get(0).getNombre());
    }

    @Test
    void testObtenerPorId_retornaProductoSiExiste() {
        // ARRANGE
        // 1. Creo el producto falso que simulará venir de la BD
        Producto p = new Producto();
        p.setId(3L);
        p.setNombre("Portatil");
        p.setTipo("electronica");
        p.setPrecio(new BigDecimal("1500.00"));

        //2. Le digo al mock que cuando busque por ID 3, devuelva ese producto
        when(productoDAO.findById(3L)).thenReturn(Optional.of(p));

        // ACT
        // 3. Llamo al metodo real que quiero probar
        ProductoDTO resultado = productoService.obtenerPorId(3L);

        // ASSERT
        // 4. Verifico que el DTO tiene los datos correctos (los mismos que el producto falso)
        assertEquals(3L, resultado.getId());
        assertEquals("Portatil", resultado.getNombre());
        assertEquals("electronica", resultado.getTipo());
    }

    @Test
    void testObtenerPorId_lanzaExcepcionSiNoExiste() {
        // ARRANGE
        when(productoDAO.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(RuntimeException.class, () -> productoService.obtenerPorId(99L));
    }
}