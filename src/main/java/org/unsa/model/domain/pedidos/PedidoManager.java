package org.unsa.model.domain.pedidos;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unsa.model.domain.usuarios.Cliente;
import org.unsa.model.domain.usuarios.Direccion;
import org.unsa.model.domain.usuarios.Repartidor;
import org.unsa.model.domain.restaurantes.Restaurante;
import org.unsa.model.repository.ClienteRepository;
import org.unsa.model.repository.PedidoRepository;
import org.unsa.model.repository.RepartidorRepository;
import org.unsa.model.repository.RestauranteRepository;
import org.unsa.model.service.Interfaces.IPedidoServicio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PedidoManager implements IPedidoServicio {

    private static final Logger logger = Logger.getLogger(PedidoManager.class.getName());

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final RepartidorRepository repartidorRepository;

    public Pedido crearNuevoPedido(Integer idCliente, Integer idRestaurante, List<DatosPlatoPedido> itemsCarrito, Direccion direccionEntrega, String instruccionesEspeciales) {
        try {
            Cliente cliente = clienteRepository.findById(idCliente)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + idCliente));
            Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                    .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " + idRestaurante));

            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setRestaurante(restaurante);
            pedido.setDireccionEntrega(direccionEntrega);
            pedido.setInstruccionesEspeciales(instruccionesEspeciales);
            pedido.setEstado(EstadoPedido.PENDIENTE);

            // Convertir DatosPlatoPedido -> ItemPedido aquí si es necesario
            // pedido.setItems(convertirItems(itemsCarrito));

            Pedido pedidoGuardado = pedidoRepository.save(pedido);
            logger.info("Pedido creado exitosamente con ID: " + pedidoGuardado.getIdPedido());
            return pedidoGuardado;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al crear el pedido: " + e.getMessage(), e);
            throw new RuntimeException("No se pudo crear el pedido.");
        }
    }

    @Override
    public Pedido crearPedido(Integer idCliente, Integer idRestaurante, List<DatosPlatoPedido> itemsCarrito, Direccion direccionEntrega, String instruccionesEspeciales) {
        return crearNuevoPedido(idCliente, idRestaurante, itemsCarrito, direccionEntrega, instruccionesEspeciales);
    }

    @Override
    public Pedido obtenerPedidoPorId(Integer idPedido) {
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + idPedido));
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(Integer idCliente) {
        return pedidoRepository.findByCliente_Id(idCliente);
    }

    @Override
    public void actualizarEstadoPedido(Integer idPedido, EstadoPedido nuevoEstado) {
        Pedido pedido = obtenerPedidoPorId(idPedido);
        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);
    }

    @Override
    public void asignarRepartidorAPedido(Integer idPedido, Integer idRepartidor) {
        Pedido pedido = obtenerPedidoPorId(idPedido);
        Repartidor repartidor = repartidorRepository.findById(idRepartidor)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con ID: " + idRepartidor));
        pedido.setRepartidor(repartidor);
        pedido.setEstado(EstadoPedido.EN_CAMINO);
        pedidoRepository.save(pedido);
    }

    @Override
    public void cancelarPedido(Integer idPedido, Integer idUsuario) {
        Pedido pedido = obtenerPedidoPorId(idPedido);
        pedido.setEstado(EstadoPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    @Override
    public void confirmarEntrega(Integer idPedido) {
        Pedido pedido = obtenerPedidoPorId(idPedido);
        pedido.setEstado(EstadoPedido.ENTREGADO);
        pedidoRepository.save(pedido);
    }
}
