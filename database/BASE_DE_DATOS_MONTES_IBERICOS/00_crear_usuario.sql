-- ============================================================
-- Montes Ibéricos — TFG DAM Dual
-- Archivo: 00_crear_usuario.sql
-- Autor: Roberto
-- Descripción: En Oracle no se trabaja directamente con el
--              usuario SYSTEM para los proyectos. SYSTEM es
--              el administrador principal del motor de base
--              de datos, es decir, tiene acceso y control
--              sobre todo Oracle. Sería como el portero de
--              un edificio que tiene llave de todas las
--              plantas.
--
--              Por eso lo primero que hay que hacer es crear
--              un usuario propio para el proyecto, en este
--              caso admin_montes_ibericos. Este usuario tendrá
--              su propio espacio de trabajo dentro de Oracle,
--              donde se crearán todas las tablas y datos del
--              proyecto, separado del resto.
--
--              Este script se ejecuta una única vez conectado
--              como SYSTEM, porque solo el administrador puede
--              crear usuarios nuevos. Una vez creado, ya no
--              se vuelve a usar SYSTEM para nada. Todas las
--              tablas se crean desde admin_montes_ibericos,
--              y Spring Boot también se conectará con ese
--              usuario.
--
--              Es el mismo proceso que sigue cualquier empresa:
--              el administrador crea tu usuario y a partir de
--              ahí trabajas con el tuyo propio.
--
-- IMPORTANTE: Ejecutar conectado como SYSTEM, una sola vez.
-- ============================================================

-- Creo el usuario del proyecto con su contraseña.
-- Todo lo que cree en el proyecto (tablas, datos...)
-- quedará guardado dentro de este usuario, en su
-- propio esquema separado del resto de Oracle.
CREATE USER admin_montes_ibericos IDENTIFIED BY MontesIbericos1998;

-- Le doy permiso para conectarse a Oracle
-- y para poder crear tablas e índices.
GRANT CONNECT, RESOURCE TO admin_montes_ibericos;

-- Le doy permisos de administrador para el entorno
-- de desarrollo. En un proyecto real en producción
-- estos permisos se limitarían solo a lo necesario,
-- pero para el TFG es suficiente y más cómodo.
GRANT DBA TO admin_montes_ibericos;

-- Le doy permiso para abrir sesiones, es decir,
-- para poder iniciar sesión en la base de datos.
-- Sin esto no podría ni conectarse.
GRANT CREATE SESSION TO admin_montes_ibericos;

-- Le doy espacio ilimitado para guardar datos.
-- Sin esto Oracle le pondría un límite muy pequeño
-- por defecto y se quedaría sin espacio enseguida.
GRANT UNLIMITED TABLESPACE TO admin_montes_ibericos;