-- ============================================================
-- Montes Ibéricos — TFG DAM Dual
-- Archivo: 01_crear_tablas.sql
-- Autor: Roberto
-- Descripción: Creación de todas las tablas de la base de
--              datos del proyecto. Las tablas se crean en
--              orden porque unas dependen de otras. Por
--              ejemplo, PERMISOS necesita que USUARIO exista
--              antes de crearse porque tiene una clave
--              foránea que apunta a ella.
-- IMPORTANTE: Ejecutar conectado como ADMIN_MONTES_IBERICOS
-- ============================================================


-- ============================================================
-- TABLA USUARIO
-- Es la tabla principal de la que dependen casi todas las
-- demás. Guarda los datos de cada cazador registrado.
-- La contraseña nunca se guarda en texto plano, Spring Boot
-- la convierte en un hash BCrypt antes de guardarla.
-- ============================================================
CREATE TABLE USUARIO (
    -- NUMBER GENERATED ALWAYS AS IDENTITY es como el
    -- AUTO_INCREMENT de MySQL. Oracle genera el número
    -- automáticamente, no hay que introducirlo a mano.
    idUsuario        NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    DNI              VARCHAR2(9),
    -- UNIQUE significa que no puede haber dos usuarios
    -- con el mismo email en toda la tabla.
    Email            VARCHAR2(200)   NOT NULL UNIQUE,
    Nombre           VARCHAR2(20)    NOT NULL,
    -- La contraseña se guarda como hash BCrypt.
    -- Un hash BCrypt tiene siempre 60 caracteres,
    -- pero se pone 500 por seguridad por si cambia.
    Password_Hash    VARCHAR2(500)   NOT NULL,
    Primer_Apellido  VARCHAR2(50)    NOT NULL,
    Rol              VARCHAR2(20)    NOT NULL,
    Segundo_Apellido VARCHAR2(50),
    Telefono         VARCHAR2(9)     NOT NULL,

    -- CONSTRAINT es una regla que Oracle comprueba antes
    -- de guardar cualquier dato. Si el dato no cumple
    -- la regla, Oracle lo rechaza con un error.

    -- Acepta tanto DNI como NIE:
    -- DNI: 8 números seguidos de una de las 23 letras
    --      válidas del DNI español (se excluyen I, O, U, Ñ)
    -- NIE: empieza por X, Y o Z, luego 7 números y
    --      una de las 23 letras válidas
    CONSTRAINT validacion_dni CHECK (
        REGEXP_LIKE(DNI, '^[0-9]{8}[ABCDEFGHJKLMNPQRSTVWXYZ]$')
        OR
        REGEXP_LIKE(DNI, '^[XYZ][0-9]{7}[ABCDEFGHJKLMNPQRSTVWXYZ]$')
    ),

    -- Comprueba que el email tenga formato nombre@dominio.ext
    -- El símbolo + significa "uno o más caracteres del tipo anterior"
    -- El \. es un punto literal (sin barra sería "cualquier carácter")
    -- El {2,} significa "mínimo 2 letras"
    CONSTRAINT validacion_email CHECK (REGEXP_LIKE(Email, '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')),

    -- Los teléfonos españoles empiezan por 6, 7, 8 o 9
    -- y tienen 9 dígitos en total.
    CONSTRAINT validacion_tel CHECK (REGEXP_LIKE(Telefono, '^[6-9][0-9]{8}$')),

    -- En Oracle no existe ENUM como en MySQL.
    -- Se simula con VARCHAR2 + CHECK IN ('valor1','valor2')
    -- Solo puede ser CAZADOR o ADMIN, cualquier otra cosa
    -- Oracle la rechaza.
    CONSTRAINT validacion_rol CHECK (Rol IN ('CAZADOR','ADMIN'))
);


-- ============================================================
-- TABLA PERMISOS
-- Guarda las licencias de caza, permisos de armas y seguros
-- de cada cazador con su fecha de caducidad.
-- Spring Boot revisa esta tabla cada día y manda
-- notificaciones cuando se acerca la fecha límite.
-- ============================================================
CREATE TABLE PERMISOS (
    idPermiso NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idUsuario NUMBER NOT NULL,
    Nota VARCHAR2(255),
    Fecha_Caducidad DATE NOT NULL,
    Fecha_Expedicion DATE NOT NULL,
    -- Estos campos controlan si ya se mandó la notificación
    -- a esa distancia de días. DEFAULT 0 significa que
    -- cuando se crea el permiso aún no se ha notificado nada.
    -- Cuando Spring Boot manda el aviso, lo cambia a 1
    -- para no volver a mandarlo.
    Notificacion_UnoDia      NUMBER(1) DEFAULT 0 NOT NULL,
    Notificacion_CincoDias   NUMBER(1) DEFAULT 0 NOT NULL,
    Notificacion_DiezDias    NUMBER(1) DEFAULT 0 NOT NULL,
    Notificacion_VeinteDias  NUMBER(1) DEFAULT 0 NOT NULL,
    Notificacion_TreintaDias NUMBER(1) DEFAULT 0 NOT NULL,
    -- Feature flag para la renovación automática de pago.
    -- Por defecto está desactivado (0). Cuando el usuario
    -- contrate el servicio premium se pondrá a 1 y Spring
    -- Boot renovará el permiso automáticamente antes de
    -- que caduque sin que el usuario tenga que hacer nada.
    Renovacion_Auto NUMBER(1) DEFAULT 0 NOT NULL,
    Tipo_Permiso VARCHAR2(30) NOT NULL,

    -- FOREIGN KEY indica que idUsuario de esta tabla
    -- apunta al idUsuario de la tabla USUARIO.
    -- ON DELETE CASCADE significa que si se borra un usuario,
    -- todos sus permisos se borran automáticamente también.
    CONSTRAINT fk_permiso_usuario FOREIGN KEY (idUsuario)
        REFERENCES USUARIO(idUsuario) ON DELETE CASCADE,
    CONSTRAINT validacion_tipo_permiso CHECK (Tipo_Permiso IN ('Licencia_Caza','Seguro_Caza','Permiso_Armas','Otro')),
    CONSTRAINT validacion_renovacion CHECK (Renovacion_Auto IN (0,1)),
    -- Comprueba que la fecha de caducidad sea posterior
    -- a la fecha de expedición. No tiene sentido que
    -- un permiso caduque antes de expedirse.
    CONSTRAINT validacion_fechas CHECK (Fecha_Caducidad > Fecha_Expedicion)
);


-- ============================================================
-- TABLA ARMAS
-- Guarda las armas de cada cazador.
-- Los datos de marca, modelo y calibre se obtienen de la
-- API GunSpec cuando el usuario busca su arma, por lo que
-- vienen validados desde la propia API.
-- El número de serie lo introduce el usuario manualmente.
-- ============================================================
CREATE TABLE ARMAS (
    idArmas NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idUsuario NUMBER NOT NULL,
    Marca    VARCHAR2(100) NOT NULL,
    Modelo   VARCHAR2(100) NOT NULL,
    Calibre  VARCHAR2(30)  NOT NULL,
    -- UNIQUE en Numero_Serie significa que no puede haber
    -- dos armas con el mismo número de serie. Es como
    -- el DNI del arma, es único en el mundo.
    Numero_Serie VARCHAR2(30) NOT NULL UNIQUE,
    Notas    VARCHAR2(255),
    Tipo_Arma VARCHAR2(20) NOT NULL,

    CONSTRAINT fk_arma_usuario FOREIGN KEY (idUsuario)
        REFERENCES USUARIO(idUsuario) ON DELETE CASCADE,
    -- El número de serie solo acepta letras mayúsculas y
    -- números. El símbolo + significa uno o más caracteres.
    CONSTRAINT validacion_serie CHECK (REGEXP_LIKE(Numero_Serie, '^[A-Z0-9]+$')),
    CONSTRAINT validacion_tipo_arma CHECK (Tipo_Arma IN ('Rifle','Escopeta','Carabina','Pistola'))
);


-- ============================================================
-- TABLA RECECHO
-- Guarda los recechos que ofrece Montes Ibéricos.
-- Solo el admin puede crear, editar y borrar recechos.
-- En lugar de coordenadas exactas se guarda provincia y
-- municipio para proteger la ubicación de las fincas,
-- ya que en el mundo de la caza eso es información
-- muy sensible que los propietarios no quieren revelar.
-- ============================================================
CREATE TABLE RECECHO (
    idRececho    NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    Descripcion  VARCHAR2(500),
    Imagen       VARCHAR2(200),
    -- Municipio se escribe tal como viene del dropdown
    -- de la API de municipios de España. No tiene CHECK
    -- porque el dropdown ya garantiza que el valor es correcto.
    Munucipio    VARCHAR2(200),
    Provincia    VARCHAR2(200) NOT NULL,
    Precio       NUMBER        NOT NULL,
    Dificultad   VARCHAR2(50)  NOT NULL,

    CONSTRAINT validacion_precio CHECK (Precio >= 0),
    CONSTRAINT validacion_dificultad CHECK (Dificultad IN ('Fácil','Medio','Difícil'))
);


-- ============================================================
-- TABLA RESERVA
-- Cuando un cazador reserva un rececho se guarda aquí.
-- El estado empieza siempre en Pendiente y el admin
-- lo cambia a Confirmada o Cancelada.
-- ============================================================
CREATE TABLE RESERVA (
    idReserva     NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idUsuario     NUMBER       NOT NULL,
    idRececho     NUMBER       NOT NULL,
    Fecha_Reserva DATE         NOT NULL,
    -- DEFAULT 'Pendiente' significa que si no se especifica
    -- el estado al crear la reserva, Oracle pone
    -- Pendiente automáticamente.
    Estado        VARCHAR2(30) DEFAULT 'Pendiente' NOT NULL,
    Notas         VARCHAR2(500),

    CONSTRAINT fk_reserva_usuario FOREIGN KEY (idUsuario)
        REFERENCES USUARIO(idUsuario) ON DELETE CASCADE,
    CONSTRAINT fk_reserva_rececho FOREIGN KEY (idRececho)
        REFERENCES RECECHO(idRececho) ON DELETE CASCADE,
    CONSTRAINT validacion_estado CHECK (Estado IN ('Pendiente','Confirmada','Cancelada'))
);


-- ============================================================
-- TABLA JORNADA
-- Cada salida de caza que registra un cazador.
-- El rececho es opcional porque el cazador puede ir
-- a cazar a un sitio que no sea uno de nuestros recechos.
-- ============================================================
CREATE TABLE JORNADA (
    idJornada     NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idUsuario     NUMBER   NOT NULL,
    -- Sin NOT NULL porque el rececho es opcional
    idRececho     NUMBER,
    Fecha_Jornada DATE     NOT NULL,
    -- Oracle no tiene tipo TIME, se guarda como texto
    -- en formato HH:MM. Por ejemplo '06:30' o '14:00'.
    Hora_Inicio   VARCHAR2(5),
    Hora_Fin      VARCHAR2(5),
    Notas         VARCHAR2(500),
    -- Campo para el modo offline. Cuando el cazador
    -- está sin internet y registra la jornada, se pone
    -- a 0 (no sincronizada). Cuando recupera cobertura,
    -- Spring Boot la sube al servidor y lo cambia a 1.
    Sincronizada  NUMBER(1) DEFAULT 1 NOT NULL,

    CONSTRAINT fk_jornada_usuario FOREIGN KEY (idUsuario)
        REFERENCES USUARIO(idUsuario) ON DELETE CASCADE,
    -- ON DELETE SET NULL significa que si se borra el rececho,
    -- la jornada no se borra, simplemente queda sin rececho
    -- asociado (el campo queda a NULL).
    CONSTRAINT fk_jornada_rececho FOREIGN KEY (idRececho)
        REFERENCES RECECHO(idRececho) ON DELETE SET NULL,
    -- El patrón de hora comprueba que sea formato HH:MM válido.
    -- [01][0-9] acepta 00-19, 2[0-3] acepta 20-23.
    -- [0-5][0-9] acepta minutos de 00 a 59.
    CONSTRAINT validacion_hora_inicio CHECK (REGEXP_LIKE(Hora_Inicio, '^([01][0-9]|2[0-3]):[0-5][0-9]$')),
    CONSTRAINT validacion_hora_fin    CHECK (REGEXP_LIKE(Hora_Fin,    '^([01][0-9]|2[0-3]):[0-5][0-9]$'))
);


-- ============================================================
-- TABLA PIEZA_COBRADA
-- Cada animal que se cobra en una jornada.
-- Las coordenadas GPS las captura el móvil automáticamente,
-- el usuario no tiene que escribir nada.
-- La foto se sube a Firebase Storage y aquí se guarda
-- solo la URL de esa imagen.
-- ============================================================
CREATE TABLE PIEZA_COBRADA (
    idPieza      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idJornada    NUMBER        NOT NULL,
    Especie      VARCHAR2(100) NOT NULL,
    Sexo         VARCHAR2(10)  NOT NULL,
    Foto_Url     VARCHAR2(500),
    -- Las coordenadas GPS tienen hasta 7 decimales
    -- para una precisión de centímetros.
    Latitud      NUMBER(10,7),
    Longitud     NUMBER(10,7),
    Notas        VARCHAR2(500),
    Tipo_Trofeo  VARCHAR2(20)  NOT NULL,

    CONSTRAINT fk_pieza_jornada FOREIGN KEY (idJornada)
        REFERENCES JORNADA(idJornada) ON DELETE CASCADE,
    CONSTRAINT validacion_sexo CHECK (Sexo IN ('Macho','Hembra')),
    CONSTRAINT validacion_tipo_trofeo CHECK (Tipo_Trofeo IN ('Selectivo','Representativo','Bronce','Plata','Oro'))
);


-- ============================================================
-- TABLA PUBLICACION
-- Las fotos y textos que publican los cazadores en la
-- red social de la app. No está ligada a ninguna jornada
-- concreta, el cazador puede publicar lo que quiera.
-- ============================================================
CREATE TABLE PUBLICACION (
    idPublicacion     NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idUsuario         NUMBER    NOT NULL,
    -- CLOB (Character Large Object) es el tipo de Oracle
    -- para textos muy largos, más de 4000 caracteres.
    -- VARCHAR2 tiene un límite de 4000, CLOB no tiene límite.
    Descripcion       CLOB,
    -- TIMESTAMP guarda fecha Y hora exacta, a diferencia
    -- de DATE que solo guarda el día.
    -- Necesitamos hora exacta para ordenar las publicaciones.
    -- DEFAULT SYSTIMESTAMP pone automáticamente la fecha y
    -- hora actual del servidor cuando se crea la publicación.
    Fecha_Publicacion TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    Imagen_URL        VARCHAR2(500),
    Likes             NUMBER    DEFAULT 0 NOT NULL,

    CONSTRAINT fk_publicacion_usuario FOREIGN KEY (idUsuario)
        REFERENCES USUARIO(idUsuario) ON DELETE CASCADE,
    CONSTRAINT validacion_likes CHECK (Likes >= 0)
);


-- ============================================================
-- TABLA COMENTARIO
-- Los comentarios de la red social.
-- Si se borra una publicación, todos sus comentarios
-- se borran automáticamente (ON DELETE CASCADE).
-- ============================================================
CREATE TABLE COMENTARIO (
    idComentario      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idPublicacion     NUMBER        NOT NULL,
    idUsuario         NUMBER        NOT NULL,
    Contenido         VARCHAR2(500) NOT NULL,
    Fecha_Comentario  TIMESTAMP     DEFAULT SYSTIMESTAMP NOT NULL,

    CONSTRAINT fk_comentario_publicacion FOREIGN KEY (idPublicacion)
        REFERENCES PUBLICACION(idPublicacion) ON DELETE CASCADE,
    CONSTRAINT fk_comentario_usuario FOREIGN KEY (idUsuario)
        REFERENCES USUARIO(idUsuario) ON DELETE CASCADE
);


-- ============================================================
-- TABLA OFERTA
-- Las ofertas que publica Montes Ibéricos en la app.
-- Pueden ser recechos, ropa o equipamiento.
-- Solo el admin puede gestionarlas.
-- No tiene FK a USUARIO porque las ofertas son siempre
-- de la empresa, no de los cazadores.
-- ============================================================
CREATE TABLE OFERTA (
    idOferta    NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    -- NUMBER(8,2) significa número de hasta 8 dígitos
    -- con 2 decimales. Por ejemplo 999999.99
    Precio      NUMBER(8,2)    DEFAULT 0  NOT NULL,
    Descripcion VARCHAR2(1000)            NOT NULL,
    Dificultad  VARCHAR2(30)              NOT NULL,
    Imagen_URL  VARCHAR2(100),
    Nombre      VARCHAR2(200)             NOT NULL,
    Tipo_Oferta VARCHAR2(20)              NOT NULL,

    -- Los nombres de constraints deben ser únicos en todo
    -- el esquema, por eso se añade _oferta al final para
    -- no chocar con los constraints de RECECHO que tienen
    -- nombres similares.
    CONSTRAINT validacion_precio_oferta     CHECK (Precio >= 0),
    CONSTRAINT validacion_dificultad_oferta CHECK (Dificultad IN ('Facil','Medio','Dificil')),
    CONSTRAINT validacion_tipo_oferta       CHECK (Tipo_Oferta IN ('Rececho','Ropa','Equipamiento','Otro'))
);


-- ============================================================
-- TABLA NOTIFICACION
-- Registro de todas las notificaciones enviadas.
-- Spring Boot usa esta tabla para no mandar la misma
-- notificación dos veces al mismo usuario.
-- ============================================================
CREATE TABLE NOTIFICACION (
    idNotificacion NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    idUsuario      NUMBER        NOT NULL,
    -- Estos tres campos son semáforos (0/1):
    -- 0 = todavía no se ha enviado
    -- 1 = ya se envió
    -- Se controlan por separado porque puede que el push
    -- se mande bien pero el email falle, o al revés.
    Enviada_Email  NUMBER(1)     DEFAULT 0 NOT NULL,
    Enviada_Push   NUMBER(1)     DEFAULT 0 NOT NULL,
    Fecha_Envio    TIMESTAMP     DEFAULT SYSTIMESTAMP NOT NULL,
    Leida          NUMBER(1)     DEFAULT 0 NOT NULL,
    Mensaje        VARCHAR2(500) NOT NULL,
    Titulo         VARCHAR2(200) NOT NULL,
    Tipo           VARCHAR2(30)  NOT NULL,

    CONSTRAINT fk_notificacion_usuario FOREIGN KEY (idUsuario)
        REFERENCES USUARIO(idUsuario) ON DELETE CASCADE,
    CONSTRAINT validacio_tipo_notificacion   CHECK (Tipo IN ('Permiso_Caducidad','Reserva','Social','Sistema')),
    -- Se añade _notificacion al final para que no choquen
    -- con otros constraints del mismo nombre en otras tablas.
    CONSTRAINT validacion_leida_notificacion CHECK (Leida         IN (0,1)),
    CONSTRAINT validacion_push_notificacion  CHECK (Enviada_Push  IN (0,1)),
    CONSTRAINT validacion_email_notificacion CHECK (Enviada_Email IN (0,1))
);