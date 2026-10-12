package com.montesibericos.backend.repositorio;

import com.montesibericos.backend.tablas.Reserva;
import com.montesibericos.backend.tablas.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {

    // Todas las reservas de un usuario
    List<Reserva> findByUsuario(Usuario usuario);

    // Reservas por estado (Pendiente, Confirmada, Cancelada)
    List<Reserva> findByEstado(String estado);

    // Reservas de un usuario por estado
    List<Reserva> findByUsuarioAndEstado(Usuario usuario, String estado);
}