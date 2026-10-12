package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Publicacion;
import com.montesibericos.backend.tablas.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, Long> {

    // Todas las publicaciones de un usuario
    List<Publicacion> findByUsuario(Usuario usuario);

    // Feed ordenado por fecha descendente (más recientes primero)
    List<Publicacion> findAllByOrderByFechaPublicacionDesc();
}