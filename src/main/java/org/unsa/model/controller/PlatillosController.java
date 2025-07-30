// file: src/main/java/org/unsa/controller/PlatillosController.java
package org.unsa.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unsa.model.domain.restaurantes.Plato;
import org.unsa.model.dtos.CrearPlatilloRequest;
import org.unsa.model.dtos.ActualizarPlatilloRequest;
import org.unsa.model.service.interfaces.IPlatilloServicio;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes/{idRestaurante}/platillos")
public class PlatillosController {

    private final IPlatilloServicio platilloServicio;

    @Autowired
    public PlatillosController(IPlatilloServicio platilloServicio) {
        this.platilloServicio = platilloServicio;
    }

    // Crear un nuevo platillo
    @PostMapping
    public ResponseEntity<Plato> crearPlatillo(@PathVariable Integer idRestaurante, @RequestBody CrearPlatilloRequest request) {
        Plato nuevo = platilloServicio.crearPlatillo(idRestaurante, request.getNombre(), request.getDescripcion(), request.getPrecio());
        return ResponseEntity.ok(nuevo);
    }

    // Listar todos los platillos de un restaurante
    @GetMapping
    public ResponseEntity<List<Plato>> listarPlatillos(@PathVariable Integer idRestaurante) {
        return ResponseEntity.ok(platilloServicio.listarPlatillosPorRestaurante(idRestaurante));
    }

    // Ver detalle de un platillo específico
    @GetMapping("/{idPlatillo}")
    public ResponseEntity<Plato> verDetalle(@PathVariable Integer idPlatillo) {
        return ResponseEntity.ok(platilloServicio.verDetallePlatillo(idPlatillo));
    }

    // Actualizar platillo
    @PutMapping("/{idPlatillo}")
    public ResponseEntity<Plato> actualizarPlatillo(@PathVariable Integer idPlatillo, @RequestBody ActualizarPlatilloRequest request) {
        Plato actualizado = platilloServicio.actualizarPlatillo(idPlatillo, request.getNombre(), request.getDescripcion(), request.getPrecio(), request.isDisponible());
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar platillo
    @DeleteMapping("/{idPlatillo}")
    public ResponseEntity<Void> eliminarPlatillo(@PathVariable Integer idPlatillo) {
        platilloServicio.eliminarPlatillo(idPlatillo);
        return ResponseEntity.noContent().build();
    }

    // Marcar platillo como disponible o no disponible
    @PatchMapping("/{idPlatillo}/disponible")
    public ResponseEntity<Void> marcarDisponible(@PathVariable Integer idPlatillo, @RequestParam boolean disponible) {
        platilloServicio.marcarPlatilloComoDisponible(idPlatillo, disponible);
        return ResponseEntity.ok().build();
    }
}
