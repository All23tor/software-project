// file: src/main/java/org/unsa/service/interfaces/IPedidoServicio.java
package org.unsa.model.service.interfaces;

import org.unsa.model.domain.pedidos.Pedido;
import org.unsa.model.domain.pedidos.DatosPlatoPedido;
import org.unsa.model.domain.pedidos.EstadoPedido;
import org.unsa.model.domain.usuarios.Direccion;

import java.util.List;

/**
 * Interfaz para el servicio de gestion de pedidos.
 * Define las operaciones de negocio relacionadas con los pedidos.
 */
public interface IPedidoServicio {
    Pedido crearPedido(Integer idCliente, Integer idRestaurante, List<DatosPlatoPedido> itemsCarrito, Direccion direccionEntrega, String instruccionesEspeciales);

    Pedido obtenerPedidoPorId(Integer idPedido);

    List<Pedido> obtenerPedidosPorCliente(Integer idCliente);

    void actualizarEstadoPedido(Integer idPedido, EstadoPedido nuevoEstado);

    void asignarRepartidorAPedido(Integer idPedido, Integer idRepartidor);

    void cancelarPedido(Integer idPedido, Integer idUsuario);

    void confirmarEntrega(Integer idPedido);
}
