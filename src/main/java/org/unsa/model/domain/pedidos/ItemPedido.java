// file: src/main/java/org/unsa/model/dominio/pedidos/ItemPedido.java
package org.unsa.model.domain.pedidos;

import jakarta.persistence.*;
import org.unsa.model.domain.restaurantes.Dinero;
import org.unsa.model.domain.restaurantes.Plato;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa un item individual dentro de un pedido.
 * Contiene el plato y la cantidad solicitada.
 * Esta clase sera un componente incrustable en Pedido, o una entidad separada
 * si se necesita una clave primaria compuesta o mas complejidad.
 * Para simplificar, la haremos una entidad separada con su propio ID.
 */
@Entity // Marca esta clase como una entidad JPA
@Table(name = "items_pedido") // Mapea esta entidad a la tabla "items_pedido"
public class ItemPedido {
    @Id // Marca 'id' como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID sera autoincremental por la DB
    private Integer id; // ID propio para el ItemPedido

    @ManyToOne // Relacion muchos a uno con Plato
    @JoinColumn(name = "plato_id", nullable = false) // Columna para la clave foranea
    private Plato plato;
    private int cantidad;

    @Embedded // Indica que Dinero es un componente incrustable

    @AttributeOverride(name = "valor", column = @Column(name = "subtotal_valor"))
    @AttributeOverride(name = "moneda", column = @Column(name = "subtotal_moneda"))
    private Dinero subtotal; // Subtotal calculado para este item

    @ManyToOne(fetch = FetchType.LAZY) // Many ItemPedidos to One Pedido
    @JoinColumn(name = "pedido_id", nullable = false) // Foreign key column in items_pedido table
    private Pedido pedido;

    @Transient // Indica que este campo no se mapeara a la base de datos
    private static final Logger logger = Logger.getLogger(ItemPedido.class.getName());

    /**
     * Constructor vacío para JPA.
     */
    public ItemPedido() {} // Necesario para JPA

    /**
     * Constructor para ItemPedido.
     * @param plato El objeto Plato asociado a este item.
     * @param cantidad La cantidad de este plato en el pedido.
     * @throws IllegalArgumentException Si el plato es nulo o la cantidad es menor o igual a cero.
     */
    public ItemPedido(Plato plato, int cantidad) {
        if (plato == null) {
            logger.log(Level.SEVERE, () -> "Intento de crear ItemPedido con plato nulo.");
            throw new IllegalArgumentException("El plato no puede ser nulo.");
        }
        if (cantidad <= 0) {
            logger.log(Level.SEVERE, () -> "Intento de crear ItemPedido con cantidad invalida: " + cantidad);
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        this.plato = plato;
        this.cantidad = cantidad;
        // Calcular subtotal al crear el item
        if (plato.getPrecio() != null) {
            this.subtotal = new Dinero(plato.getPrecio().getValor() * cantidad, plato.getPrecio().getMoneda());
        } else {
            this.subtotal = new Dinero(0.0, "PEN"); // O la moneda por defecto
            logger.log(Level.WARNING, () -> "Plato '" + plato.getNombre() + "' no tiene precio definido. Subtotal a 0.");
        }
        logger.info(() -> "ItemPedido creado: " + this.toString());
    }

    // --- Getters ---
    public Integer getId() { return id; } // Getter para el ID propio del ItemPedido
    public void setId(int id) { this.id = id; } // Setter para el ID propio del ItemPedido

    public Plato getPlato() {
        return plato;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Dinero getSubtotal() {
        return subtotal;
    }
    public Pedido getPedido() { return pedido; }

    // --- Setters ---
    public void setId(Integer id ) { this.id = id; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public void setPlato(Plato plato) {
        if (plato == null) {
            logger.log(Level.WARNING, () -> "Intento de establecer plato nulo para ItemPedido.");
            throw new IllegalArgumentException("El plato no puede ser nulo.");
        }
        this.plato = plato;
        // Recalcular subtotal si el plato cambia
        if (plato.getPrecio() != null) {
            this.subtotal = new Dinero(plato.getPrecio().getValor() * cantidad, plato.getPrecio().getMoneda());
        } else {
            this.subtotal = new Dinero(0.0, "PEN");
        }
        logger.info(() -> "Plato de ItemPedido actualizado a: " + plato.getNombre());
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            logger.log(Level.WARNING, () -> "Intento de establecer cantidad invalida para ItemPedido: " + cantidad);
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        this.cantidad = cantidad;
        // Recalcular subtotal si la cantidad cambia
        if (plato != null && plato.getPrecio() != null) {
            this.subtotal = new Dinero(plato.getPrecio().getValor() * cantidad, plato.getPrecio().getMoneda());
        }
        logger.info(() -> "Cantidad de ItemPedido actualizada a: " + cantidad);
    }



    // --- Métodos Esenciales para Objetos (equals y hashCode) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido itemPedido = (ItemPedido) o;
        return id.equals(itemPedido.id); // ItemsPedido son iguales si tienen el mismo ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --- Método toString() ---
    @Override
    public String toString() {
        return "ItemPedido{" +
                "id=" + id +
                ", plato=" + (plato != null ? plato.getNombre() : "N/A") +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                '}';
    }
}
