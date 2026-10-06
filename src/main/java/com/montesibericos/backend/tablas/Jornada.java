package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

// @Data genera automáticamente getters, setters, equals, hashCode y toString
@Data
@Entity
@Table(name = "JORNADA")
public class Jornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDJORNADA")
    private Long idJornada;

    // Relación con USUARIO: muchas jornadas pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;

    // Relación con RECECHO: opcional, el cazador puede ir fuera de nuestros recechos
    @ManyToOne
    @JoinColumn(name = "IDRECECHO")
    private Rececho rececho;

    // DATE en Oracle = LocalDate en Java
    @Column(name = "FECHA_JORNADA", nullable = false)
    private LocalDate fechaJornada;

    // Hora en formato HH:MM guardada como String
    @Column(name = "HORA_INICIO", length = 5)
    private String horaInicio;

    @Column(name = "HORA_FIN", length = 5)
    private String horaFin;

    @Column(name = "NOTAS", length = 500)
    private String notas;

    // Modo offline: 0 = pendiente de sincronizar, 1 = ya sincronizada
    @Column(name = "SINCRONIZADA", nullable = false)
    private Integer sincronizada = 1;
}