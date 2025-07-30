// file: src/main/java/org/unsa/dto/pedidos/CrearPedidoRequest.java
package org.unsa.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unsa.model.domain.usuarios.Direccion;
import org.unsa.model.domain.pedidos.DatosPlatoPedido; // Asegurarse de importar DatosPlatoPedido

import java.util.List;

/**
 * DTO para la solicitud de creacion de un nuevo pedido.
 * Contiene los datos necesarios para crear un pedido desde el cliente.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrearPedidoRequest {
    // Cambiado a int
    // Getters y Setters
    private Integer idCliente; // Cambiado a int
    // Cambiado a int
    private Integer idRestaurante; // Cambiado a int
    private List<DatosPlatoPedido> itemsCarrito;
    private Direccion direccionEntrega;
    private String instruccionesEspeciales;
}
