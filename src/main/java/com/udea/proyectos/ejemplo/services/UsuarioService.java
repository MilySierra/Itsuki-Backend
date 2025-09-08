package com.udea.proyectos.ejemplo.services;

import com.udea.proyectos.ejemplo.dto.UsuarioDTO;

public interface UsuarioService {
    
    UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO login(UsuarioDTO usuarioDTO);
}
