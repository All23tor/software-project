// file: src/main/java/org/unsa/service/interfaces/IRestauranteServicio.java
package org.unsa.model.service.interfaces;

import org.unsa.model.domain.restaurantes.Restaurante;
import org.unsa.model.domain.restaurantes.TipoCocina;
import org.unsa.model.domain.restaurantes.HorarioAtencion; // Para actualizar horarios

import java.util.List;

/**
 * Interfaz para el servicio de gestion de restaurantes.
 * Define las operaciones de negocio relacionadas con los restaurantes.
 */
public interface IRestauranteServicio {
    Restaurante registrarRestaurante(String nombre, String direccion, String telefono, TipoCocina tipoCocina);

    List<Restaurante> buscarRestaurantes(String query);

    List<Restaurante> obtenerTodosRestaurantes();

    Restaurante verDetalleRestaurante(String idRestaurante);

    Restaurante actualizarRestaurante(String idRestaurante, String nombre, String direccion, String telefono, TipoCocina tipoCocina);

    void eliminarRestaurante(String idRestaurante);

    void actualizarHorarioRestaurante(String idRestaurante, HorarioAtencion horario);
}
