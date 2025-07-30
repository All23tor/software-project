package org.unsa.model.service.interfaces;

import org.unsa.model.domain.usuarios.Usuario; // O tu entidad base de usuario
import org.unsa.model.dtos.CrearUsuarioRequest; // Suponiendo estos DTOs
import org.unsa.model.dtos.ActualizarUsuarioRequest;

import java.util.List;

public interface IUsuarioServicio {
    Usuario crearUsuario(CrearUsuarioRequest request);
    Usuario obtenerUsuarioPorId(Integer id);
    List<Usuario> obtenerTodosLosUsuarios();
    Usuario actualizarUsuario(Integer id, ActualizarUsuarioRequest request);
    void eliminarUsuario(Integer id);
}