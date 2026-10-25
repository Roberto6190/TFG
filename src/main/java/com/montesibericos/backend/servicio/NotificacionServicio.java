package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Notificacion;
import com.montesibericos.backend.repositorio.NotificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServicio {

    @Autowired
    private NotificacionRepositorio notificacionRepositorio;

    public Notificacion crear(Notificacion notificacion) {
        return notificacionRepositorio.save(notificacion);
    }

    public List<Notificacion> obtenerPorUsuario(Long idUsuario) {
        return notificacionRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public List<Notificacion> obtenerNoLeidasPorUsuario(Long idUsuario) {
        return notificacionRepositorio.findByUsuario_IdUsuarioAndLeida(idUsuario, 0);
    }

    public Notificacion marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada con id: " + id));
        notificacion.setLeida(1);
        return notificacionRepositorio.save(notificacion);
    }

    public void eliminar(Long id) {
        notificacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada con id: " + id));
        notificacionRepositorio.deleteById(id);
    }
}