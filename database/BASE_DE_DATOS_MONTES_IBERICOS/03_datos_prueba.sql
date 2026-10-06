-- ============================================================
-- Montes Ibéricos — TFG DAM Dual
-- Archivo: 03_datos_prueba.sql
-- Autor: Roberto
-- Descripción: Datos de prueba para poder testear la app
--              durante el desarrollo sin tener que meter
--              datos a mano cada vez. Incluye un admin,
--              varios cazadores, un rececho, reservas,
--              jornadas y piezas cobradas de ejemplo.
--              IMPORTANTE: Borrar estos datos antes de
--              la entrega final del TFG con el archivo
--              04_limpiar_datos_prueba.sql
-- IMPORTANTE: Ejecutar conectado como ADMIN_MONTES_IBERICOS
-- ============================================================


-- ============================================================
-- USUARIOS DE PRUEBA
-- Las contraseñas son hashes BCrypt generados por Spring Boot.
-- La contraseña real de todos es: Test1234!
-- ============================================================

-- Usuario administrador
INSERT INTO USUARIO (DNI, Email, Nombre, Password_Hash, Primer_Apellido, Segundo_Apellido, Rol, Telefono)
VALUES (
    '12345678Z',
    'admin@montesibericos.es',
    'Roberto',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
    'García',
    'López',
    'ADMIN',
    '600000001'
);

-- Cazador de prueba 1
INSERT INTO USUARIO (DNI, Email, Nombre, Password_Hash, Primer_Apellido, Segundo_Apellido, Rol, Telefono)
VALUES (
    '87654321A',
    'cazador1@email.es',
    'Juan',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
    'Martínez',
    'Pérez',
    'CAZADOR',
    '611111111'
);

-- Cazador de prueba 2
INSERT INTO USUARIO (DNI, Email, Nombre, Password_Hash, Primer_Apellido, Segundo_Apellido, Rol, Telefono)
VALUES (
    '11223344B',
    'cazador2@email.es',
    'Carlos',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
    'Sánchez',
    'Ruiz',
    'CAZADOR',
    '622222222'
);


-- ============================================================
-- PERMISOS DE PRUEBA
-- Se meten permisos con fechas variadas para probar
-- las notificaciones de caducidad.
-- ============================================================

-- Permiso que caduca en 5 días (para probar notificación)
INSERT INTO PERMISOS (idUsuario, Nota, Fecha_Caducidad, Fecha_Expedicion, Tipo_Permiso)
VALUES (
    2,
    'Licencia de caza de Castilla y León',
    SYSDATE + 5,
    SYSDATE - 360,
    'Licencia_Caza'
);

-- Permiso que caduca en 30 días
INSERT INTO PERMISOS (idUsuario, Nota, Fecha_Caducidad, Fecha_Expedicion, Tipo_Permiso)
VALUES (
    2,
    'Permiso de armas categoría D',
    SYSDATE + 30,
    SYSDATE - 335,
    'Permiso_Armas'
);

-- Permiso que caduca en 60 días
INSERT INTO PERMISOS (idUsuario, Nota, Fecha_Caducidad, Fecha_Expedicion, Tipo_Permiso)
VALUES (
    3,
    'Seguro de caza federación española',
    SYSDATE + 60,
    SYSDATE - 305,
    'Seguro_Caza'
);


-- ============================================================
-- ARMAS DE PRUEBA
-- ============================================================

INSERT INTO ARMAS (idUsuario, Marca, Modelo, Calibre, Numero_Serie, Tipo_Arma)
VALUES (2, 'Blaser', 'R8', '308 Win', 'BL308001A', 'Rifle');

INSERT INTO ARMAS (idUsuario, Marca, Modelo, Calibre, Numero_Serie, Tipo_Arma)
VALUES (2, 'Browning', 'Bar MK3', '30-06', 'BR3006002B', 'Rifle');

INSERT INTO ARMAS (idUsuario, Marca, Modelo, Calibre, Numero_Serie, Tipo_Arma)
VALUES (3, 'Beretta', 'A400', '12', 'BE12003C', 'Escopeta');


-- ============================================================
-- RECECHOS DE PRUEBA
-- ============================================================

INSERT INTO RECECHO (Descripcion, Imagen, Munucipio, Provincia, Precio, Dificultad)
VALUES (
    'Rececho de ciervo en zona de montaña con robledal. Terreno abrupto con buenas poblaciones de venado.',
    NULL,
    'Candeleda',
    'Ávila',
    350,
    'Difícil'
);

INSERT INTO RECECHO (Descripcion, Imagen, Munucipio, Provincia, Precio, Dificultad)
VALUES (
    'Rececho de jabalí en zona de dehesa. Terreno llano con buena visibilidad.',
    NULL,
    'Plasencia',
    'Cáceres',
    200,
    'Fácil'
);

INSERT INTO RECECHO (Descripcion, Imagen, Munucipio, Provincia, Precio, Dificultad)
VALUES (
    'Rececho de corzo en zona de pinar. Ideal para trofeos selectivos.',
    NULL,
    'Cuéllar',
    'Segovia',
    280,
    'Medio'
);


-- ============================================================
-- RESERVAS DE PRUEBA
-- ============================================================

INSERT INTO RESERVA (idUsuario, idRececho, Fecha_Reserva, Estado, Notas)
VALUES (2, 1, SYSDATE + 15, 'Confirmada', 'Prefiero salir al amanecer');

INSERT INTO RESERVA (idUsuario, idRececho, Fecha_Reserva, Estado, Notas)
VALUES (3, 2, SYSDATE + 7, 'Pendiente', NULL);

INSERT INTO RESERVA (idUsuario, idRececho, Fecha_Reserva, Estado, Notas)
VALUES (2, 3, SYSDATE + 30, 'Pendiente', 'Busco trofeo selectivo');


-- ============================================================
-- JORNADAS DE PRUEBA
-- ============================================================

INSERT INTO JORNADA (idUsuario, idRececho, Fecha_Jornada, Hora_Inicio, Hora_Fin, Notas, Sincronizada)
VALUES (2, 1, SYSDATE - 10, '06:00', '14:00', 'Día con mucho viento, buena actividad al amanecer', 1);

INSERT INTO JORNADA (idUsuario, idRececho, Fecha_Jornada, Hora_Inicio, Hora_Fin, Notas, Sincronizada)
VALUES (2, 2, SYSDATE - 5, '07:00', '12:00', 'Jornada corta, lluvia por la mañana', 1);

INSERT INTO JORNADA (idUsuario, idRececho, Fecha_Jornada, Hora_Inicio, Hora_Fin, Notas, Sincronizada)
VALUES (3, 1, SYSDATE - 3, '05:30', '15:00', NULL, 1);


-- ============================================================
-- PIEZAS COBRADAS DE PRUEBA
-- ============================================================

INSERT INTO PIEZA_COBRADA (idJornada, Especie, Sexo, Latitud, Longitud, Notas, Tipo_Trofeo)
VALUES (1, 'Ciervo', 'Macho', 40.1500000, -5.2000000, 'Ciervo de 12 puntas, buen trofeo', 'Oro');

INSERT INTO PIEZA_COBRADA (idJornada, Especie, Sexo, Latitud, Longitud, Notas, Tipo_Trofeo)
VALUES (2, 'Jabalí', 'Macho', 40.0100000, -6.0500000, 'Jabalí de unos 80kg', 'Representativo');

INSERT INTO PIEZA_COBRADA (idJornada, Especie, Sexo, Latitud, Longitud, Notas, Tipo_Trofeo)
VALUES (3, 'Ciervo', 'Hembra', 40.1600000, -5.2100000, 'Selectivo, hembra vieja sin cría', 'Selectivo');


-- ============================================================
-- PUBLICACIONES DE PRUEBA
-- ============================================================

INSERT INTO PUBLICACION (idUsuario, Descripcion, Imagen_URL)
VALUES (2, 'Gran jornada en Gredos! Ciervo de 12 puntas después de 3 horas de espera al amanecer.', NULL);

INSERT INTO PUBLICACION (idUsuario, Descripcion, Imagen_URL)
VALUES (3, 'Primera jornada de la temporada. Buen día aunque sin cobrar nada.', NULL);


-- ============================================================
-- COMENTARIOS DE PRUEBA
-- ============================================================

INSERT INTO COMENTARIO (idPublicacion, idUsuario, Contenido)
VALUES (1, 3, 'Menudo ejemplar! Enhorabuena!');

INSERT INTO COMENTARIO (idPublicacion, idUsuario, Contenido)
VALUES (1, 1, 'Que pasada, bien hecho!');


-- ============================================================
-- OFERTAS DE PRUEBA
-- ============================================================

INSERT INTO OFERTA (Precio, Descripcion, Dificultad, Nombre, Tipo_Oferta)
VALUES (350, 'Rececho de ciervo en Sierra de Gredos. Incluye guía y transporte desde el pueblo.', 'Dificil', 'Rececho Ciervo Gredos', 'Rececho');

INSERT INTO OFERTA (Precio, Descripcion, Dificultad, Nombre, Tipo_Oferta)
VALUES (89, 'Chaqueta de caza impermeable y transpirable. Talla S a XXL.', 'Facil', 'Chaqueta Caza Pro', 'Ropa');

INSERT INTO OFERTA (Precio, Descripcion, Dificultad, Nombre, Tipo_Oferta)
VALUES (45, 'Mochila de caza 30L con sistema de hidratación incluido.', 'Facil', 'Mochila Hunter 30L', 'Equipamiento');


-- Confirmar todos los cambios
COMMIT;