package com.udea.proyectos.ejemplo.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter; 

@Getter
@Setter
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

}
