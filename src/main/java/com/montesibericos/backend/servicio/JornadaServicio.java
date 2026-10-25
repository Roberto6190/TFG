package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Jornada;
import com.montesibericos.backend.repositorio.JornadaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JornadaServicio {

    @Autowired
    private JornadaRepositorio jornadaRepositorio;

    public Jornada crear(Jornada jornada) {
        return jornadaRepositorio.save(jornada);
    }

    public List<Jornada> obtenerPorUsuario(Long idUsuario) {
        return jornadaRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public Jornada obtenerPorId(Long id) {
        return jornadaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Jornada no encontrada con id: " + id));
    }

    public Jornada actualizar(Long id, Jornada datosNuevos) {
        Jornada jornadaExistente = obtenerPorId(id);
        jornadaExistente.setFechaJornada(datosNuevos.getFechaJornada());
        jornadaExistente.setHoraInicio(datosNuevos.getHoraInicio());
        jornadaExistente.setHoraFin(datosNuevos.getHoraFin());
        jornadaExistente.setNotas(datosNuevos.getNotas());
        jornadaExistente.setRececho(datosNuevos.getRececho());
        return jornadaRepositorio.save(jornadaExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        jornadaRepositorio.deleteById(id);
    }
}