package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// JpaRepository nos da gratis: findAll, findById, save, delete y muchos más
// Sin escribir ningún SQL
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email (para el login)
    Optional<Usuario> findByEmail(String email);

    // Comprobar si ya existe un email registrado
    boolean existsByEmail(String email);

    // Buscar usuario por DNI o NIE
    Optional<Usuario> findByDni(String dni);
}