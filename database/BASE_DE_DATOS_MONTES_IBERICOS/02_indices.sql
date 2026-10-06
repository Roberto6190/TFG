-- ============================================================
-- Montes Ibéricos — TFG DAM Dual
-- Archivo: 02_indices.sql
-- Autor: Roberto
-- Descripción: Índices para mejorar el rendimiento de las
--              consultas más frecuentes de la app.
--              Un índice es una estructura interna que Oracle
--              mantiene automáticamente. Funciona como el
--              índice de un libro: en lugar de leer toda la
--              tabla fila por fila, Oracle va directo a las
--              filas que necesita.
--              Una vez creados no hay que tocarlos, Oracle
--              los usa solo cuando hace una búsqueda.
--              No se crean índices en todas las columnas
--              porque cada índice ocupa espacio y ralentiza
--              las inserciones. Solo se crean en las columnas
--              que se usan frecuentemente en búsquedas.
-- IMPORTANTE: Ejecutar conectado como ADMIN_MONTES_IBERICOS
-- ============================================================


-- ============================================================
-- ÍNDICES DE PERMISOS
-- Son los más importantes porque Spring Boot revisa
-- esta tabla cada día para mandar notificaciones.
-- ============================================================

-- Cuando un usuario abre "Mis permisos", Spring Boot busca
-- todos los permisos de ese usuario por su idUsuario.
-- Sin este índice Oracle leería toda la tabla entera.
CREATE INDEX idx_permiso_usuario ON PERMISOS(idUsuario);

-- El scheduler de Spring Boot ejecuta cada día una búsqueda
-- por Fecha_Caducidad para saber qué permisos caducan pronto.
-- Este índice hace que esa búsqueda diaria sea instantánea.
CREATE INDEX idx_permiso_caducidad ON PERMISOS(Fecha_Caducidad);


-- ============================================================
-- ÍNDICES DE ARMAS
-- ============================================================

-- Para mostrar las armas de un cazador concreto.
CREATE INDEX idx_arma_usuario ON ARMAS(idUsuario);


-- ============================================================
-- ÍNDICES DE RESERVAS
-- ============================================================

-- Para ver las reservas de un usuario concreto.
CREATE INDEX idx_reserva_usuario ON RESERVA(idUsuario);

-- Para que el admin pueda filtrar reservas por fecha
-- y ver qué recechos están ocupados qué días.
CREATE INDEX idx_reserva_fecha ON RESERVA(Fecha_Reserva);


-- ============================================================
-- ÍNDICES DE JORNADAS
-- ============================================================

-- Para ver el historial de jornadas de un cazador.
CREATE INDEX idx_jornada_usuario ON JORNADA(idUsuario);

-- Para filtrar jornadas por fecha en las estadísticas.
CREATE INDEX idx_jornada_fecha ON JORNADA(Fecha_Jornada);


-- ============================================================
-- ÍNDICES DE PIEZAS COBRADAS
-- ============================================================

-- Para ver todas las piezas cobradas en una jornada concreta.
CREATE INDEX idx_pieza_jornada ON PIEZA_COBRADA(idJornada);


-- ============================================================
-- ÍNDICES DE LA RED SOCIAL
-- ============================================================

-- Para ver las publicaciones de un usuario concreto
-- en su perfil.
CREATE INDEX idx_publicacion_usuario ON PUBLICACION(idUsuario);

-- Para ordenar el feed de publicaciones por fecha,
-- mostrando primero las más recientes.
CREATE INDEX idx_publicacion_fecha ON PUBLICACION(Fecha_Publicacion);

-- Para cargar los comentarios de una publicación concreta.
CREATE INDEX idx_comentario_publicacion ON COMENTARIO(idPublicacion);


-- ============================================================
-- ÍNDICES DE NOTIFICACIONES
-- ============================================================

-- Para cargar todas las notificaciones de un usuario.
CREATE INDEX idx_notificacion_usuario ON NOTIFICACION(idUsuario);

-- Índice compuesto: combina dos columnas a la vez.
-- Sirve para la consulta más frecuente de notificaciones:
-- "dame las notificaciones NO leídas de este usuario".
-- Al tener las dos columnas juntas Oracle resuelve esa
-- consulta con un solo índice en lugar de dos.
CREATE INDEX idx_notificacion_leida ON NOTIFICACION(idUsuario, Leida);