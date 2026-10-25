package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Oferta;
import com.montesibericos.backend.repositorio.OfertaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfertaServicio {

    @Autowired
    private OfertaRepositorio ofertaRepositorio;

    public Oferta crear(Oferta oferta) {
        return ofertaRepositorio.save(oferta);
    }

    public List<Oferta> obtenerTodas() {
        return ofertaRepositorio.findAll();
    }

    public Oferta obtenerPorId(Long id) {
        return ofertaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada con id: " + id));
    }

    public Oferta actualizar(Long id, Oferta datosNuevos) {
        Oferta ofertaExistente = obtenerPorId(id);
        ofertaExistente.setNombre(datosNuevos.getNombre());
        ofertaExistente.setDescripcion(datosNuevos.getDescripcion());
        ofertaExistente.setPrecio(datosNuevos.getPrecio());
        ofertaExistente.setDificultad(datosNuevos.getDificultad());
        ofertaExistente.setTipoOferta(datosNuevos.getTipoOferta());
        ofertaExistente.setImagenUrl(datosNuevos.getImagenUrl());
        return ofertaRepositorio.save(ofertaExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        ofertaRepositorio.deleteById(id);
    }
}