package org.unsa.model.domain.pedidos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unsa.model.domain.restaurantes.Dinero;
import org.unsa.model.domain.restaurantes.Restaurante;
import org.unsa.model.domain.usuarios.Cliente;
import org.unsa.model.domain.usuarios.Direccion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para la clase Pedido.
 * Verifica invariantes del dominio y comportamiento básico.
 */
class PedidoTest {

    private Cliente cliente;
    private Restaurante restaurante;
    private Direccion direccion;
    private ItemPedido itemMock;

    @BeforeEach
    void setUp() {
        cliente = mock(Cliente.class);
        restaurante = mock(Restaurante.class);
        direccion = new Direccion("Calle Falsa", "Arequipa", "Arequipa", "Cerca al parque");
        itemMock = mock(ItemPedido.class);

        when(cliente.getId()).thenReturn(1);
        when(restaurante.getId()).thenReturn(10);
        when(itemMock.getSubtotal()).thenReturn(new Dinero(15.5, "PEN"));
    }

    @Test
    void constructorVacioInicializaCorrectamente() {
        Pedido pedido = new Pedido();

        assertNotNull(pedido.getFechaHoraCreacion());
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertEquals(new Dinero(0.0, "PEN"), pedido.getMontoTotal());
        assertNotNull(pedido.getItems());
        assertTrue(pedido.getItems().isEmpty());
    }

    @Test
    void constructorCompletoConDatosValidosCreaPedido() {
        Pedido pedido = new Pedido(1, cliente, restaurante, direccion, List.of(itemMock), "Sin cebolla");

        assertEquals(cliente, pedido.getCliente());
        assertEquals(restaurante, pedido.getRestaurante());
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertEquals(new Dinero(15.5, "PEN"), pedido.getMontoTotal());
        assertEquals("Sin cebolla", pedido.getInstruccionesEspeciales());
        assertNotNull(pedido.getDireccionEntrega());
        assertEquals(1, pedido.getItems().size());
    }

    @Test
    void constructorLanzaExcepcionSiClienteEsNulo() {
        List<ItemPedido> items = List.of(itemMock);
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new Pedido(1, null, restaurante, direccion, items, null));
        assertEquals("El cliente no puede ser nulo.", ex.getMessage());
    }

    @Test
    void constructorLanzaExcepcionSiRestauranteEsNulo() {
        List<ItemPedido> items = List.of(itemMock);
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new Pedido(1, cliente, null, direccion, items, null));
        assertEquals("El restaurante no puede ser nulo.", ex.getMessage());
    }

    @Test
    void constructorLanzaExcepcionSiDireccionEsNula() {
        List<ItemPedido> items = List.of(itemMock);
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new Pedido(1, cliente, restaurante, null, items, null));
        assertEquals("La direccion de entrega no puede ser nula.", ex.getMessage());
    }

    @Test
    void constructorLanzaExcepcionSiListaDeItemsEsNulaOVacia() {
        Exception ex1 = assertThrows(IllegalArgumentException.class,
                () -> new Pedido(1, cliente, restaurante, direccion, null, null));
        assertEquals("La lista de items no puede ser nula o vacia.", ex1.getMessage());

        List<ItemPedido> emptyList = List.of();
        Exception ex2 = assertThrows(IllegalArgumentException.class,
                () -> new Pedido(1, cliente, restaurante, direccion, emptyList, null));
        assertEquals("La lista de items no puede ser nula o vacia.", ex2.getMessage());
    }

    @Test
    void calcularMontoTotalIgnoraItemsConSubtotalNulo() {
        ItemPedido item1 = mock(ItemPedido.class);
        ItemPedido item2 = mock(ItemPedido.class);
        when(item1.getSubtotal()).thenReturn(null);
        when(item2.getSubtotal()).thenReturn(new Dinero(20.0, "PEN"));

        Pedido pedido = new Pedido(2, cliente, restaurante, direccion, List.of(item1, item2), null);

        assertEquals(new Dinero(20.0, "PEN"), pedido.getMontoTotal());
    }

    @Test
    void actualizarEstadoCambiaElEstadoDelPedido() {
        Pedido pedido = new Pedido(3, cliente, restaurante, direccion, List.of(itemMock), null);
        pedido.actualizarEstado(EstadoPedido.ENTREGADO);

        assertEquals(EstadoPedido.ENTREGADO, pedido.getEstado());
    }

    @Test
    void equalsYHashCodeBasadosEnId() {
        Pedido p1 = new Pedido(1, cliente, restaurante, direccion, List.of(itemMock), null);
        Pedido p2 = new Pedido(1, cliente, restaurante, direccion, List.of(itemMock), null);
        Pedido p3 = new Pedido(2, cliente, restaurante, direccion, List.of(itemMock), null);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
