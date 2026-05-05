package com.udea.proyectos.ejemplo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter; 

@Getter
@Setter
@Entity
@Table(name="carrito")
public class Carrito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="cantidad")
    private int cantidad;

    @ManyToOne
    @JoinColumn(name="id_usuario", nullable=false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name="id_producto", nullable=false)
    private Producto producto;

    public Carrito() {
    }

    public Carrito(long id, int cantidad, Usuario usuario, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.usuario = usuario;
        this.producto = producto;
    }
    
}