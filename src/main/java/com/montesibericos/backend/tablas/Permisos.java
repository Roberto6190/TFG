package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "PERMISOS")

public class Permisos {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "IDPERMISO")
        private Long idPermiso;

        // Relación con USUARIO: muchos permisos pertenecen a un usuario
        @ManyToOne
        @JoinColumn(name = "IDUSUARIO", nullable = false)
        private Usuario usuario;

        @Column(name = "NOTA", length = 255)
        private String nota;

        // DATE en Oracle = LocalDate en Java
        @Column(name = "FECHA_CADUCIDAD", nullable = false)
        private LocalDate fechaCaducidad;

        @Column(name = "FECHA_EXPEDICION", nullable = false)
        private LocalDate fechaExpedicion;

        // Campos para controlar si ya se mandó la notificación
        // 0 = no enviada, 1 = ya enviada
        @Column(name = "NOTIFICACION_UNODIA", nullable = false)
        private Integer notificacionUnoDia = 0;

        @Column(name = "NOTIFICACION_CINCODIAS", nullable = false)
        private Integer notificacionCincoDias = 0;

        @Column(name = "NOTIFICACION_DIEZDIAS", nullable = false)
        private Integer notificacionDiezDias = 0;

        @Column(name = "NOTIFICACION_VEINTEDIAS", nullable = false)
        private Integer notificacionVeinteDias = 0;

        @Column(name = "NOTIFICACION_TREINTADIAS", nullable = false)
        private Integer notificacionTreintaDias = 0;

        // Feature flag para renovación automática de pago
        // 0 = desactivado, 1 = activado (servicio premium)
        @Column(name = "RENOVACION_AUTO", nullable = false)
        private Integer renovacionAuto = 0;

        @Column(name = "TIPO_PERMISO", length = 30, nullable = false)
        private String tipoPermiso;
}

