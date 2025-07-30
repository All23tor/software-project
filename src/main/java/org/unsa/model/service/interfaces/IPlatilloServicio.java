// file: src/main/java/org/unsa/service/interfaces/IPlatilloServicio.java
package org.unsa.model.service.interfaces;

import org.unsa.model.domain.restaurantes.Plato;
import org.unsa.model.domain.restaurantes.Dinero; // Necesario para crear/actualizar Plato

import java.util.List;

/**
 * Interfaz para el servicio de gestion de platillos.
 * Define las operaciones de negocio relacionadas con los platos de un restaurante.
 */
public interface IPlatilloServicio {
    Plato crearPlatillo(Integer idRestaurante, String nombre, String descripcion, Dinero precio);

    List<Plato> listarPlatillosPorRestaurante(Integer idRestaurante);

    Plato verDetallePlatillo(Integer idPlatillo);

    Plato actualizarPlatillo(Integer idPlatillo, String nombre, String descripcion, Dinero precio, boolean disponible);

    void eliminarPlatillo(Integer idPlatillo);

    void marcarPlatilloComoDisponible(Integer idPlatillo, boolean disponible);
}
