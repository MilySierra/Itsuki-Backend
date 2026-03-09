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

    @Test
    void testCrearUsuario_exitoso() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Juan");
        dto.setEmail("juan@mail.com");
        dto.setContrasena("123456");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setNombre("Juan");
        usuarioGuardado.setEmail("juan@mail.com");
        usuarioGuardado.setContrasena("hash123");

        when(usuarioDao.findByEmail("juan@mail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("hash123");
        when(usuarioDao.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // ACT
        UsuarioDTO resultado = usuarioService.crearUsuario(dto);

        // ASSERT
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@mail.com", resultado.getEmail());
        assertEquals("No puedes saber lol", resultado.getContrasena()); // así lo definiste tú
        verify(passwordEncoder).encode("123456");
    }

    @Test
    void testLogeo_exitoso() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("juan@mail.com");
        dto.setContrasena("123456");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setNombre("Juan");
        usuarioGuardado.setEmail("juan@mail.com");
        usuarioGuardado.setContrasena("123456");

        when(usuarioDao.findByEmail("juan@mail.com")).thenReturn(Optional.of(usuarioGuardado));
        when(passwordEncoder.matches("123456", "123456")).thenReturn(true);
        when(usuarioDao.findUsuarioByEmail("juan@mail.com")).thenReturn(usuarioGuardado);

        // ACT
        UsuarioDTO resultado = usuarioService.login(dto);

        // ASSERT
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@mail.com", resultado.getEmail());
        assertEquals("No puedes saber lol", resultado.getContrasena()); // así lo definiste tú
    }

    

    @Test
    void testCrearUsuario_lanzaExcepcionSiEmailYaExiste() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("existe@mail.com");
        dto.setContrasena("123456");

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
        dto.setEmail("juan@mail.com");
        dto.setContrasena("wrongpass");

        Usuario usuario = new Usuario();
        usuario.setEmail("juan@mail.com");
        usuario.setContrasena("hashCorrecto");

        when(usuarioDao.findByEmail("juan@mail.com")).thenReturn(Optional.of(usuario));
        when(usuarioDao.findUsuarioByEmail("juan@mail.com")).thenReturn(usuario);
        when(passwordEncoder.matches("wrongpass", "hashCorrecto")).thenReturn(false);

        // ACT + ASSERT
        assertThrows(ResponseStatusException.class,
            () -> usuarioService.login(dto));
    }

    @Test
    void testLogin_usuarioInexistente_lanzaExcepcion() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("juan@mail.com");
        dto.setContrasena("juan");

        Usuario usuario = new Usuario();
        usuario.setEmail("juanm@mail.com");
        usuario.setContrasena("juan");

        when(usuarioDao.findByEmail("juanm@mail.com")).thenReturn(Optional.of(usuario));

        // ACT + ASSERT
        assertThrows(ResponseStatusException.class,
            () -> usuarioService.login(dto));
    }

    @Test
    void testCrearUsuario_lanzaExcepcionSiContraseñaNula() {
        // ARRANGE
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail("Julian@mail.com");
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