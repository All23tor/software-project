// file: src/main/java/org/unsa/model/domain/restaurantes/Plato.java
package org.unsa.model.domain.restaurantes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Getter
@Setter
@Entity
@Table(name = "platos")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlato;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    private boolean disponible;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "precio_valor"))
    @AttributeOverride(name = "moneda", column = @Column(name = "precio_moneda"))
    private Dinero precio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @Transient
    private static final Logger logger = LoggerFactory.getLogger(Plato.class);

    public Plato() {
        this.disponible = true;
        logger.info("Nuevo plato creado (constructor vacío). ID será asignado por JPA.");
    }

    public Plato(Integer idPlato, String nombre, String descripcion, boolean disponible, Dinero precio) {
        this.idPlato = idPlato;
        if (nombre == null || nombre.trim().isEmpty()) {
            logger.warn("Intento de crear Plato con nombre nulo o vacío.");
            throw new IllegalArgumentException("El nombre del plato no puede ser nulo o vacío.");
        }
        if (precio == null) {
            logger.warn("Intento de crear Plato con precio nulo.");
            throw new IllegalArgumentException("El precio del plato no puede ser nulo.");
        }
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.precio = precio;
        logger.warn("Plato creado (completo) con ID: {} y nombre: {}", this.idPlato, this.nombre);
    }


    public Plato(Restaurante restaurante, String nombre, String descripcion, Dinero precio) {
        if (restaurante == null) throw new IllegalArgumentException("El restaurante no puede ser nulo.");
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre del plato no puede ser nulo o vacío.");
        if (precio == null) throw new IllegalArgumentException("El precio no puede ser nulo.");

        this.restaurante = restaurante;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = true;
        logger.info("Plato creado asociado a restaurante: {}", restaurante.getNombre());
    }

    public Integer getId() {
        return idPlato;
    }

    public void setId(Integer id) {
        if (id != null && id <= 0) {
            logger.warn("ID inválido: {} ", id);
            throw new IllegalArgumentException("El ID debe ser positivo o nulo.");
        }
        this.idPlato = id;
    }

    public void marcarComoDisponible(boolean disponible) {
        this.disponible = disponible;
        logger.info("Plato '{}' marcado como {}", nombre, disponible ? "disponible" : "no disponible");
    }

    public void actualizarPrecio(Dinero nuevoPrecio) {
        if (nuevoPrecio == null) throw new IllegalArgumentException("El nuevo precio no puede ser nulo.");
        this.precio = nuevoPrecio;
        logger.info("Precio actualizado:  {}  {}", nuevoPrecio.getValor(), nuevoPrecio.getMoneda());
    }

    @Override
    public String toString() {
        return "Plato{" + "idPlato=" + idPlato + ", nombre='" + nombre + '\'' + ", descripcion='" + descripcion + '\'' + ", disponible=" + disponible + ", precio=" + (precio != null ? precio.getValor() + " " + precio.getMoneda() : "N/A") + ", restaurante=" + (restaurante != null ? restaurante.getNombre() : "N/A") + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plato plato)) return false;
        return Objects.equals(idPlato, plato.idPlato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlato);
    }
}
