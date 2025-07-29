//file : src/main/java/org/unsa/model/repository/RestauranteRepository.java

package org.unsa.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unsa.model.domain.restaurantes.Restaurante;
import org.unsa.model.domain.restaurantes.TipoCocina;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {

    // Buscar todos los restaurantes por tipo de cocina
    List<Restaurante> findByTipoCocina(TipoCocina tipoCocina);

    // Buscar restaurante por nombre exacto
    Optional<Restaurante> findByNombre(String nombre);
}
