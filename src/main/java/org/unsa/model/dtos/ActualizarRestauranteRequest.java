// file: src/main/java/org/unsa/dto/restaurantes/ActualizarRestauranteRequest.java
package org.unsa.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la solicitud de actualizacion de un restaurante existente.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarRestauranteRequest {
    // Getters y Setters
    private String nombre;
    private String direccion;
    private String telefono;
    private String tipoCocina;

}
