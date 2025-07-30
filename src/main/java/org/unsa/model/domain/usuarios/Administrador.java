// file: src/main/java/org/unsa/model/dominio/usuarios/Administrador.java
package org.unsa.model.domain.usuarios;

import jakarta.persistence.Entity; // Importar la anotacion Entity
import jakarta.persistence.PrimaryKeyJoinColumn; // Para herencia JOINED
import jakarta.persistence.Transient; // Para campos no persistentes
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;

/**
 * Clase que representa un Administrador en el sistema.
 * Extiende de Usuario, demostrando Herencia (parte del estilo "Things").
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Entity // Marca esta clase como una entidad JPA
@PrimaryKeyJoinColumn(name = "id") // Especifica la columna de union con la tabla padre
public class Administrador extends Usuario {
    @Transient // Indica que este campo no se mapeara a la base de datos
    private static final Logger logger = Logger.getLogger(Administrador.class.getName());

    // Constantes para literales de String duplicados en toString() y logs
    private static final String TO_STRING_PREFIX = "Administrador{";
    private static final String DEPARTAMENTO_FIELD = ", departamento='";
    private static final String TO_STRING_SUFFIX = "}";
    private static final String SINGLE_QUOTE = "'";
    private static final String LOG_PREFIX_ADMIN = "Administrador ";

    // --- Getters y Setters específicos de Administrador ---
    private String departamento;

    /**
     * Constructor vacío para JPA.
     */
    public Administrador() {
        super();
    }

    /**
     * Constructor para la clase Administrador.
     * @param id Identificador unico del administrador.
     * @param nombre Nombre completo del administrador.
     * @param email Correo electronico del administrador.
     * @param telefono Numero de telefono del administrador.
     * @param departamento Departamento al que pertenece el administrador.
     */
    public Administrador(int id, String nombre , String email, String telefono, String departamento){ // ID cambiado a int
        super(id,nombre,email,telefono);
        this.departamento = departamento;
        logger.info(() -> LOG_PREFIX_ADMIN + getNombre() + " creado con ID: " + getId());
    }

    /**
     * Representacion en cadena del objeto Administrador para depuracion.
     */
    @Override
    public String toString() {
        return TO_STRING_PREFIX +
                "id=" + getId() + // Cambiado para int
                ", nombre='" + getNombre() + SINGLE_QUOTE +
                ", email='" + getEmail() + SINGLE_QUOTE +
                ", telefono='" + getTelefono() + SINGLE_QUOTE +
                ", fechaRegistro=" + getFechaRegistro() +
                ", activo=" + isActivo() +
                DEPARTAMENTO_FIELD + departamento + SINGLE_QUOTE +
                TO_STRING_SUFFIX;
    }
}
