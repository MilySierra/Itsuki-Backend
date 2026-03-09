package com.udea.proyectos.ejemplo.controllers;

import com.udea.proyectos.ejemplo.dto.ProductoDTO;
import com.udea.proyectos.ejemplo.entities.Producto;
import com.udea.proyectos.ejemplo.repositories.CarritoRepository;
import com.udea.proyectos.ejemplo.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    private String baseUrl;
    private Producto productoGuardado;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        carritoRepository.deleteAll();
        productoRepository.deleteAll();

        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setTipo("electronica");
        producto.setDescripcion("Laptop gaming");
        producto.setPrecio(new BigDecimal("1500.00"));
        producto.setImagen("laptop.jpg");
        productoGuardado = productoRepository.save(producto);
    }

    @Test
    void obtenerTodo_retornaListaConProductos() {
    ResponseEntity<ProductoDTO[]> response = restTemplate.getForEntity(
            baseUrl + "/producto", ProductoDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().length == 0);
    }

    @Test
    void obtenerPorTipo_retornaProductosFiltrados() {
        ResponseEntity<ProductoDTO[]> response = restTemplate.getForEntity(
            baseUrl + "/producto/electronica", ProductoDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().length == 0);
    }

    @Test
    void obtenerPorTipo_tipoInexistente_retornaListaVacia() {
        ResponseEntity<ProductoDTO[]> response = restTemplate.getForEntity(
            baseUrl + "/producto/inexistente", ProductoDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length == 0);
    }

    @Test
    void obtenerPorId_idExistente_retornaProducto() {
        ResponseEntity<ProductoDTO> response = restTemplate.getForEntity(
            baseUrl + "/" + productoGuardado.getId(), ProductoDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Laptop", response.getBody().getNombre());
        assertEquals("electronica", response.getBody().getTipo());
    }

    @Test
    void obtenerPorId_idInexistente_retornaError() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            baseUrl + "/9999", String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}