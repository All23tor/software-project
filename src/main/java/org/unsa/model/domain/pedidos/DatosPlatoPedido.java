// file: src/main/java/org/unsa/model/dominio/pedidos/DatosPlatoPedido.java
package org.unsa.model.domain.pedidos;

import lombok.Getter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa los datos de un plato y su cantidad
 * tal como se recibirian en un carrito de compras o una solicitud de pedido.
 * Es un DTO (Data Transfer Object) simple.
 */
@Getter
public class DatosPlatoPedido {
    private final Integer idPlato;
    private final int cantidad;

    private static final Logger logger = Logger.getLogger(DatosPlatoPedido.class.getName());

    /**
     * Constructor para DatosPlatoPedido.
     *
     * @param idPlato  ID del plato.
     * @param cantidad Cantidad del plato.
     * @throws IllegalArgumentException Si el ID del plato es nulo/vacío o la cantidad es <= 0.
     */
    public DatosPlatoPedido(Integer idPlato, int cantidad) {
        if (idPlato == null) {
            logger.log(Level.SEVERE, () -> "Intento de crear DatosPlatoPedido con ID de plato nulo o vacio.");
            throw new IllegalArgumentException("El ID del plato no puede ser nulo o vacio.");
        }
        if (cantidad <= 0) {
            logger.log(Level.SEVERE, () -> "Intento de crear DatosPlatoPedido con cantidad invalida: " + cantidad);
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        this.idPlato = idPlato;
        this.cantidad = cantidad;
        logger.info(() -> "DatosPlatoPedido creado para plato ID: " + idPlato + ", cantidad: " + cantidad);
    }

    @Override
    public String toString() {
        return "DatosPlatoPedido{" + "idPlato='" + idPlato + '\'' + ", cantidad=" + cantidad + '}';
    }
}