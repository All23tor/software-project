// file: src/main/java/org/unsa/model/dominio/pedidos/Pedido.java
package org.unsa.model.domain.pedidos;

import jakarta.persistence.*; // Importar todas las anotaciones de JPA
import org.unsa.model.domain.usuarios.Direccion;
import org.unsa.model.domain.restaurantes.Dinero;
import org.unsa.model.domain.restaurantes.Restaurante;
import org.unsa.model.domain.usuarios.Cliente; // Importar Cliente para la relacion ManyToOne
import org.unsa.model.domain.usuarios.Repartidor; // Importar Repartidor para la relacion ManyToOne

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.*;

/**
 * Clase que representa un Pedido en el sistema SueldoMinimo App.
 * Contiene detalles del pedido, cliente, repartidor, restaurante y los items.
 */
@Data
@Entity
@Table(name = "pedidos") // Mapea esta entidad a la tabla "pedidos"
public class Pedido {
    @Id // Marca 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido; // ID de tipo int

    @ManyToOne // Relacion muchos a uno con Cliente
    @JoinColumn(name = "cliente_id", nullable = false) // Columna para la clave foranea
    private Cliente cliente; // Referencia al objeto Cliente

    @ManyToOne // Relacion muchos a uno con Repartidor (opcional)
    @JoinColumn(name = "repartidor_id") // Columna para la clave foranea, puede ser nula
    private Repartidor repartidor; // Referencia al objeto Repartidor

    @ManyToOne // Relacion muchos a uno con Restaurante
    @JoinColumn(name = "restaurante_id", nullable = false) // Columna para la clave foranea
    private Restaurante restaurante; // Referencia al objeto Restaurante

    @Temporal(TemporalType.TIMESTAMP) // Almacena la fecha y hora completa
    @Column(nullable = false)
    private Date fechaHoraCreacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    @Embedded // Incrusta el objeto Dinero
    @AttributeOverride(name = "valor", column = @Column(name = "monto_total_valor"))
    @AttributeOverride(name = "moneda", column = @Column(name = "monto_total_moneda"))
    private Dinero montoTotal;



    private String instruccionesEspeciales;

    @Embedded // Incrusta el objeto Direccion

            @AttributeOverride(name = "calle", column = @Column(name = "direccion_calle"))
            @AttributeOverride(name = "numero", column = @Column(name = "direccion_numero"))
            @AttributeOverride(name = "ciudad", column = @Column(name = "direccion_ciudad"))
            @AttributeOverride(name = "codigoPostal", column = @Column(name = "direccion_codigo_postal"))
            @AttributeOverride(name = "referencia", column = @Column(name = "direccion_referencia"))

    private Direccion direccionEntrega;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", orphanRemoval = true) // Un pedido tiene muchos items, cascada completa
    private List<ItemPedido> items = new ArrayList<>();
    // Inicializar para evitar NullPointerException

    @Transient // Indica que este campo no se mapeara a la base de datos
    private static final Logger logger = Logger.getLogger(Pedido.class.getName());

    // Constantes para literales de String duplicados en toString()
    private static final String TO_STRING_PREFIX = "Pedido{";
    private static final String ID_FIELD = "id=";
    private static final String CLIENTE_FIELD = ", cliente=";
    private static final String REPARTIDOR_FIELD = ", repartidor=";
    private static final String RESTAURANTE_FIELD = ", restaurante=";
    private static final String FECHA_HORA_CREACION_FIELD = ", fechaHoraCreacion=";
    private static final String ESTADO_FIELD = ", estado=";
    private static final String MONTO_TOTAL_FIELD = ", montoTotal=";
    private static final String INSTRUCCIONES_ESPECIALES_FIELD = ", instruccionesEspeciales='";
    private static final String DIRECCION_ENTREGA_FIELD = ", direccionEntrega=";
    private static final String ITEMS_FIELD = ", items=";
    private static final String TO_STRING_SUFFIX = "}";
    private static final String SINGLE_QUOTE = "'";

    /**
     * Constructor vacío para la clase Pedido.
     * Es necesario para JPA.
     */
    public Pedido() {
        this.fechaHoraCreacion = new Date();
        this.estado = EstadoPedido.PENDIENTE;
        this.montoTotal = new Dinero(0.0, "PEN"); // O la moneda por defecto
        logger.info(() -> "Nuevo pedido creado (constructor vacio). ID sera asignado por JPA.");
    }

    /**
     * Constructor completo para la clase Pedido.
     * Los IDs de Cliente, Repartidor y Restaurante se reemplazan por los objetos reales.
     * @param idPedido Identificador unico del pedido (int).
     * @param cliente Cliente que realiza el pedido.
     * @param restaurante Restaurante del pedido.
     * @param direccionEntrega Direccion de entrega del pedido.
     * @param items Lista de items del pedido.
     * @param instruccionesEspeciales Instrucciones adicionales para el pedido (puede ser nulo).
     */
    public Pedido(Integer idPedido, Cliente cliente, Restaurante restaurante, Direccion direccionEntrega, List<ItemPedido> items, String instruccionesEspeciales) { // ID cambiado a int
        // En un entorno real con GenerationType.IDENTITY, el ID no se pasaria
        // en el constructor para nuevas entidades, se dejaria a la DB.
        // Lo mantenemos para compatibilidad con TestUsuarios.java por ahora.
        this.idPedido = idPedido;

        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo.");
        if (restaurante == null) throw new IllegalArgumentException("El restaurante no puede ser nulo.");
        if (direccionEntrega == null) throw new IllegalArgumentException("La direccion de entrega no puede ser nula.");
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("La lista de items no puede ser nula o vacia.");

        this.cliente = cliente;
        this.restaurante = restaurante;
        this.direccionEntrega = direccionEntrega;
        this.items.addAll(items); // Añadir todos los items
        this.instruccionesEspeciales = instruccionesEspeciales;
        this.fechaHoraCreacion = new Date();
        this.estado = EstadoPedido.PENDIENTE;
        this.montoTotal = calcularMontoTotal(); // Calcular monto total al crear
        this.repartidor = null; // Inicializa repartidor como nulo (no asignado)
        logger.info(() -> "Pedido creado (completo) con ID: " + this.idPedido + " para cliente: " + this.cliente.getId());
    }

    // --- Getters ---
    // Retorna copia defensiva

    // --- Setters ---
    // --- Métodos de Comportamiento ---

    /**
     * Calcula el monto total del pedido sumando los subtotales de todos los items.
     * @return El monto total del pedido.
     */
    @Transient // Este metodo no es persistente directamente
    private Dinero calcularMontoTotal() {
        if (items == null || items.isEmpty()) {
            return new Dinero(0.0, "PEN"); // O la moneda por defecto
        }
        // Asegurarse de que el primer item tenga un subtotal con moneda para inicializar
        String monedaBase = items.getFirst().getSubtotal() != null ? items.getFirst().getSubtotal().getMoneda() : "PEN";
        Dinero total = new Dinero(0.0, monedaBase);
        for (ItemPedido item : items) {
            if (item.getSubtotal() != null) {
                total = total.sumar(item.getSubtotal());
            } else {
                logger.log(Level.WARNING, () -> "Item con subtotal nulo encontrado en pedido " + this.idPedido + ". Se ignorara.");
            }
        }
        return total;
    }

    /**
     * Actualiza el estado del pedido.
     * @param nuevoEstado El nuevo estado del pedido.
     */
    @Transient // Este metodo no es persistente directamente
    public void actualizarEstado(EstadoPedido nuevoEstado) {
        setEstado(nuevoEstado); // Reutiliza el setter con su validacion y log
    }

    // --- Métodos Esenciales para Objetos (equals y hashCode) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return idPedido == pedido.idPedido; // Pedidos son iguales si tienen el mismo ID (int)
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido); // Hash basado en int ID
    }

    // --- Método toString() ---
    @Override
    public String toString() {
        return TO_STRING_PREFIX +
                ID_FIELD + idPedido +
                CLIENTE_FIELD + (cliente != null ? cliente.getId() : "N/A") +
                REPARTIDOR_FIELD + (repartidor != null ? repartidor.getId() : "N/A") +
                RESTAURANTE_FIELD + (restaurante != null ? restaurante.getId() : "N/A") +
                FECHA_HORA_CREACION_FIELD + fechaHoraCreacion +
                ESTADO_FIELD + estado +
                MONTO_TOTAL_FIELD + montoTotal +
                INSTRUCCIONES_ESPECIALES_FIELD + (instruccionesEspeciales != null ? instruccionesEspeciales : "N/A") + SINGLE_QUOTE +
                DIRECCION_ENTREGA_FIELD + direccionEntrega +
                ITEMS_FIELD + items +
                TO_STRING_SUFFIX;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
}
