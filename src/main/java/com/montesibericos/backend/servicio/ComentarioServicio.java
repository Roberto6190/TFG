package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Comentario;
import com.montesibericos.backend.repositorio.ComentarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepositorio;

    public Comentario crear(Comentario comentario) {
        return comentarioRepositorio.save(comentario);
    }

    public List<Comentario> obtenerPorPublicacion(Long idPublicacion) {
        return comentarioRepositorio.findByPublicacion_IdPublicacion(idPublicacion);
    }

    public List<Comentario> obtenerPorUsuario(Long idUsuario) {
        return comentarioRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public Comentario obtenerPorId(Long id) {
        return comentarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con id: " + id));
    }

    public Comentario actualizar(Long id, Comentario datosNuevos) {
        Comentario comentarioExistente = obtenerPorId(id);
        comentarioExistente.setContenido(datosNuevos.getContenido());
        return comentarioRepositorio.save(comentarioExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        comentarioRepositorio.deleteById(id);
    }
}