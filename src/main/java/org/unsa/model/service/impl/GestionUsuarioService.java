// file: src/main/java/org/unsa/model/service/impl/GestionUsuarioService.java
package org.unsa.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Para manejar transacciones de base de datos
import org.springframework.dao.DataIntegrityViolationException;

import org.unsa.model.domain.usuarios.Usuario;
import org.unsa.model.domain.usuarios.Cliente;
import org.unsa.model.dtos.CrearUsuarioRequest;
import org.unsa.model.dtos.ActualizarUsuarioRequest;
import org.unsa.model.service.interfaces.IUsuarioServicio;
import org.unsa.model.repository.UsuarioRepository;
import org.unsa.model.repository.ClienteRepository;

import java.util.List;
import java.util.Optional; //manejar si no existen

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementacion del servicio para la gestion de usuarios.
 * Contiene la logica de negocio para operaciones CRUD de usuarios.
 */
@Service // Indica a Spring que esta clase es un bean de servicio
@org.springframework.context.annotation.Primary
// Si hay multiples implementaciones de IUsuarioServicio y esta es la predeterminada
public class GestionUsuarioService implements IUsuarioServicio {

    private static final Logger logger = LoggerFactory.getLogger(GestionUsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository; // Asumo que lo necesitas para crear clientes específicos

    @Autowired
    public GestionUsuarioService(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        logger.info("GestionUsuarioService inicializado con repositorios.");
    }

    @Override
    @Transactional // Este método modifica la base de datos, necesita una transacción
    public Usuario crearUsuario(CrearUsuarioRequest request) {
        logger.info("Intentando crear un nuevo usuario con email: {}", request.getEmail());

        // Validar si el email ya existe para evitar duplicados
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Fallo al crear usuario: el email '{}' ya está registrado.", request.getEmail());
            throw new IllegalArgumentException("El email '" + request.getEmail() + "' ya está registrado.");
        }

        // Crear una instancia de la entidad Usuario (o Cliente, si es el caso)
        // Aquí puedes tener lógica para diferenciar tipos de usuario
        Usuario nuevoUsuario;
        // Si el request incluye un campo para tipo de usuario, podrías usarlo
        // Por ahora, asumamos que siempre es un Cliente si hereda de Usuario
        Cliente nuevoCliente = new Cliente();
        // Puedes setear el ID si es relevante para el constructor (aunque usualmente es autogenerado)
        // nuevoCliente.setId(null); // Deja que JPA lo genere
        nuevoCliente.setNombre(request.getNombre());
        nuevoCliente.setEmail(request.getEmail());
        nuevoCliente.setTelefono(request.getTelefono());
        nuevoCliente.setDireccionPrincipal(request.getDireccionPrincipal()); // Asumo que Direccion es @Embedded en Usuario/Cliente

        // Establecer valores por defecto para Usuario (si no se hacen en el constructor)

        try {
            // Guardar el nuevo usuario/cliente en la base de datos
            // Dependiendo de tu estrategia de herencia, podrías guardar en usuarioRepository
            // o en clienteRepository si Cliente tiene un repositorio específico.
            nuevoUsuario = clienteRepository.save(nuevoCliente); // Guarda como Cliente

            logger.info("Usuario con ID {} y email {} creado exitosamente.", nuevoUsuario.getId(), nuevoUsuario.getEmail());
            return nuevoUsuario;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error al guardar usuario debido a un problema de integridad de datos.", e);
        }
    }

    @Override
    @Transactional(readOnly = true) // Este método solo lee la base de datos
    public Usuario obtenerUsuarioPorId(Integer id) {
        logger.info("Buscando usuario con ID: {}", id);
        // Usa findById que devuelve un Optional
        return usuarioRepository.findById(id).orElse(null); // Retorna null si no lo encuentra
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodosLosUsuarios() {
        logger.info("Obteniendo todos los usuarios.");
        return usuarioRepository.findAll(); // Spring Data JPA proporciona findAll()
    }

    @Override
    @Transactional
    public Usuario actualizarUsuario(Integer id, ActualizarUsuarioRequest request) {
        logger.info("Intentando actualizar usuario con ID: {}", id);

        // Buscar el usuario existente
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        // Aplicar los cambios del DTO a la entidad existente
        // Solo actualiza los campos que no son nulos en el request
        if (request.getNombre() != null) {
            usuarioExistente.setNombre(request.getNombre());
        }
        if (request.getEmail() != null) {
            // Validar si el nuevo email ya existe y no pertenece a este usuario
            Optional<Usuario> usuarioConEmail = usuarioRepository.findByEmail(request.getEmail());
            if (usuarioConEmail.isPresent() && !usuarioConEmail.get().getId().equals(id)) {
                logger.warn("Fallo al actualizar usuario: el email '{}' ya está registrado por otro usuario.", request.getEmail());
                throw new IllegalArgumentException("El email '" + request.getEmail() + "' ya está registrado por otro usuario.");
            }
            usuarioExistente.setEmail(request.getEmail());
        }
        if (request.getTelefono() != null) {
            usuarioExistente.setTelefono(request.getTelefono());
        }
        if (request.getDireccionPrincipal() != null) { // Si la dirección es @Embedded y se puede actualizar
            usuarioExistente.setDireccionPrincipal(request.getDireccionPrincipal());
        }
        if (request.getActivo() != null) {
            usuarioExistente.setActivo(request.getActivo());
        }

        try {
            Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente); // Guardar los cambios
            logger.info("Usuario con ID {} actualizado exitosamente.", usuarioActualizado.getId());
            return usuarioActualizado;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Error al actualizar usuario debido a un problema de integridad de datos.", e);
        }
    }

    @Override
    @Transactional
    public void eliminarUsuario(Integer id) {
        logger.info("Intentando eliminar usuario con ID: {}", id);

        if (!usuarioRepository.existsById(id)) {
            logger.warn("Fallo al eliminar usuario: usuario con ID {} no encontrado.", id);
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }

        usuarioRepository.deleteById(id);
        logger.info("Usuario con ID {} eliminado exitosamente.", id);
    }

    // Puedes añadir un método para buscar por email si lo necesitas a menudo
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}