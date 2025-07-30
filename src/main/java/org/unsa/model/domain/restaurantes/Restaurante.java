// file: src/main/java/org/unsa/model/dominio/restaurantes/Restaurante.java
package org.unsa.model.domain.restaurantes;

import jakarta.persistence.*; // Importar todas las anotaciones de JPA
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa un Restaurante en el sistema SueldoMinimo App.
 * Demuestra el estilo "Things" (POO) con encapsulacion y responsabilidades claras.
 */
@Getter
@Entity // Marca esta clase como una entidad JPA
@Table(name = "restaurantes") // Mapea esta entidad a la tabla "restaurantes"
public class Restaurante {
    @Id // Marca 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID sera autoincremental por la DB
    private Integer id; // Cambiado a int
    @Setter
    @Column(nullable = false) // El nombre no puede ser nulo
    private String nombre;
    @Setter
    private String direccion;
    @Setter
    private String telefono;
    @Setter
    @Enumerated(EnumType.STRING) // Almacena el enum como String en la DB
    @Column(nullable = false) // El tipo de cocina no puede ser nulo
    private TipoCocina tipoCocina;

    @Transient // Indica que este campo no se mapeara a la base de datos
    private static final Logger logger = Logger.getLogger(Restaurante.class.getName());

    // Constantes para literales de String duplicados en toString()
    private static final String TO_STRING_PREFIX = "Restaurante{";
    private static final String ID_FIELD = "id=";
    private static final String NOMBRE_FIELD = ", nombre='";
    private static final String DIRECCION_FIELD = ", direccion='";
    private static final String TELEFONO_FIELD = ", telefono='";
    private static final String TIPO_COCINA_FIELD = ", tipoCocina=";
    private static final String TO_STRING_SUFFIX = "}";
    private static final String SINGLE_QUOTE = "'";

    /**
     * Constructor vacío para la clase Restaurante.
     * Es necesario para JPA.
     */
    public Restaurante() {
        logger.info(() -> "Nuevo restaurante creado (constructor vacio). ID sera asignado por JPA.");
    }

    /**
     * Constructor para la clase Restaurante con parametros iniciales.
     * El ID se pasa para simulacion, pero JPA lo gestionara en un entorno real.
     *
     * @param id         Identificador unico del restaurante (int).
     * @param nombre     Nombre del restaurante.
     * @param direccion  Direccion fisica del restaurante.
     * @param telefono   Numero de telefono del restaurante.
     * @param tipoCocina Tipo de cocina que ofrece el restaurante.
     */
    public Restaurante(Integer id, String nombre, String direccion, String telefono, TipoCocina tipoCocina) { // ID cambiado a int
        // En un entorno real con GenerationType.IDENTITY, el ID no se pasaria
        // en el constructor para nuevas entidades, se dejaria a la DB.
        // Lo mantenemos para compatibilidad con TestUsuarios.java por ahora.
        this.id = id; // Si id es 0, JPA lo autogenerara
        if (nombre == null || nombre.trim().isEmpty()) {
            logger.log(Level.SEVERE, "Intento de crear restaurante con nombre nulo o vacio.");
            throw new IllegalArgumentException("El nombre del restaurante no puede ser nulo o vacio.");
        }
        if (tipoCocina == null) {
            logger.log(Level.SEVERE, "Intento de crear restaurante con tipo de cocina nulo.");
            throw new IllegalArgumentException("El tipo de cocina no puede ser nulo.");
        }
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipoCocina = tipoCocina;
        logger.info(() -> "Restaurante creado con ID: " + this.id + " y nombre: " + this.nombre);
    }

    // --- Getters y Setters ---

    public void setId(Integer id) {
        if (id <= 0 && id != 0) { // Permitir 0 para que JPA lo autogenere
            logger.log(Level.WARNING, "Intento de establecer ID de restaurante invalido: {}", id);
            throw new IllegalArgumentException("El ID debe ser positivo o 0 para autogeneracion.");
        }
        this.id = id;
        logger.info(() -> "ID de restaurante actualizado a: " + id);
    }

    /**
     * Representacion en cadena del objeto Restaurante para depuracion.
     */
    @Override
    public String toString() {
        return TO_STRING_PREFIX + ID_FIELD + id + NOMBRE_FIELD + nombre + SINGLE_QUOTE + DIRECCION_FIELD + (direccion != null ? direccion : "N/A") + SINGLE_QUOTE + TELEFONO_FIELD + (telefono != null ? telefono : "N/A") + SINGLE_QUOTE + TIPO_COCINA_FIELD + tipoCocina + TO_STRING_SUFFIX;
    }

    /**
     * Compara si este objeto Restaurante es igual a otro objeto.
     * Dos objetos Restaurante se consideran iguales si tienen el mismo ID.
     *
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return id.equals(that.id); // Comparacion de int ID
    }

    /**
     * Genera un codigo hash para este objeto Restaurante.
     * Es consistente con el metodo equals().
     *
     * @return El codigo hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash basado en int ID
    }
}
