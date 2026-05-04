package com.udea.proyectos.ejemplo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 30)
    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Size(max = 255)
    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    public Usuario() {
    }

    public Usuario(@Size(max = 30) String email, @Size(max = 255) String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }


    public Usuario(long id, @Size(max = 100) String nombre, @Size(max = 30) String email,
            @Size(max = 255) String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
}
