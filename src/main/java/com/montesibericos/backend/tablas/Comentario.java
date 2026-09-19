package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data//Usamos esto para que auto-implemente los getters y setters.
@Entity
@Table(name = "COMENTARIO")

public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "IDCOMENTARIO")
    private Long idComentario;

    // Relación con PUBLICACION: muchos comentarios pertenecen a una publicación
    @ManyToOne
    @JoinColumn(name = "IDPUBLICACION", nullable = false)
    private Publicacion publicacion;

    // Relación con USUARIO: muchos comentarios pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "CONTENIDO", nullable = false, length = 500)
    private String contenido;

    @Column(name = "FECHA_COMENTARIO", nullable = false)
    private LocalDateTime fechaComentario;
}
