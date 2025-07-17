// file: src/main/java/org/unsa/service/interfaces/IRestauranteServicio.java
package org.unsa.service.interfaces;

import org.unsa.model.dominio.restaurantes.Restaurante;
import org.unsa.model.dominio.restaurantes.TipoCocina;
import org.unsa.model.dominio.restaurantes.HorarioAtencion; // Para actualizar horarios

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
