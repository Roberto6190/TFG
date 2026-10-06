package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;

// @Data genera automáticamente getters, setters, equals, hashCode y toString
@Data
@Entity
@Table(name = "PIEZA_COBRADA")
public class PiezaCobrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPIEZA")
    private Long idPieza;

    // Relación con JORNADA: muchas piezas pertenecen a una jornada
    @ManyToOne
    @JoinColumn(name = "IDJORNADA", nullable = false)
    private Jornada jornada;

    @Column(name = "ESPECIE", length = 100, nullable = false)
    private String especie;

    @Column(name = "SEXO", length = 10, nullable = false)
    private String sexo;

    @Column(name = "FOTO_URL", length = 500)
    private String fotoUrl;

    // Coordenadas GPS capturadas automáticamente por el móvil
    @Column(name = "LATITUD")
    private Double latitud;

    @Column(name = "LONGITUD")
    private Double longitud;

    @Column(name = "NOTAS", length = 500)
    private String notas;

    @Column(name = "TIPO_TROFEO", length = 20, nullable = false)
    private String tipoTrofeo;
}