package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Reserva;
import com.montesibericos.backend.repositorio.ReservaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    public Reserva crear(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }

    public List<Reserva> obtenerPorUsuario(Long idUsuario) {
        return reservaRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public Reserva obtenerPorId(Long id) {
        return reservaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    public Reserva actualizar(Long id, Reserva datosNuevos) {
        Reserva reservaExistente = obtenerPorId(id);
        reservaExistente.setFechaReserva(datosNuevos.getFechaReserva());
        reservaExistente.setEstado(datosNuevos.getEstado());
        reservaExistente.setNotas(datosNuevos.getNotas());
        reservaExistente.setRececho(datosNuevos.getRececho());
        return reservaRepositorio.save(reservaExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        reservaRepositorio.deleteById(id);
    }
}