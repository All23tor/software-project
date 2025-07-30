// file: src/main/java/org/unsa/model/controller/UsuariosController.java
package org.unsa.model.controller;

import org.unsa.model.domain.usuarios.Usuario;
import org.unsa.model.dtos.CrearUsuarioRequest;
import org.unsa.model.dtos.ActualizarUsuarioRequest;
import org.unsa.model.service.interfaces.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException; // Para manejar errores de base de datos como duplicados

import jakarta.validation.Valid; // Para habilitar las validaciones en los Dtos (ej. @NotBlank, @Email)

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador REST para la gestión de usuarios en el sistema.
 * Expone endpoints (rutas de API) para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los usuarios.
 */
@RestController // Marca esta clase como un controlador REST, lo que implica @ResponseBody en todos los métodos
@RequestMapping("/usuarios") // Define la ruta base para todos los endpoints de este controlador (ej. /api/usuarios)
public class UsuariosController {

    private static final Logger logger = LoggerFactory.getLogger(UsuariosController.class);

    private final IUsuarioServicio usuarioServicio; // Inyecta la interfaz del servicio de usuarios

    /**
     * Constructor del controlador que inyecta la dependencia del servicio de usuarios.
     *
     * @param usuarioServicio La implementación del servicio de usuarios.
     */
    @Autowired
    public UsuariosController(IUsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
        logger.info("UsuariosController inicializado y listo para recibir peticiones.");
    }

    /**
     * Endpoint para crear un nuevo usuario en el sistema.
     * Método HTTP: POST
     * Ruta: /usuarios
     *
     * @param request El DTO (Data Transfer Object) {@code CrearUsuarioRequest} que contiene los datos del nuevo usuario.
     *                {@code @Valid} activa las validaciones definidas en el DTO.
     *                {@code @RequestBody} indica que los datos del usuario vienen en el cuerpo de la petición.
     * @return {@code ResponseEntity} con el usuario creado y un estado HTTP 201 (Created) si es exitoso,
     * o un estado de error (ej. 400 Bad Request, 409 Conflict, 500 Internal Server Error).
     */
    @PostMapping // Mapea las peticiones POST a /usuarios
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody CrearUsuarioRequest request) {
        logger.info("Recibida solicitud para crear un nuevo usuario con email: {}", request.getEmail());
        try {
            Usuario nuevoUsuario = usuarioServicio.crearUsuario(request); // Llama al servicio para la lógica de negocio
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED); // Retorna 201 Created con el objeto usuario
        } catch (IllegalArgumentException e) {
            // Captura excepciones de argumentos inválidos (ej. datos faltantes o incorrectos)
            logger.warn("Error de validación o datos inválidos al crear usuario: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request
        } catch (DataIntegrityViolationException e) {
            // Captura excepciones de violación de integridad de datos (ej. email duplicado)
            logger.warn("Error de integridad de datos al crear usuario (ej. email ya registrado): {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Retorna 409 Conflict
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada
            logger.error("Error interno del servidor al crear usuario con email {}: {}", request.getEmail(), e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500 Internal Server Error
        }
    }

    /**
     * Endpoint para obtener los detalles de un usuario específico por su ID.
     * Método HTTP: GET
     * Ruta: /usuarios/{id}
     *
     * @param id El ID (identificador único) del usuario, extraído de la ruta de la URL.
     *           {@code @PathVariable} mapea el valor de la URL a este parámetro.
     * @return {@code ResponseEntity} con el objeto {@code Usuario} y un estado HTTP 200 (OK) si se encuentra,
     * o un estado HTTP 404 (Not Found) si el usuario no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        logger.info("Recibida solicitud para obtener usuario con ID: {}", id);
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id); // Llama al servicio para obtener el usuario
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK); // Retorna 200 OK con el objeto usuario
        } else {
            logger.warn("Usuario con ID {} no encontrado.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found
        }
    }

    /**
     * Endpoint para obtener una lista de todos los usuarios registrados en el sistema.
     * Método HTTP: GET
     * Ruta: /usuarios
     *
     * @return {@code ResponseEntity} con una lista de objetos {@code Usuario} y un estado HTTP 200 (OK).
     * Si no hay usuarios, devuelve una lista vacía.
     */
    @GetMapping // Mapea las peticiones GET a /usuarios
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        logger.info("Recibida solicitud para obtener todos los usuarios.");
        List<Usuario> usuarios = usuarioServicio.obtenerTodosLosUsuarios(); // Llama al servicio para obtener la lista
        return new ResponseEntity<>(usuarios, HttpStatus.OK); // Retorna 200 OK con la lista de usuarios
    }

    /**
     * Endpoint para actualizar la información de un usuario existente.
     * Método HTTP: PUT
     * Ruta: /usuarios/{id}
     *
     * @param id      El ID del usuario a actualizar.
     * @param request El DTO {@code ActualizarUsuarioRequest} con los datos que se desean modificar.
     * @return {@code ResponseEntity} con el usuario actualizado y un estado HTTP 200 (OK) si es exitoso,
     * o un estado de error (ej. 400 Bad Request, 404 Not Found, 409 Conflict, 500 Internal Server Error).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody ActualizarUsuarioRequest request) {
        logger.info("Recibida solicitud para actualizar usuario con ID: {}", id);
        try {
            Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(id, request); // Llama al servicio para actualizar
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK); // Retorna 200 OK con el objeto usuario actualizado
        } catch (IllegalArgumentException e) {
            // Captura si el usuario no es encontrado o hay datos inválidos
            logger.warn("Error al actualizar usuario {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retorna 400 Bad Request (si es por datos) o 404 Not Found (si es por ID)
        } catch (DataIntegrityViolationException e) {
            // Captura si hay una violación de integridad (ej. email duplicado)
            logger.warn("Error de integridad de datos al actualizar usuario {} (ej. email ya registrado): {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Retorna 409 Conflict
        } catch (Exception e) {
            logger.error("Error interno del servidor al actualizar usuario {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500 Internal Server Error
        }
    }

    /**
     * Endpoint para eliminar un usuario del sistema por su ID.
     * Método HTTP: DELETE
     * Ruta: /usuarios/{id}
     *
     * @param id El ID del usuario a eliminar.
     * @return {@code ResponseEntity} con un estado HTTP 204 (No Content) si la eliminación es exitosa,
     * o un estado de error (ej. 404 Not Found, 500 Internal Server Error).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        logger.info("Recibida solicitud para eliminar usuario con ID: {}", id);
        try {
            usuarioServicio.eliminarUsuario(id); // Llama al servicio para eliminar
            // No hay contenido que devolver en una eliminación exitosa
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 No Content
        } catch (IllegalArgumentException e) {
            // Captura si el usuario no es encontrado para eliminar
            logger.warn("Usuario con ID {} no encontrado para eliminar: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 Not Found
        } catch (Exception e) {
            logger.error("Error interno del servidor al eliminar usuario {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Retorna 500 Internal Server Error
        }
    }
}