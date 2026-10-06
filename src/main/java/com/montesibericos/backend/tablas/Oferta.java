package com.montesibericos.backend.tablas;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data//Usamos esto para que auto-implemente los getters y setters.
@Entity
@Table(name = "OFERTA")

public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "IDOFERTA")
    private Long idOferta;

    @Column(name = "PRECIO", nullable = false)
    private Double precio = 0.0;

    @Column(name = "DESCRIPCION", length = 1000, nullable = false)
    private String descripcion;

    @Column(name = "DIFICULTAD", length = 30, nullable = false)
    private String dificultad;

    @Column(name = "IMAGEN_URL", length = 100)
    private String imagenUrl;

    @Column(name = "NOMBRE", length = 200, nullable = false)
    private String nombre;

    @Column(name = "TIPO_OFERTA", length = 20, nullable = false)
    private String tipoOferta;
}
