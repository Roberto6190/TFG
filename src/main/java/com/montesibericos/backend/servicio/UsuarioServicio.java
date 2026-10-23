package com.montesibericos.backend.servicio;

import com.montesibericos.backend.tablas.Usuario;
import com.montesibericos.backend.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario registrar(Usuario usuario) {
        usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
        return usuarioRepositorio.save(usuario);
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public Usuario obtenerPorEmail(String email) {
        return usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepositorio.findAll();
    }

    public Usuario actualizar(Long id, Usuario datosNuevos) {
        Usuario usuarioExistente = obtenerPorId(id);
        usuarioExistente.setNombre(datosNuevos.getNombre());
        usuarioExistente.setPrimerApellido(datosNuevos.getPrimerApellido());
        usuarioExistente.setSegundoApellido(datosNuevos.getSegundoApellido());
        usuarioExistente.setTelefono(datosNuevos.getTelefono());
        usuarioExistente.setDni(datosNuevos.getDni());
        return usuarioRepositorio.save(usuarioExistente);
    }

    public void eliminar(Long id) {
        obtenerPorId(id);
        usuarioRepositorio.deleteById(id);
    }
}