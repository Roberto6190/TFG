package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Notificacion;
import com.montesibericos.backend.tablas.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificacionRepositorio extends JpaRepository<Notificacion, Long> {

    // Todas las notificaciones de un usuario
    List<Notificacion> findByUsuario(Usuario usuario);

    // Notificaciones no leídas de un usuario
    List<Notificacion> findByUsuarioAndLeida(Usuario usuario, Integer leida);
}