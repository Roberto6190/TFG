package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data//Usamos esto para que auto-implemente los getters y setters.
@Entity
@Table(name = "PUBLICACION")

public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "IDPUBLICACION")
    private Long idPublicacion;

    // Relación con USUARIO: muchas publicaciones pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;

    @Lob
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "FECHA_PUBLICACION", nullable = false)
    private LocalDateTime fechaPublicacion;

    @Column(name = "IMAGEN_URL", length = 500)
    private String imagenUrl;

    @Column(name = "LIKES", nullable = false)
    private Integer likes = 0;
}
