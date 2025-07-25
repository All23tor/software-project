//file : src/main/java/org/unsa/model/repository/PlatoRepository.java

package org.unsa.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unsa.model.domain.restaurantes.Plato;

import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato, Integer> {

    // Lista todos los platos de un restaurante específico
    List<Plato> findByRestauranteId(Integer restauranteId);

    // Lista todos los platos disponibles (disponible = true)
    List<Plato> findByDisponibleTrue();

    // Búsqueda de platos por nombre (parcial e insensible a mayúsculas)
    List<Plato> findByNombreContainingIgnoreCase(String nombre);
}
