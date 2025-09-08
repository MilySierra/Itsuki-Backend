package com.udea.proyectos.ejemplo.services.Impl;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.udea.proyectos.ejemplo.dto.UsuarioDTO;
import com.udea.proyectos.ejemplo.entities.Usuario;
import com.udea.proyectos.ejemplo.repositories.UsuarioRepository;
import com.udea.proyectos.ejemplo.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static Usuario convertToRepository(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContrasena(usuarioDTO.getContrasena());
        return usuario;
    }

    public static UsuarioDTO convertToDTO(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setContrasena("No puedes saber lol");
        return usuarioDTO;
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDao.findByEmail(usuarioDTO.getEmail()).isPresent()){
            throw new UnsupportedOperationException("El correo ya existe.");
        } else {
            Usuario usuario = convertToRepository(usuarioDTO);
            if (usuario.getEmail() != null && usuario.getContrasena() != null){
                usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
                Usuario guardado = usuarioDao.save(usuario);
                return convertToDTO(guardado);
            } else {
                throw new UnsupportedOperationException("Llene todos los campos.");
            }
        }
    }

    @Override
    public UsuarioDTO login(UsuarioDTO usuarioDTO) {
        if (usuarioDao.findByEmail(usuarioDTO.getEmail()).isPresent()){
            Usuario usuario = usuarioDao.findUsuarioByEmail(usuarioDTO.getEmail());
            if (passwordEncoder.matches(usuarioDTO.getContrasena(), usuario.getContrasena())){
                return convertToDTO(usuario);
            } else {
                throw new UnsupportedOperationException("La contraseña es incorrecta.");
            }
        } else {
            throw new NoSuchElementException("El usuario no existe.");
        }
    }
}
