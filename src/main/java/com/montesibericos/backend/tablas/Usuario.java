package com.montesibericos.backend.tablas;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USUARIO")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "IDUSUARIO")
    private Long idUsuario;

    @Column(name = "DNI", length = 9)
    private String dni;

    @Column(name = "EMAIL", length = 200, nullable = false, unique = true)
    private String email;

    @Column(name = "NOMBRE", length = 20, nullable = false)
    private String nombre;

    @Column(name = "PASSWORD_HASH", length = 500, nullable = false)
    private String passwordHash;

    @Column(name = "PRIMER_APELLIDO", length = 50, nullable = false)
    private String primerApellido;

    @Column(name = "ROL", length = 20, nullable = false)
    private String rol;

    @Column(name = "SEGUNDO_APELLIDO", length = 50, nullable = false)
    private String segundoApellido;

    @Column(name = "TELEFONO", length = 9, nullable = false)
    private String telefono;

}
