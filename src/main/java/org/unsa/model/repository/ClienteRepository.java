// file: src/main/java/org/unsa/softwareproject/repository/ClienteRepository.java
package org.unsa.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unsa.model.domain.usuarios.Cliente; // Asegúrate de que esta ruta sea correcta

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}