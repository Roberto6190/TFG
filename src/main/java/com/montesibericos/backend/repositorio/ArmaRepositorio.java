package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Arma;
import com.montesibericos.backend.tablas.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmaRepositorio extends JpaRepository<Arma, Long> {

    // Todas las armas de un usuario
    List<Arma> findByUsuario_IdUsuario(Long usuario);

    // Buscar arma por número de serie
    boolean existsByNumeroSerie(String numeroSerie);
}
