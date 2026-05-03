package com.udea.proyectos.ejemplo.dto;

import lombok.Getter;
import lombok.Setter; 

@Getter
@Setter
public class UsuarioDTO {
    private long id;
    private String nombre;
    private String email;
    private String contrasena;
    
    public UsuarioDTO() {
    }

    public UsuarioDTO(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }


    public UsuarioDTO(long id, String nombre, String email, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }
    
}
