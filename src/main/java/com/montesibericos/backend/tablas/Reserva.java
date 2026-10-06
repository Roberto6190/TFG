package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

// @Data genera automáticamente getters, setters, equals, hashCode y toString
@Data
@Entity
@Table(name = "RESERVA")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRESERVA")
    private Long idReserva;

    // Relación con USUARIO: muchas reservas pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;

    // Relación con RECECHO: muchas reservas pertenecen a un rececho
    @ManyToOne
    @JoinColumn(name = "IDRECECHO", nullable = false)
    private Rececho rececho;

    // DATE en Oracle = LocalDate en Java
    @Column(name = "FECHA_RESERVA", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "ESTADO", length = 30, nullable = false)
    private String estado = "Pendiente";

    @Column(name = "NOTAS", length = 500)
    private String notas;
}