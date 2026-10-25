package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.PiezaCobrada;
import com.montesibericos.backend.repositorio.PiezaCobradaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiezaCobradaServicio {

    @Autowired
    private PiezaCobradaRepositorio piezaCobradaRepositorio;

    public PiezaCobrada crear(PiezaCobrada pieza) {
        return piezaCobradaRepositorio.save(pieza);
    }

    public List<PiezaCobrada> obtenerPorJornada(Long idJornada) {
        return piezaCobradaRepositorio.findByJornada_IdJornada(idJornada);
    }

    public PiezaCobrada obtenerPorId(Long id) {
        return piezaCobradaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Pieza no encontrada con id: " + id));
    }

    public PiezaCobrada actualizar(Long id, PiezaCobrada datosNuevos) {
        PiezaCobrada piezaExistente = obtenerPorId(id);
        piezaExistente.setEspecie(datosNuevos.getEspecie());
        piezaExistente.setSexo(datosNuevos.getSexo());
        piezaExistente.setTipoTrofeo(datosNuevos.getTipoTrofeo());
        piezaExistente.setFotoUrl(datosNuevos.getFotoUrl());
        piezaExistente.setLatitud(datosNuevos.getLatitud());
        piezaExistente.setLongitud(datosNuevos.getLongitud());
        piezaExistente.setNotas(datosNuevos.getNotas());
        return piezaCobradaRepositorio.save(piezaExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        piezaCobradaRepositorio.deleteById(id);
    }
}