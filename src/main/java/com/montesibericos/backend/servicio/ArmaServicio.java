package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Arma;
import com.montesibericos.backend.repositorio.ArmaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmaServicio {

    @Autowired
    private ArmaRepositorio armaRepositorio;

    public Arma crear(Arma arma) {
        return armaRepositorio.save(arma);
    }

    public List<Arma> obtenerPorUsuario(Long idUsuario) {
        return armaRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public Arma obtenerPorId(Long id) {
        return armaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Arma no encontrada con id: " + id));
    }

    public Arma actualizar(Long id, Arma datosNuevos) {
        Arma armaExistente = obtenerPorId(id);
        armaExistente.setMarca(datosNuevos.getMarca());
        armaExistente.setModelo(datosNuevos.getModelo());
        armaExistente.setCalibre(datosNuevos.getCalibre());
        armaExistente.setNumeroSerie(datosNuevos.getNumeroSerie());
        armaExistente.setTipoArma(datosNuevos.getTipoArma());
        armaExistente.setNotas(datosNuevos.getNotas());
        return armaRepositorio.save(armaExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        armaRepositorio.deleteById(id);
    }
}