package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Comentario;
import com.montesibericos.backend.tablas.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

    // Todos los comentarios de una publicación
    List<Comentario> findByPublicacion_IdPublicacion(Long publicacion);

    List<Comentario> findByUsuario_IdUsuario(Long idUsuario);
}