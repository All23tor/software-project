// file: src/main/java/org/unsa/model/dtos/CrearRestauranteRequest.java
package org.unsa.model.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unsa.model.domain.restaurantes.TipoCocina;
import org.unsa.model.domain.usuarios.Direccion; // Asegúrate de la ruta de Direccion
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class CrearRestauranteRequest {
    @NotBlank(message = "El nombre del restaurante no puede estar vacío.")
    private String nombre;
    private String descripcion;
    @NotNull(message = "La dirección no puede ser nula.")
    private Direccion direccion; // Asumiendo que Direccion es un objeto de valor/embeddable
    @Pattern(regexp = "^\\d{9}$", message = "El teléfono debe tener 9 dígitos.")
    private String telefono;
    @NotNull(message = "El tipo de cocina no puede ser nulo.")
    private TipoCocina tipoCocina;
}