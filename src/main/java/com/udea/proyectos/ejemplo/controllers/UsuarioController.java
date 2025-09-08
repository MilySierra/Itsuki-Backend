package com.udea.proyectos.ejemplo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.udea.proyectos.ejemplo.dto.UsuarioDTO;
import com.udea.proyectos.ejemplo.services.UsuarioService;

@RestController
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioDTO> postMethodName(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioCreado = usuarioService.crearUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    @PostMapping("/usuario/login")
    public ResponseEntity<UsuarioDTO> getMethodName(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioLogueado =usuarioService.login(usuarioDTO);
        return ResponseEntity.ok(usuarioLogueado);
    }
    
    
}
