//file : src/main/java/org/unsa/model/dominio/Pedidos/Direccion.java

package org.unsa.model.domain.pedidos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Direccion {
    private String calle;
    private Integer numero;
    private String ciudad;
    private String distrito;
    private String coordenadas;

    public boolean esValida() {
        return calle != null && !calle.isEmpty() && numero > 0 && ciudad != null && distrito != null && coordenadas != null;
    }
}