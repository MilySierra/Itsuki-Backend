package com.udea.proyectos.ejemplo.dto;

import java.math.BigDecimal;

public class CarritoDTO {
    private long id;
    private int cantidad;
    private long id_usuario;
    private long id_producto;
    private String nombre_producto;
    private BigDecimal precio_producto;
    private BigDecimal subtotal;

    public CarritoDTO() {
    }

    public CarritoDTO(long id, int cantidad, long id_usuario, long id_producto, String nombre_producto,
            BigDecimal precio_producto, BigDecimal subtotal) {
        this.id = id;
        this.cantidad = cantidad;
        this.id_usuario = id_usuario;
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.precio_producto = precio_producto;
        this.subtotal = subtotal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public long getId_producto() {
        return id_producto;
    }

    public void setId_producto(long id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public BigDecimal getPrecio_producto() {
        return precio_producto;
    }

    public void setPrecio_producto(BigDecimal precio_producto) {
        this.precio_producto = precio_producto;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
  

}
