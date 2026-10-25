package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Rececho;
import com.montesibericos.backend.repositorio.RecechoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecechoServicio {

    @Autowired
    private RecechoRepositorio recechoRepositorio;

    public Rececho crear(Rececho rececho) {
        return recechoRepositorio.save(rececho);
    }

    public List<Rececho> obtenerTodos() {
        return recechoRepositorio.findAll();
    }

    public Rececho obtenerPorId(Long id) {
        return recechoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Rececho no encontrado con id: " + id));
    }

    public Rececho actualizar(Long id, Rececho datosNuevos) {
        Rececho recechoExistente = obtenerPorId(id);
        recechoExistente.setMunicipio(datosNuevos.getMunicipio());
        recechoExistente.setProvincia(datosNuevos.getProvincia());
        recechoExistente.setDescripcion(datosNuevos.getDescripcion());
        recechoExistente.setPrecio(datosNuevos.getPrecio());
        recechoExistente.setDificultad(datosNuevos.getDificultad());
        recechoExistente.setImagen(datosNuevos.getImagen());
        return recechoRepositorio.save(recechoExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        recechoRepositorio.deleteById(id);
    }
}