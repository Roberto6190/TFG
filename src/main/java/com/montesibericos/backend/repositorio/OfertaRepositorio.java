package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfertaRepositorio extends JpaRepository<Oferta, Long> {

    // Ofertas por tipo (Rececho, Ropa, Equipamiento)
    List<Oferta> findByTipoOferta(String tipoOferta);
}