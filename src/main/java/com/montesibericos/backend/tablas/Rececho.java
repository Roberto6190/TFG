package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;

// @Data genera automáticamente getters, setters, equals, hashCode y toString
@Data
@Entity
@Table(name = "RECECHO")
public class Rececho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRECECHO")
    private Long idRececho;

    @Column(name = "DESCRIPCION", length = 500)
    private String descripcion;

    @Column(name = "IMAGEN", length = 200)
    private String imagen;

    @Column(name = "MUNUCIPIO", length = 200)
    private String municipio;

    @Column(name = "PROVINCIA", length = 200, nullable = false)
    private String provincia;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

    @Column(name = "DIFICULTAD", length = 50, nullable = false)
    private String dificultad;
}