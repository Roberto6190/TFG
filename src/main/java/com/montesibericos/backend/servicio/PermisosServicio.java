package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Permisos;
import com.montesibericos.backend.repositorio.PermisosRepositorio;
import com.montesibericos.backend.tablas.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PermisosServicio {

    @Autowired
    private PermisosRepositorio permisosRepositorio;

    // ─── CRUD ────────────────────────────────────────────────────

    public Permisos crear(Permisos permiso) {
        return permisosRepositorio.save(permiso);
    }

    public List<Permisos> obtenerPorUsuario(Long idUsuario) {
        return permisosRepositorio.findByUsuario_IdUsuario(idUsuario);
    }

    public Permisos obtenerPorId(Long id) {
        return permisosRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado con id: " + id));
    }

    public Permisos actualizar(Long id, Permisos datosNuevos) {
        Permisos permisoExistente = obtenerPorId(id);
        permisoExistente.setNota(datosNuevos.getNota());
        permisoExistente.setFechaCaducidad(datosNuevos.getFechaCaducidad());
        permisoExistente.setFechaExpedicion(datosNuevos.getFechaExpedicion());
        permisoExistente.setTipoPermiso(datosNuevos.getTipoPermiso());
        return permisosRepositorio.save(permisoExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        permisosRepositorio.deleteById(id);
    }

    // ─── SCHEDULER ───────────────────────────────────────────────
    // Se ejecuta todos los días a las 8:00 AM automáticamente

    @Scheduled(cron = "0 0 8 * * *")
    public void comprobarCaducidades() {
        LocalDate hoy = LocalDate.now();
        List<Permisos> todosLosPermisos = permisosRepositorio.findAll();

        for (Permisos permiso : todosLosPermisos) {
            long diasRestantes = ChronoUnit.DAYS.between(hoy, permiso.getFechaCaducidad());

            if (diasRestantes == 30 && permiso.getNotificacionTreintaDias() == 0) {
                permiso.setNotificacionTreintaDias(1);
                permisosRepositorio.save(permiso);
            }
            if (diasRestantes == 20 && permiso.getNotificacionVeinteDias() == 0) {
                permiso.setNotificacionVeinteDias(1);
                permisosRepositorio.save(permiso);
            }
            if (diasRestantes == 10 && permiso.getNotificacionDiezDias() == 0) {
                permiso.setNotificacionDiezDias(1);
                permisosRepositorio.save(permiso);
            }
            if (diasRestantes == 5 && permiso.getNotificacionCincoDias() == 0) {
                permiso.setNotificacionCincoDias(1);
                permisosRepositorio.save(permiso);
            }
            if (diasRestantes == 1 && permiso.getNotificacionUnoDia() == 0) {
                permiso.setNotificacionUnoDia(1);
                permisosRepositorio.save(permiso);
            }
        }
    }
}