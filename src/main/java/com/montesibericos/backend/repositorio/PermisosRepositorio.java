package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Permisos;
import com.montesibericos.backend.tablas.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PermisosRepositorio extends JpaRepository<Permisos, Long> {

    // Todos los permisos de un usuario
    List<Permisos> findByUsuario(Usuario usuario);

    // Permisos que caducan antes de una fecha
    // Spring Boot lo usa para mandar notificaciones
    List<Permisos> findByFechaCaducidadBefore(LocalDate fecha);

    // Permisos que caducan entre dos fechas
    List<Permisos> findByFechaCaducidadBetween(LocalDate inicio, LocalDate fin);
}