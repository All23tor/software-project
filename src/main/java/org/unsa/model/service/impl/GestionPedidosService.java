// file: src/main/java/org/unsa/model/servicios/impl/GestionPedidosService.java
package org.unsa.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unsa.model.domain.pedidos.Pedido;
import org.unsa.model.domain.pedidos.ItemPedido;
import org.unsa.model.domain.usuarios.Cliente;
import org.unsa.model.domain.restaurantes.Restaurante;
import org.unsa.model.domain.restaurantes.Plato;
import org.unsa.model.domain.usuarios.Repartidor;
import org.unsa.model.domain.pedidos.DatosPlatoPedido;
import org.unsa.model.domain.pedidos.EstadoPedido;
import org.unsa.model.domain.usuarios.Direccion;
import org.unsa.model.service.interfaces.IPedidoServicio;
import org.unsa.model.repository.PedidoRepository;
import org.unsa.model.repository.ClienteRepository;
import org.unsa.model.repository.RestauranteRepository;
import org.unsa.model.repository.PlatoRepository;
import org.unsa.model.repository.RepartidorRepository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de servicio para la gestion de pedidos.
 * Contiene la logica de negocio relacionada con la creacion, actualizacion y consulta de pedidos.
 */
@Service
public class GestionPedidosService implements IPedidoServicio {

    private static final Logger logger = LoggerFactory.getLogger(GestionPedidosService.class);
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final PlatoRepository platoRepository;
    private final RepartidorRepository repartidorRepository;

    /**
     * Constructor por defecto para la clase GestionPedidosService.
     * Inicializa el PedidoManager.
     */
    @Autowired
    public GestionPedidosService(PedidoRepository pedidoRepository,
                                 RepartidorRepository repartidorRepository,
                                 ClienteRepository clienteRepository,
                                 RestauranteRepository restauranteRepository,
                                 PlatoRepository platoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.repartidorRepository = repartidorRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.platoRepository = platoRepository;
        logger.info("Servicio de GestionPedidosService inicializado con repositorios.");
    }

    /**
     * Crea un nuevo pedido en el sistema, encapsulando la logica del PedidoManager.
     *
     * @param idCliente               Id del cliente que realiza el pedido.
     * @param idRestaurante           Id del restaurante al que se hace el pedido.
     * @param itemsCarrito            Lista de datos de platos y cantidades para el pedido.
     * @param direccionEntrega        Direccion de entrega del pedido.
     * @param instruccionesEspeciales Instrucciones adicionales para el pedido (puede ser nulo).
     * @return El objeto Pedido recien creado.
     * @throws IllegalArgumentException Si los datos proporcionados son invalidos.
     */
    @Override
    @Transactional // Esto es muy importante para métodos que modifican la DB
    public Pedido crearPedido(
            Integer idCliente,
            Integer idRestaurante,
            List<DatosPlatoPedido> itemsCarrito,
            Direccion direccionEntrega,
            String instruccionesEspeciales) {

        logger.info("Solicitud para crear un nuevo pedido para cliente {} en restaurante {}", idCliente, idRestaurante);

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + idCliente));
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante no encontrado con ID: " + idRestaurante));

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setCliente(cliente);
        nuevoPedido.setRestaurante(restaurante);
        nuevoPedido.setDireccionEntrega(direccionEntrega);
        nuevoPedido.setInstruccionesEspeciales(instruccionesEspeciales);
        nuevoPedido.setEstado(EstadoPedido.PENDIENTE);

        for (DatosPlatoPedido itemDto : itemsCarrito) {
            Plato plato = platoRepository.findById(itemDto.getIdPlato())
                    .orElseThrow(() -> new IllegalArgumentException("Plato no encontrado con ID: " + itemDto.getIdPlato()));

            ItemPedido item = new ItemPedido();
            item.setPlato(plato);
            item.setCantidad(itemDto.getCantidad());
            item.setPedido(nuevoPedido);
            nuevoPedido.getItems().add(item);
        }

        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);

        logger.info("Pedido creado exitosamente con ID: {}", pedidoGuardado.getIdPedido());
        return pedidoGuardado;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Pedido> obtenerPedidosPorCliente(Integer idCliente) {
        logger.info("Obteniendo pedidos para cliente con ID: {}", idCliente);
        return pedidoRepository.findByCliente_Id(idCliente);
    }

    private static final String PEDIDO_ID = "Pdido con ID ";
    
    @Override
    @Transactional
    public void actualizarEstadoPedido(Integer idPedido, EstadoPedido nuevoEstado) {
        logger.info("Actualizando estado del pedido {} a: {}", idPedido, nuevoEstado);
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException(PEDIDO_ID + idPedido + " no encontrado."));
        pedido.actualizarEstado(nuevoEstado); // Asume que este método en Pedido valida la transición
        pedidoRepository.save(pedido); // Guarda los cambios
        logger.info("Estado de pedido {} actualizado a {} .", idPedido, nuevoEstado);
    }

    @Override
    @Transactional
    public void asignarRepartidorAPedido(Integer idPedido, Integer idRepartidor) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);
        Optional<Repartidor> repartidorOpt = repartidorRepository.findById(idRepartidor);

        if (pedidoOpt.isEmpty()) {
            throw new IllegalArgumentException(PEDIDO_ID + idPedido + " no encontrado.");
        }

        if (repartidorOpt.isEmpty()) {
            throw new IllegalArgumentException("Repartidor con ID " + idRepartidor + " no encontrado.");
        }

        Pedido pedido = pedidoOpt.get();
        Repartidor repartidor = repartidorOpt.get();

        pedido.setRepartidor(repartidor);
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void cancelarPedido(Integer idPedido, Integer idUsuario) {
        logger.info("Cancelando pedido {} por usuario {}", idPedido, idUsuario);
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException(PEDIDO_ID + idPedido + " no encontrado para cancelar."));
        if (pedido.getCliente() == null) {
            throw new IllegalStateException("El pedido con ID " + idPedido + " no tiene un cliente asociado.");
        }
        if (!pedido.getCliente().getId().equals(idUsuario)) {
            throw new IllegalArgumentException("El usuario " + idUsuario + " no tiene permiso para cancelar el pedido " + idPedido + " (no es el cliente asociado).");
        }
        pedido.actualizarEstado(EstadoPedido.CANCELADO);
        pedidoRepository.save(pedido);
        logger.info("Pedido {} cancelado por usuario {}.", idPedido, idUsuario);
    }

    @Override
    @Transactional
    public void confirmarEntrega(Integer idPedido) {
        logger.info("Confirmando entrega del pedido {}", idPedido);
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException(PEDIDO_ID + idPedido + " no encontrado para confirmar entrega."));
        pedido.actualizarEstado(EstadoPedido.ENTREGADO); // Asume que este método en Pedido actualiza a ENTREGADO
        pedidoRepository.save(pedido);
        logger.info("Pedido {} confirmado como ENTREGADO.", idPedido);
    }

    @Override
    @Transactional(readOnly = true)
    public Pedido obtenerPedidoPorId(Integer idPedido) {
        logger.info("Obteniendo pedido con ID: {}", idPedido);
        // Asegúrate de que la lógica no esté comentada o malformada
        return pedidoRepository.findById(idPedido).orElse(null);
    }
}
