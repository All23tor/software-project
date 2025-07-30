// file: src/main/java/org/unsa/service/impl/GestionPlatillosService.java
package org.unsa.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.unsa.model.domain.restaurantes.Plato;
import org.unsa.model.domain.restaurantes.Dinero;
import org.unsa.model.repository.PlatoRepository;
import org.unsa.model.repository.RestauranteRepository;
import org.unsa.model.service.interfaces.IPlatilloServicio;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class GestionPlatillosService implements IPlatilloServicio {

    private static final Logger logger = LoggerFactory.getLogger(GestionPlatillosService.class);

    private final PlatoRepository platoRepository;

    private final RestauranteRepository restauranteRepository;

    @Autowired
    public GestionPlatillosService(PlatoRepository platoRepository, RestauranteRepository restauranteRepository) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
        logger.info("Servicio de GestionPlatillosService inicializado con repositorios.");
    }

    @Override
    public Plato crearPlatillo(Integer idRestaurante, String nombre, String descripcion, Dinero precio) {
        var restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> {
                    logger.warn("Restaurante con ID {} no encontrado", idRestaurante);
                    return new IllegalArgumentException("Restaurante no encontrado");
                });

        var plato = new Plato(0, nombre, descripcion, true, precio);
        plato.setRestaurante(restaurante);

        Plato guardado = platoRepository.save(plato);
        logger.info("Platillo creado con ID {} para restaurante {}", guardado.getIdPlato(), idRestaurante);
        return guardado;
    }

    @Override
    public List<Plato> listarPlatillosPorRestaurante(Integer idRestaurante) {
        List<Plato> platos = platoRepository.findByRestauranteId(idRestaurante);
        logger.info("Se listaron {} platillos para el restaurante {}", platos.size(), idRestaurante);
        return platos;
    }

    private static final String NO_ENCONTRADO = "Platillo no encontrado";

    @Override
    public Plato verDetallePlatillo(Integer idPlatillo) {
        return platoRepository.findById(idPlatillo)
                .orElseThrow(() -> {
                    logger.warn("Platillo con ID {} no encontrado", idPlatillo);
                    return new IllegalArgumentException(NO_ENCONTRADO);
                });
    }

    @Override
    public Plato actualizarPlatillo(Integer idPlatillo, String nombre, String descripcion, Dinero precio, boolean disponible) {
        var plato = platoRepository.findById(idPlatillo)
                .orElseThrow(() -> {
                    logger.warn("Platillo con ID {} no encontrado", idPlatillo);
                    return new IllegalArgumentException(NO_ENCONTRADO);
                });

        plato.setNombre(nombre);
        plato.setDescripcion(descripcion);
        plato.setPrecio(precio);
        plato.setDisponible(disponible);

        Plato actualizado = platoRepository.save(plato);
        logger.info("Platillo ID {} actualizado: nombre='{}', disponible={}", idPlatillo, nombre, disponible);
        return actualizado;
    }

    @Override
    public void eliminarPlatillo(Integer idPlatillo) {
        if (!platoRepository.existsById(idPlatillo)) {
            logger.warn("Intento de eliminar platillo inexistente con ID {}", idPlatillo);
            throw new IllegalArgumentException(NO_ENCONTRADO);
        }

        platoRepository.deleteById(idPlatillo);
        logger.info("Platillo con ID {} eliminado", idPlatillo);
    }

    @Override
    public void marcarPlatilloComoDisponible(Integer idPlatillo, boolean disponible) {
        var plato = platoRepository.findById(idPlatillo)
                .orElseThrow(() -> {
                    logger.warn("Platillo con ID {} no encontrado al marcar disponibilidad", idPlatillo);
                    return new IllegalArgumentException(NO_ENCONTRADO);
                });

        plato.setDisponible(disponible);
        platoRepository.save(plato);
        logger.info("Platillo '{}' marcado como {}", plato.getNombre(), disponible ? "disponible" : "no disponible");
    }
}