package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Jornada;
import com.montesibericos.backend.tablas.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JornadaRepositorio extends JpaRepository<Jornada, Long> {

    // Todas las jornadas de un usuario
    List<Jornada> findByUsuario(Usuario usuario);

    // Jornadas pendientes de sincronizar (modo offline)
    List<Jornada> findByUsuarioAndSincronizada(Usuario usuario, Integer sincronizada);
}