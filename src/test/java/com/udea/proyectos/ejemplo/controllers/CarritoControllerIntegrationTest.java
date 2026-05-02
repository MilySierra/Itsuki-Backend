package com.udea.proyectos.ejemplo.controllers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.udea.proyectos.ejemplo.dto.CarritoDTO;
import com.udea.proyectos.ejemplo.entities.Producto;
import com.udea.proyectos.ejemplo.entities.Usuario;
import com.udea.proyectos.ejemplo.repositories.CarritoRepository;
import com.udea.proyectos.ejemplo.repositories.ProductoRepository;
import com.udea.proyectos.ejemplo.repositories.UsuarioRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CarritoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String baseUrl;
    private Usuario usuarioGuardado;
    private Producto productoGuardado;
    static final String RUTA = "/carrito/";

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        carritoRepository.deleteAll();
        usuarioRepository.deleteAll();
        productoRepository.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setEmail("juan@test.com");
        usuario.setContrasena(passwordEncoder.encode("123456"));
        usuarioGuardado = usuarioRepository.save(usuario);

        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setTipo("electronica");
        producto.setDescripcion("Laptop gaming");
        producto.setPrecio(new BigDecimal("1500.00"));
        producto.setImagen("laptop.jpg");
        productoGuardado = productoRepository.save(producto);
    }

    @Test
    void guardarProducto_retorna201() {
        ResponseEntity<CarritoDTO> response = restTemplate.postForEntity(
            baseUrl + RUTA + usuarioGuardado.getId() + "/" + productoGuardado.getId(),
            null, CarritoDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getCantidad());
    }

    @Test
    void guardarProducto_mismoProducto_incrementaCantidad() {
        restTemplate.postForEntity(
            baseUrl + RUTA + usuarioGuardado.getId() + "/" + productoGuardado.getId(),
            null, CarritoDTO.class);

        ResponseEntity<CarritoDTO> response = restTemplate.postForEntity(
            baseUrl + RUTA + usuarioGuardado.getId() + "/" + productoGuardado.getId(),
            null, CarritoDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(2, response.getBody().getCantidad());
    }

    @Test
    void obtenerCarrito_retornaLista() {
        restTemplate.postForEntity(
            baseUrl + RUTA+ usuarioGuardado.getId() + "/" + productoGuardado.getId(),
            null, CarritoDTO.class);

        ResponseEntity<CarritoDTO[]> response = restTemplate.getForEntity(
            baseUrl + RUTA + usuarioGuardado.getId(), CarritoDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    void eliminarProducto_decrementa_cantidad() {
        // Agregar dos veces
        restTemplate.postForEntity(
            baseUrl + RUTA + usuarioGuardado.getId() + "/" + productoGuardado.getId(),
            null, CarritoDTO.class);
        ResponseEntity<CarritoDTO> agregado = restTemplate.postForEntity(
            baseUrl + RUTA + usuarioGuardado.getId() + "/" + productoGuardado.getId(),
            null, CarritoDTO.class);

        long carritoId = agregado.getBody().getId();

        ResponseEntity<CarritoDTO> response = restTemplate.exchange(
            baseUrl + RUTA + carritoId,
            HttpMethod.PUT, null, CarritoDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getCantidad());
    }

    @Test
    void eliminarCarrito_retornaTrue() {
        ResponseEntity<CarritoDTO> agregado = restTemplate.postForEntity(
            baseUrl + RUTA + usuarioGuardado.getId() + "/" + productoGuardado.getId(),
            null, CarritoDTO.class);

        long carritoId = agregado.getBody().getId();

        ResponseEntity<Boolean> response = restTemplate.exchange(
            baseUrl + RUTA + carritoId,
            HttpMethod.DELETE, null, Boolean.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void guardarProducto_usuarioInexistente_retornaError() {
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/carrito/9999/" + productoGuardado.getId(),
            null, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}