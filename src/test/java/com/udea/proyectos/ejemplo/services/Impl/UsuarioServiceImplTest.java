package com.udea.proyectos.ejemplo.services.Impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.udea.proyectos.ejemplo.dto.UsuarioDTO;
import com.udea.proyectos.ejemplo.entities.Usuario;
import com.udea.proyectos.ejemplo.repositories.UsuarioRepository;

public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioDao;

    @Mock
    private PasswordEncoder passwordEncoder; // necesario porque el service lo usa

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    static final String EMAIL = "juan@mail.com";
    static final String CONTRASENA = "123456";

    @Test
    void testCrearUsuario_exitoso() {

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan");
        dto.setEmail(EMAIL);
        dto.setContrasena(CONTRASENA);

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setNombre("Juan");
        usuarioGuardado.setEmail(EMAIL);
        usuarioGuardado.setContrasena("hash123");

        when(usuarioDao.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(CONTRASENA)).thenReturn("hash123");
        when(usuarioDao.save(any(Usuario.class))).thenReturn(usuarioGuardado);


        UsuarioDTO resultado = usuarioService.crearUsuario(dto);


        assertEquals("Juan", resultado.getNombre());
        assertEquals(EMAIL, resultado.getEmail());
        assertEquals("No puedes saber lol", resultado.getContrasena());
        verify(passwordEncoder).encode(CONTRASENA);
    }

    @Test
    void testLogeo_exitoso() {

        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(EMAIL);
        dto.setContrasena(CONTRASENA);

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setNombre("Juan");
        usuarioGuardado.setEmail(EMAIL);
        usuarioGuardado.setContrasena(CONTRASENA);

        when(usuarioDao.findByEmail(EMAIL)).thenReturn(Optional.of(usuarioGuardado));
        when(passwordEncoder.matches(CONTRASENA, CONTRASENA)).thenReturn(true);
        when(usuarioDao.findUsuarioByEmail(EMAIL)).thenReturn(usuarioGuardado);

        // ACT
        UsuarioDTO resultado = usuarioService.login(dto);

        // ASSERT
        assertEquals("Juan", resultado.getNombre());
        assertEquals(EMAIL, resultado.getEmail());
        assertEquals("No puedes saber lol", resultado.getContrasena());
    }

    

    @Test
    void testCrearUsuario_lanzaExcepcionSiEmailYaExiste() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("existe@mail.com");
        dto.setContrasena(CONTRASENA);

        when(usuarioDao.findByEmail("existe@mail.com"))
            .thenReturn(Optional.of(new Usuario()));

        // ACT + ASSERT
        assertThrows(UnsupportedOperationException.class,
            () -> usuarioService.crearUsuario(dto));
    }

    @Test
    void testLogin_contrasenaIncorrecta_lanzaExcepcion() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(EMAIL);
        dto.setContrasena("wrongpass");

        Usuario usuario = new Usuario();
        usuario.setEmail(EMAIL);
        usuario.setContrasena("hashCorrecto");

        when(usuarioDao.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));
        when(usuarioDao.findUsuarioByEmail(EMAIL)).thenReturn(usuario);
        when(passwordEncoder.matches("wrongpass", "hashCorrecto")).thenReturn(false);

        // ACT + ASSERT
        assertThrows(ResponseStatusException.class,
            () -> usuarioService.login(dto));
    }

    @Test
    void testLogin_usuarioInexistente_lanzaExcepcion() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(EMAIL);
        dto.setContrasena("juan");

        when(usuarioDao.findByEmail(EMAIL)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(ResponseStatusException.class,
            () -> usuarioService.login(dto));
    }

    @Test
    void testCrearUsuario_lanzaExcepcionSiContraseñaNula() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(EMAIL);
        dto.setContrasena(null);

        // ACT + ASSERT
        assertThrows(UnsupportedOperationException.class,
            () -> usuarioService.crearUsuario(dto));
    }

    @Test
    void testCrearUsuario_lanzaExcepcionSiCorreoNulo() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(null);
        dto.setContrasena("hola");

        // ACT + ASSERT
        assertThrows(UnsupportedOperationException.class,
            () -> usuarioService.crearUsuario(dto));
    }
}