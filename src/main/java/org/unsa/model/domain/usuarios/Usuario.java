// file: src/main/java/org/unsa/model/dominio/usuarios/Usuario.java
package org.unsa.model.domain.usuarios;

import jakarta.persistence.*; // Importar todas las anotaciones de JPA
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Embedded;

/**
 * Clase que representa un Usuario base en el sistema SueldoMinimo App.
 * Demuestra el estilo "Things" (POO) con encapsulacion y responsabilidades claras.
 */
@Getter
@Setter
@Entity // Marca esta clase como una entidad JPA
@Table(name = "usuarios") // Mapea esta entidad a la tabla "usuarios"
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia de herencia para subclases
public class Usuario {
    @Id // Marca 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID sera autoincremental por la DB
    private Integer id;
    private String nombre;
    @Column(unique = true, nullable = false) // El email debe ser unico y no nulo
    private String email;
    private String telefono;
    @Temporal(TemporalType.TIMESTAMP) // Almacena la fecha y hora completa
    private Date fechaRegistro;
    private boolean activo;

    @Transient // Indica que este campo no se mapeara a la base de datos
    private static final Logger logger = Logger.getLogger(Usuario.class.getName());

    @Embedded
    private Direccion direccionPrincipal;
    // --- AGREGAR ESTAS CONSTANTES AQUÍ ---
    private static final String TO_STRING_PREFIX = "Usuario{"; // Inicio de la salida del toString
    private static final String ID_FIELD = "id=";
    private static final String NOMBRE_FIELD = ", nombre='";
    private static final String EMAIL_FIELD = ", email='";
    private static final String TELEFONO_FIELD = ", telefono='";
    private static final String FECHA_REGISTRO_FIELD = ", fechaRegistro=";
    private static final String ACTIVO_FIELD = ", activo=";
    private static final String TO_STRING_SUFFIX = "}"; // Fin de la salida del toString
    private static final String SINGLE_QUOTE = "'";
    // Lombok annotations (opcional, si decides usarlas para reducir boilerplate)
    // @Data
    // @NoArgsConstructor
    // @AllArgsConstructor

    /**
     * Constructor vacío para la clase Usuario.
     * Es necesario para JPA.
     */
    public Usuario(){
        this.fechaRegistro = new Date();
        this.activo = true;
        // No generamos ID aqui, JPA lo hara.
        logger.info(() -> "Nuevo usuario creado (constructor vacio). ID sera asignado por JPA.");
    }

    /**
     * Constructor para la clase Usuario con parametros iniciales.
     * El ID se pasa para simulacion, pero JPA lo gestionara en un entorno real.
     * @param id Identificador unico del usuario.
     * @param nombre Nombre completo del usuario.
     * @param email Correo electronico del usuario.
     * @param telefono Numero de telefono del usuario.
     */
    public Usuario(Integer id, String nombre, String email, String telefono) { // ID cambiado a int
        // En un entorno real con GenerationType.IDENTITY, el ID no se pasaria
        // en el constructor para nuevas entidades, se dejaria a la DB.
        // Lo mantenemos para compatibilidad con TestUsuarios.java por ahora.
        this.id = id; // Si id es 0, JPA lo autogenerara
        this.nombre = nombre;
        if (email == null || !email.contains("@")) {
            logger.log(Level.SEVERE, "Intento de crear usuario con email invalido: {}", email);
            throw new IllegalArgumentException("El email es invalido.");
        }
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = new Date();
        this.activo = true;
        logger.info(() -> "Nuevo usuario creado (con parametros) con ID: " + this.id + " y nombre: " + this.nombre);
    }

    // --- Getters y Setters ---


    public void setId(Integer id) { // Cambiado a int
        // La validación de ID positivo es más relevante para Ids pasados manualmente
        // Con GenerationType.IDENTITY, la DB se encarga.
        if (id <= 0 && id != 0) { // Permitir 0 para que JPA lo autogenere
            logger.log(Level.WARNING, "Intento de establecer ID de usuario invalido: {}", id);
            throw new IllegalArgumentException("El ID debe ser positivo o 0 para autogeneración.");
        }
        this.id = id;
        logger.info(() -> "ID de usuario actualizado a: " + id);
    }

    /**
     * Establece el correo electronico del usuario.
     * Demuestra Error/Exception Handling con validacion.
     * @param email Nuevo correo electronico.
     * @throws IllegalArgumentException Si el email es nulo o no contiene '@'.
     */
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            logger.log(Level.WARNING, "Intento de establecer email invalido {}", email);
            throw new IllegalArgumentException("El email es invalido.");
        }
        this.email = email;
        logger.info(() -> "Email actualizado para usuario " + this.id + " a: " + email);
    }

    // --- Métodos de Comportamiento (Parte del estilo "Things") ---

    private static final String ID_STRING = "(ID: ";

    /**
     * Actualiza los datos de contacto (email y telefono) del usuario.
     * @param email Nuevo correo electronico.
     * @param telefono Nuevo numero de telefono.
     */
    public void actualizarDatosContacto(String email, String telefono) {
        try {
            setEmail(email); // Esto puede lanzar IllegalArgumentException
            this.telefono = telefono;
            logger.info(() -> "Datos de contacto actualizados para usuario " + this.nombre + ID_STRING + this.id + ")");
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, String.format("Fallo al actualizar datos de contacto para usuario %d: %s", this.id, e.getMessage()), e);
        }
    }

    /**
     * Activa la cuenta del usuario.
     */
    public void activarCuenta() {
        this.activo = true;
        logger.info(() -> "Cuenta de usuario " + this.nombre + ID_STRING + this.id + ") activada.");
    }

    /**
     * Desactiva la cuenta del usuario.
     */
    public void desactivarCuenta() {
        this.activo = false;
        logger.info(() -> "Cuenta de usuario " + this.nombre + ID_STRING + this.id + ") desactivada.");
    }

    /**
     * Representacion en cadena del objeto Usuario para depuracion.
     */
    @Override
    public String toString() {
        return TO_STRING_PREFIX +
                ID_FIELD + id +
                NOMBRE_FIELD + nombre + SINGLE_QUOTE +
                EMAIL_FIELD + email + SINGLE_QUOTE +
                TELEFONO_FIELD + telefono + SINGLE_QUOTE +
                FECHA_REGISTRO_FIELD + fechaRegistro +
                ACTIVO_FIELD + activo +
                TO_STRING_SUFFIX;
    }

    /**
     * Compara si este objeto Usuario es igual a otro objeto.
     * Dos objetos Usuario se consideran iguales si tienen el mismo ID.
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id; // Comparación de int ID
    }

    /**
     * Genera un codigo hash para este objeto Usuario.
     * Es consistente con el metodo equals().
     * @return El codigo hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash basado en int ID
    }
}
