package com.udea.proyectos.ejemplo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.proyectos.ejemplo.entities.Carrito;
import com.udea.proyectos.ejemplo.entities.Producto;
import com.udea.proyectos.ejemplo.entities.Usuario;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByUsuario(Usuario usuario);
    Optional<Carrito> findByUsuarioAndProducto(Usuario usuario, Producto producto);
}
