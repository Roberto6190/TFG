package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Publicacion;
import com.montesibericos.backend.repositorio.PublicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicacionServicio {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    public Publicacion crear(Publicacion publicacion) {
        return publicacionRepositorio.save(publicacion);
    }

    public List<Publicacion> obtenerTodas() {
        return publicacionRepositorio.findAll();
    }

    public List<Publicacion> obtenerPorUsuario(Long idUsuario) {
        return publicacionRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public Publicacion obtenerPorId(Long id) {
        return publicacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicacion no encontrada con id: " + id));
    }

    public Publicacion actualizar(Long id, Publicacion datosNuevos) {
        Publicacion publicacionExistente = obtenerPorId(id);
        publicacionExistente.setDescripcion(datosNuevos.getDescripcion());
        publicacionExistente.setImagenUrl(datosNuevos.getImagenUrl());
        return publicacionRepositorio.save(publicacionExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        publicacionRepositorio.deleteById(id);
    }
}