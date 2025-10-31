package org.unsa.model.domain.pedidos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.unsa.model.domain.usuarios.Cliente;
import org.unsa.model.domain.usuarios.Direccion;
import org.unsa.model.domain.restaurantes.Restaurante;
import org.unsa.model.domain.usuarios.Repartidor;
import org.unsa.model.repository.ClienteRepository;
import org.unsa.model.repository.PedidoRepository;
import org.unsa.model.repository.RestauranteRepository;
import org.unsa.model.repository.RepartidorRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoManagerTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RepartidorRepository repartidorRepository;

    @InjectMocks
    private PedidoManager pedidoManager;

    // PRUEBA UNITARIA 1: Crear pedido exitoso
    @Test
    void testCrearNuevoPedido_Exitoso() {
        // Preaparar datos de prueba y mocks(arrage)
        Integer idCliente = 1;
        Integer idRestaurante = 1;
        Direccion direccionEntrega = new Direccion();
        String instrucciones = "Sin picante";
        
        Cliente clienteMock = new Cliente();
        clienteMock.setId(idCliente);
        
        Restaurante restauranteMock = new Restaurante();
        restauranteMock.setId(idRestaurante);
        
        Pedido pedidoGuardado = new Pedido();
        pedidoGuardado.setIdPedido(100);
        pedidoGuardado.setEstado(EstadoPedido.PENDIENTE);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteMock));
        when(restauranteRepository.findById(idRestaurante)).thenReturn(Optional.of(restauranteMock));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        // Ejecutar el metodo de prueba (act)
        Pedido resultado = pedidoManager.crearNuevoPedido(idCliente, idRestaurante, direccionEntrega, instrucciones);

        // Verificar resultados (asser)
        assertNotNull(resultado);
        assertEquals(100, resultado.getIdPedido());
        assertEquals(EstadoPedido.PENDIENTE, resultado.getEstado());
        verify(clienteRepository, times(1)).findById(idCliente);
        verify(restauranteRepository, times(1)).findById(idRestaurante);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    // PRUEBA UNITARIA 2: Cliente no encontrado
    @Test
    void testCrearNuevoPedido_ClienteNoEncontrado() {
        // Arrange
        Integer idCliente = 999;
        Integer idRestaurante = 1;
        Direccion direccionEntrega = new Direccion();
        String instrucciones = "Sin picante";

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoManager.crearNuevoPedido(idCliente, idRestaurante, direccionEntrega, instrucciones);
        });

        assertEquals("Cliente no encontrado con ID: 999", exception.getMessage());
        verify(clienteRepository, times(1)).findById(idCliente);
        verify(restauranteRepository, never()).findById(any());
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    // PRUEBA UNITARIA 3: Obtener pedido por ID exitoso
    @Test
    void testObtenerPedidoPorId_Exitoso() {
        // Arrange
        Integer idPedido = 100;
        Pedido pedidoMock = new Pedido();
        pedidoMock.setIdPedido(idPedido);
        pedidoMock.setEstado(EstadoPedido.PENDIENTE);

        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedidoMock));

        // Act
        Pedido resultado = pedidoManager.obtenerPedidoPorId(idPedido);

        // Assert
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getIdPedido());
        assertEquals(EstadoPedido.PENDIENTE, resultado.getEstado());
        verify(pedidoRepository, times(1)).findById(idPedido);
    }

    // PRUEBA UNITARIA 4: Actualizar estado de pedido
    @Test
    void testActualizarEstadoPedido_Exitoso() {
        // Arrange
        Integer idPedido = 100;
        EstadoPedido nuevoEstado = EstadoPedido.EN_PREPARACION;
        
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setIdPedido(idPedido);
        pedidoExistente.setEstado(EstadoPedido.PENDIENTE);

        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedidoExistente));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoExistente);

        // Act
        pedidoManager.actualizarEstadoPedido(idPedido, nuevoEstado);

        // Assert
        assertEquals(nuevoEstado, pedidoExistente.getEstado());
        verify(pedidoRepository, times(1)).findById(idPedido);
        verify(pedidoRepository, times(1)).save(pedidoExistente);
    }
}