package com.udea.proyectos.ejemplo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.udea.proyectos.ejemplo.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
    List<Producto> findByTipo(String tipo);
    
    @Override
    List<Producto> findAll();
}
