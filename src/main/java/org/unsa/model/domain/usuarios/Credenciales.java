// file: src/main/java/org/unsa/model/dominio/usuarios/Credenciales.java
package org.unsa.model.domain.usuarios;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa las credenciales de autenticacion de un usuario.
 * Contiene el nombre de usuario y la contraseña (idealmente, un hash de la contraseña).
 */
public class Credenciales {
    private String nombreUsuario;
    private String contrasenaHash; // Almacenar hash de la contraseña, no texto plano

    private static final Logger logger = Logger.getLogger(Credenciales.class.getName());

    // Constantes para literales de String duplicados en toString()
    private static final String TO_STRING_PREFIX = "Credenciales{";
    private static final String NOMBRE_USUARIO_FIELD = "nombreUsuario='";
    private static final String CONTRASENA_HASH_FIELD = ", contrasenaHash='";
    private static final String TO_STRING_SUFFIX = "}";
    private static final String SINGLE_QUOTE = "'";


    /**
     * Constructor para la clase Credenciales.
     *
     * @param nombreUsuario  El nombre de usuario.
     * @param contrasenaHash El hash de la contraseña (nunca la contraseña en texto plano).
     * @throws IllegalArgumentException Si el nombre de usuario o el hash de contraseña son nulos o vacios.
     */
    public Credenciales(String nombreUsuario, String contrasenaHash) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            logger.log(Level.SEVERE, () -> "Intento de crear Credenciales con nombre de usuario nulo o vacio.");
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacio.");
        }
        if (contrasenaHash == null || contrasenaHash.trim().isEmpty()) {
            logger.log(Level.SEVERE, () -> "Intento de crear Credenciales con hash de contrasena nulo o vacio.");
            throw new IllegalArgumentException("El hash de contrasena no puede ser nulo o vacio.");
        }
        this.nombreUsuario = nombreUsuario;
        this.contrasenaHash = contrasenaHash;
        logger.info(() -> "Credenciales creadas para usuario: " + nombreUsuario);
    }

    // --- Getters y Setters ---
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            logger.log(Level.WARNING, () -> "Intento de establecer nombre de usuario nulo o vacio.");
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacio.");
        }
        this.nombreUsuario = nombreUsuario;
        logger.info(() -> "Nombre de usuario actualizado a: " + nombreUsuario);
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        if (contrasenaHash == null || contrasenaHash.trim().isEmpty()) {
            logger.log(Level.WARNING, () -> "Intento de establecer hash de contrasena nulo o vacio.");
            throw new IllegalArgumentException("El hash de contrasena no puede ser nulo o vacio.");
        }
        this.contrasenaHash = contrasenaHash;
        logger.info(() -> "Hash de contrasena actualizado para usuario: " + nombreUsuario);
    }

    /**
     * Representacion en cadena del objeto Credenciales para depuracion.
     * NOTA: Nunca exponer la contrasena hash directamente en logs de produccion.
     */
    @Override
    public String toString() {
        return TO_STRING_PREFIX + NOMBRE_USUARIO_FIELD + nombreUsuario + SINGLE_QUOTE + CONTRASENA_HASH_FIELD + "[HASH_OCULTO]" + SINGLE_QUOTE + // Ocultar hash por seguridad
                TO_STRING_SUFFIX;
    }

    /**
     * Compara si este objeto Credenciales es igual a otro objeto.
     * Dos objetos Credenciales se consideran iguales si tienen el mismo nombre de usuario.
     * (La comparacion de contrasena hash se haria en un servicio de autenticacion).
     *
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credenciales that = (Credenciales) o;
        return Objects.equals(nombreUsuario, that.nombreUsuario);
    }

    /**
     * Genera un codigo hash para este objeto Credenciales.
     * Es consistente con el metodo equals().
     *
     * @return El codigo hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombreUsuario);
    }
}
