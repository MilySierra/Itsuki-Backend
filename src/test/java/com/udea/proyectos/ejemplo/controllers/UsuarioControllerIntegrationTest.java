package com.udea.proyectos.ejemplo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.udea.proyectos.ejemplo.dto.UsuarioDTO;
import com.udea.proyectos.ejemplo.repositories.CarritoRepository;
import com.udea.proyectos.ejemplo.repositories.UsuarioRepository;

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
    static final String EMAIL = "juan@test.com";
    static final String CONTRASENA = "123456";
    static final String RUTA = "/usuario";

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
        dto.setEmail(EMAIL);
        dto.setContrasena(CONTRASENA);

        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity(
            baseUrl + RUTA, dto, UsuarioDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Juan", response.getBody().getNombre());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals("No puedes saber lol", response.getBody().getContrasena());
    }

    @Test
    void crearUsuario_emailDuplicado_retornaError() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan");
        dto.setEmail(EMAIL);
        dto.setContrasena(CONTRASENA);

        restTemplate.postForEntity(baseUrl + RUTA, dto, UsuarioDTO.class);

        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + RUTA, dto, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void login_exitoso_retorna200() {
        // Primero crear usuario
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan");
        dto.setEmail(EMAIL);
        dto.setContrasena(CONTRASENA);
        restTemplate.postForEntity(baseUrl + RUTA, dto, UsuarioDTO.class);

        // Luego login
        UsuarioDTO loginDTO = new UsuarioDTO();
        loginDTO.setEmail(EMAIL);
        loginDTO.setContrasena(CONTRASENA);

        ResponseEntity<UsuarioDTO> response = restTemplate.postForEntity(
            baseUrl + "/usuario/login", loginDTO, UsuarioDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void login_usuarioInexistente_retorna404() {
        UsuarioDTO loginDTO = new UsuarioDTO();
        loginDTO.setEmail("noexiste@test.com");
        loginDTO.setContrasena(CONTRASENA);

        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/usuario/login", loginDTO, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}