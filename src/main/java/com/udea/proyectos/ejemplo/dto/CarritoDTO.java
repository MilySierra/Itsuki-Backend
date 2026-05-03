package com.udea.proyectos.ejemplo.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter; 

@Getter
@Setter
public class CarritoDTO {
    private long id;
    private int cantidad;
    private long id_usuario;
    private long id_producto;
    private String nombre_producto;
    private BigDecimal precio_producto;
    private String imagen;
    private String descripcion;
    private BigDecimal subtotal;

    public CarritoDTO() {
    }


    public CarritoDTO(long id, int cantidad, long id_usuario, long id_producto, String nombre_producto,
            BigDecimal precio_producto, String imagen, String descripcion, BigDecimal subtotal) {
        this.id = id;
        this.cantidad = cantidad;
        this.id_usuario = id_usuario;
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.precio_producto = precio_producto;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.subtotal = subtotal;
    }

}
