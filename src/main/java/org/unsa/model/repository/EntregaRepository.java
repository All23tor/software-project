// file: src/main/java/org/unsa/softwareproject/repository/EntregaRepository.java
package org.unsa.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unsa.model.domain.pedidos.Entrega; // Asegúrate de que esta ruta sea correcta

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
    // Puedes añadir métodos personalizados aquí, por ejemplo:
    // List<Entrega> findByRepartidorId(Integer repartidorId);
    // List<Entrega> findByEstado(EstadoEntrega estado);
    // List<Entrega> findByPedidoId(Integer pedidoId); // Si ItemPedido tiene un campo 'pedidoId'
    // O si Entrega tiene una relación @ManyToOne con Pedido llamada 'pedido':
    // List<Entrega> findByPedido_Id(Integer pedidoId);
}