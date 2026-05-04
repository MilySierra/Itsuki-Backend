package com.udea.proyectos.ejemplo.entities;

import java.math.BigDecimal;

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
@Table(name="producto")
public class Producto {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Size(max=30)
    @Column(name="nombre", length=30)
    private String nombre;

    @Size(max=30)
    @Column(name="tipo", length=30)
    private String tipo;
    
    @Size(max=300)
    @Column(name="descripcion", length=300)
    private String descripcion;


    @Column(name="precio")
    private BigDecimal precio;

    @Size(max=100)
    @Column(name="imagen", length=100)
    private String imagen;
    
}
