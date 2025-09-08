package com.udea.proyectos.ejemplo.dto;

import java.math.BigDecimal;

public class ProductoDTO {
    private long id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private BigDecimal precio;
    private String imagen;
    
    public ProductoDTO() {
    }

    public ProductoDTO(long id, String nombre, String tipo, String descripcion, BigDecimal precio, String imagen) {
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
