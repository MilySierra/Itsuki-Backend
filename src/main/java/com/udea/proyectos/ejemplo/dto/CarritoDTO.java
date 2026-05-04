package com.udea.proyectos.ejemplo.dto;

import java.math.BigDecimal;

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
}
