// file: src/main/java/org/unsa/model/dominio/usuarios/Repartidor.java
package org.unsa.model.domain.usuarios;

import jakarta.persistence.Entity; // Importar la anotacion Entity
import jakarta.persistence.PrimaryKeyJoinColumn; // Para herencia JOINED
import jakarta.persistence.Transient; // Para campos no persistentes
import lombok.*;

import java.util.logging.Logger;

/**
 * Clase que representa a un Repartidor en el sistema SueldoMinimo App.
 * Extiende de Usuario y puede incluir atributos especificos de repartidor.
 */
@Getter
@Setter
@Entity // Marca esta clase como una entidad JPA
@PrimaryKeyJoinColumn(name = "id") // Especifica la columna de union con la tabla padre
@EqualsAndHashCode(callSuper = false)
public class Repartidor extends Usuario {

    // --- Getters y Setters especificos de Repartidor ---
    private String tipoVehiculo; // Ejemplo: "Moto", "Bicicleta", "Auto"
    private boolean disponibleParaEntregas; // Estado de disponibilidad
    @Transient // Indica que este campo no se mapeara a la base de datos
    private static final Logger logger = Logger.getLogger(Repartidor.class.getName());

    // Constantes para literales de String duplicados en toString()
    private static final String TO_STRING_PREFIX = "Repartidor{";
    private static final String TIPO_VEHICULO_FIELD = ", tipoVehiculo='";
    private static final String DISPONIBLE_FIELD = ", disponibleParaEntregas=";
    private static final String TO_STRING_SUFFIX = "}";
    private static final String SINGLE_QUOTE = "'";

    /**
     * Constructor vacío para JPA.
     */
    public Repartidor() {
        super();
    }

    /**
     * Constructor para la clase Repartidor.
     *
     * @param idRepartidor           Identificador unico del repartidor (int).
     * @param nombre                 Nombre completo del repartidor.
     * @param email                  Correo electronico del repartidor.
     * @param telefono               Numero de telefono del repartidor.
     * @param tipoVehiculo           Tipo de vehiculo que utiliza el repartidor.
     * @param disponibleParaEntregas Estado de disponibilidad para nuevas entregas.
     */
    public Repartidor(Integer idRepartidor, String nombre, String email, String telefono, String tipoVehiculo, boolean disponibleParaEntregas) { // ID cambiado a int
        super(idRepartidor, nombre, email, telefono); // Llama al constructor de la clase padre Usuario
        this.tipoVehiculo = tipoVehiculo;
        this.disponibleParaEntregas = disponibleParaEntregas;
        logger.info(() -> "Repartidor creado con ID: " + getId() + ", nombre: " + getNombre());
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
        logger.info(() -> "Tipo de vehiculo actualizado para repartidor " + getId() + " a: " + tipoVehiculo);
    }

    public void setDisponibleParaEntregas(boolean disponibleParaEntregas) {
        this.disponibleParaEntregas = disponibleParaEntregas;
        logger.info(() -> "Disponibilidad actualizada para repartidor " + getId() + " a: " + disponibleParaEntregas);
    }

    /**
     * Representacion en cadena del objeto Repartidor para depuracion.
     */
    @Override
    public String toString() {
        return TO_STRING_PREFIX + "id=" + getId() + // Cambiado para int
                ", nombre='" + getNombre() + SINGLE_QUOTE + ", email='" + getEmail() + SINGLE_QUOTE + ", telefono='" + getTelefono() + SINGLE_QUOTE + ", fechaRegistro=" + getFechaRegistro() + ", activo=" + isActivo() + TIPO_VEHICULO_FIELD + (tipoVehiculo != null ? tipoVehiculo : "N/A") + SINGLE_QUOTE + DISPONIBLE_FIELD + disponibleParaEntregas + TO_STRING_SUFFIX;
    }
}
