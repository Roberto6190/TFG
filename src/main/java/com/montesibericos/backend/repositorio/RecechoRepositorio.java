package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Rececho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecechoRepositorio extends JpaRepository<Rececho, Long> {

    // Buscar recechos por provincia
    List<Rececho> findByProvincia(String provincia);

    // Buscar recechos por dificultad
    List<Rececho> findByDificultad(String dificultad);
}