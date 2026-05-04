package com.udea.proyectos.ejemplo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

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

    public Usuario(@Size(max = 30) String email, @Size(max = 255) String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }

}
