package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Jornada;
import com.montesibericos.backend.tablas.PiezaCobrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PiezaCobradaRepositorio extends JpaRepository<PiezaCobrada, Long> {

    // Todas las piezas de una jornada
    List<PiezaCobrada> findByJornada(Jornada jornada);

    // Buscar piezas por especie
    List<PiezaCobrada> findByEspecie(String especie);
}