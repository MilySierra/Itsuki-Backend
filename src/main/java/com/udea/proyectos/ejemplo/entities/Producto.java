package com.udea.proyectos.ejemplo.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

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

    public Producto() {
    }

    public Producto(long id, @Size(max = 30) String nombre, @Size(max = 30) String tipo,
            @Size(max = 300) String descripcion, BigDecimal precio, @Size(max = 50) String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
}
