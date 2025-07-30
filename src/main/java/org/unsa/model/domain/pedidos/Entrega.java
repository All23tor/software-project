package org.unsa.model.domain.pedidos;

import jakarta.persistence.*; // Importa todas las anotaciones JPA
import org.unsa.model.domain.usuarios.Repartidor;

import java.util.Date;
import java.util.Objects;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que representa una Entrega en el sistema.
 * Debe ser una entidad JPA para ser persistida.
 */
@Getter
@Setter
@Entity // Marca esta clase como una entidad JPA
@Table(name = "entregas") // Mapea esta entidad a la tabla "entregas"
public class Entrega {

    @Id // Marca 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Integer id; // Usar Integer es la mejor práctica para IDs JPA

    // Relación ManyToOne con Pedido
    // Una entrega pertenece a un pedido. Un pedido puede tener una o más entregas (aunque típicamente es 1:1)
    @ManyToOne(fetch = FetchType.LAZY) // Carga perezosa para optimización
    @JoinColumn(name = "pedido_id", nullable = false) // Columna FK en la tabla 'entregas'
    private Pedido pedido; // Referencia al objeto Pedido

    // Relación ManyToOne con Repartidor
    // Una entrega es asignada a un repartidor. Un repartidor puede tener muchas entregas.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repartidor_id") // Columna FK en la tabla 'entregas'. Puede ser nulo si aún no se asigna.
    private Repartidor repartidor; // Referencia al objeto Repartidor

    @Enumerated(EnumType.STRING) // Almacena el enum como String en la DB
    @Column(nullable = false)
    private EstadoEntrega estado;

    @Temporal(TemporalType.TIMESTAMP) // Almacena fecha y hora
    private Date fechaHoraAsignacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRecojo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraEntrega;

    private String ubicacionActualRepartidor; // Coordenadas o descripción de la ubicación

    @Transient // Este campo no se mapeará a la base de datos
    private static final Logger logger = LoggerFactory.getLogger(Entrega.class);

    // Constantes para toString (si las usas, como en otras entidades)
    private static final String TO_STRING_PREFIX = "Entrega{";
    private static final String ID_FIELD = "id=";
    private static final String PEDIDO_ID_FIELD = ", pedidoId=";
    private static final String REPARTIDOR_ID_FIELD = ", repartidorId=";
    private static final String ESTADO_FIELD = ", estado=";
    private static final String TO_STRING_SUFFIX = "}";

    /**
     * Constructor vacío requerido por JPA.
     */
    public Entrega() {
        this.estado = EstadoEntrega.PENDIENTE; // Estado inicial por defecto
        logger.info("Nueva instancia de Entrega creada (constructor vacío).");
    }

    /**
     * Constructor para crear una nueva entrega con el pedido asociado.
     * El ID del repartidor y las fechas se asignarán en el servicio.
     *
     * @param pedido El objeto Pedido al que pertenece esta entrega.
     */
    public Entrega(Pedido pedido) {
        this.pedido = Objects.requireNonNull(pedido, "El pedido no puede ser nulo para una entrega.");
        this.estado = EstadoEntrega.PENDIENTE; // Estado inicial
        this.fechaHoraAsignacion = null;
        this.fechaHoraRecojo = null;
        this.fechaHoraEntrega = null;
        logger.info("Nueva Entrega creada para Pedido ID: {} ", pedido.getIdPedido());
    }

    /**
     * Asigna un repartidor a esta entrega. Este método asume que el objeto Repartidor ya está cargado.
     *
     * @param repartidor El objeto Repartidor asignado.
     */
    public void asignarRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }
        this.repartidor = repartidor;
        this.estado = EstadoEntrega.ASIGNADO;
        this.fechaHoraAsignacion = new Date();
        logger.info("Entrega ID  {}  asignada a Repartidor ID {} ", id, repartidor.getId());
    }

    private static String idString = "Entrega ID ";

    /**
     * Actualiza el estado de la entrega.
     *
     * @param nuevoEstado El nuevo estado de la entrega.
     * @throws IllegalStateException Si el cambio de estado no es válido.
     */
    public void actualizarEstado(EstadoEntrega nuevoEstado) {
        // Ejemplo de validación de transición de estados. Ajusta según tu lógica de negocio.
        if (this.estado == EstadoEntrega.PROPORCIONADO && nuevoEstado != EstadoEntrega.INCIDENCIA) {
            // Asumo que PROPORCIONADO en tu código original era ENTREGADO.
            throw new IllegalStateException("No se puede cambiar el estado de una entrega ya completada a menos que sea una incidencia.");
        }
        this.estado = nuevoEstado;
        logger.info("{}{} cambió a estado: {}", idString, id, nuevoEstado);
    }

    /**
     * Registra el momento en que el pedido fue recogido por el repartidor.
     */
    public void registrarRecojo() {
        if (this.estado != EstadoEntrega.ASIGNADO) { // Solo si ya está asignado
            throw new IllegalStateException("El pedido no puede ser recogido si no ha sido asignado.");
        }
        this.estado = EstadoEntrega.RECOGIDO;
        this.fechaHoraRecojo = new Date();
        logger.info("{}{}: Pedido recogido.", idString, id);
    }

    /**
     * Registra el momento en que el pedido fue entregado al cliente.
     */
    public void registrarEntrega() {
        if (this.estado != EstadoEntrega.RECOGIDO) { // Solo si ya fue recogido
            throw new IllegalStateException("El pedido no puede ser entregado si no ha sido recogido.");
        }
        this.estado = EstadoEntrega.PROPORCIONADO;
        this.fechaHoraEntrega = new Date();
        logger.info("{}{}: Pedido entregado.", idString, id);
    }


    /**
     * Actualiza la ubicación actual del repartidor para esta entrega.
     *
     * @param coordenadas Las coordenadas o descripción de la ubicación.
     */
    public void actualizarUbicacion(String coordenadas) {
        this.ubicacionActualRepartidor = coordenadas;
        logger.info("{}{}: Ubicación actualizada a: {}", idString, id, coordenadas);
    }

    @Override
    public String toString() {
        return TO_STRING_PREFIX + ID_FIELD + id + PEDIDO_ID_FIELD + (pedido != null ? pedido.getIdPedido() : "N/A") + REPARTIDOR_ID_FIELD + (repartidor != null ? repartidor.getId() : "N/A") + ESTADO_FIELD + estado + ", fechaHoraAsignacion=" + fechaHoraAsignacion + ", fechaHoraRecojo=" + fechaHoraRecojo + ", fechaHoraEntrega=" + fechaHoraEntrega + ", ubicacionActualRepartidor='" + ubicacionActualRepartidor + '\'' + TO_STRING_SUFFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrega entrega = (Entrega) o;
        return Objects.equals(id, entrega.id); // Comparación de Integer ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Hash basado en Integer ID
    }
}