package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;

@Data//Usamos esto para que auto-implemente los getters y setters.
@Entity
@Table(name = "ARMAS")

public class Arma {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    @Column(name = "IDARMAS")
    private Long idArmas;

    // Relación con USUARIO: muchas armas pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "MARCA", nullable = false, length = 100)
    private String marca;

    @Column(name = "MODELO", nullable = false, length = 100)
    private String modelo;

    @Column(name = "CALIBRE", nullable = false, length = 30)
    private String calibre;

    @Column(name = "NUMERO_SERIE", nullable = false, length = 30, unique = true)
    private String numeroSerie;

    @Column(name = "NOTAS", nullable = false, length = 255)
    private String notas;

    @Column(name = "TIPO_ARMA", nullable = false, length = 20)
    private String tipoArma;
}
