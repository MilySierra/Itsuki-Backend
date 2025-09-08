package com.udea.proyectos.ejemplo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.proyectos.ejemplo.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(long id);
    Usuario findUsuarioByEmail(String email);
}
