package com.udea.proyectos.ejemplo.controllers;

import com.udea.proyectos.ejemplo.dto.UsuarioDTO;
import com.udea.proyectos.ejemplo.repositories.CarritoRepository;
import com.udea.proyectos.ejemplo.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UsuarioControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        carritoRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Test
    void crearUsuario_exitoso_retorna201() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan");
        dto.setEmail("juan@test.com");
        dto.setContrasena("123456");

        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity(
            baseUrl + "/usuario", dto, UsuarioDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Juan", response.getBody().getNombre());
        assertEquals("juan@test.com", response.getBody().getEmail());
        assertEquals("No puedes saber lol", response.getBody().getContrasena());
    }

    @Test
    void crearUsuario_emailDuplicado_retornaError() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan");
        dto.setEmail("juan@test.com");
        dto.setContrasena("123456");

        restTemplate.postForEntity(baseUrl + "/usuario", dto, UsuarioDTO.class);

        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/usuario", dto, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void login_exitoso_retorna200() {
        // Primero crear usuario
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan");
        dto.setEmail("juan@test.com");
        dto.setContrasena("123456");
        restTemplate.postForEntity(baseUrl + "/usuario", dto, UsuarioDTO.class);

        // Luego login
        UsuarioDTO loginDTO = new UsuarioDTO();
        loginDTO.setEmail("juan@test.com");
        loginDTO.setContrasena("123456");

        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity(
            baseUrl + "/usuario/login", loginDTO, UsuarioDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("juan@test.com", response.getBody().getEmail());
    }

    @Test
    void login_usuarioInexistente_retorna404() {
        UsuarioDTO loginDTO = new UsuarioDTO();
        loginDTO.setEmail("noexiste@test.com");
        loginDTO.setContrasena("123456");

        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/usuario/login", loginDTO, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}