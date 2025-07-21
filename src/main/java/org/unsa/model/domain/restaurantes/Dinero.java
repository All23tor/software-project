// file: src/main/java/org/unsa/model/domain/restaurantes/Dinero.java
package org.unsa.model.domain.restaurantes;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Dinero {
    private double valor;
    private String moneda;

    public Dinero() {}

    public Dinero(double valor, String moneda) {
        this.valor = valor;
        this.moneda = moneda;
    }
}
