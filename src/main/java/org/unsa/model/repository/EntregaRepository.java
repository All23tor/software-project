// file: src/main/java/org/unsa/softwareproject/repository/EntregaRepository.java
package org.unsa.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unsa.model.domain.pedidos.Entrega; // Asegúrate de que esta ruta sea correcta

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
}