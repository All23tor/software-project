package org.unsa.model.domain.usuarios;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private String calle;
    private String ciudad;
    private String distrito;
    private String referencia;

    // Puedes agregar validaciones con @NotNull, @Size, etc.
}
