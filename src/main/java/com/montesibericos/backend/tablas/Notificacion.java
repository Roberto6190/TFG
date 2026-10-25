package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "NOTIFICACION")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDNOTIFICACION")
    private Long idNotificacion;

    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "ENVIADA_EMAIL", nullable = false)
    private Integer enviadaEmail = 0;

    @Column(name = "ENVIADA_PUSH", nullable = false)
    private Integer enviadaPush = 0;

    @Column(name = "FECHA_ENVIO", nullable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "LEIDA", nullable = false)
    private Integer leida = 0;

    @Column(name = "MENSAJE", length = 500, nullable = false)
    private String mensaje;

    @Column(name = "TITULO", length = 200, nullable = false)
    private String titulo;

    @Column(name = "TIPO", length = 30, nullable = false)
    private String tipo;
}