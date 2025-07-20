package org.unsa.softwareproject.dominio.pedidos;

import org.unsa.softwareproject.dominio.Restaurantes.Dinero;

public class ItemPedido {
    private final int id;
    private final int idPlato;
    private String nombrePlato;
    private int cantidad;
    private Dinero precioUnitario;
    private Dinero subtotal;

    // Constructor
    public ItemPedido(int id, int idPlato, String nombrePlato, int cantidad, Dinero precioUnitario) {
        this.id = id;
        this.idPlato = idPlato;
        setNombrePlato(nombrePlato);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        calcularSubtotal();
    }
    /**
     * Calcula el subtotal del ítem basado en cantidad y precio unitario.
     */
    public Dinero calcularSubtotal() {
        validarCantidad();
        this.subtotal = precioUnitario.multiplicar(cantidad);
        return this.subtotal;
    }
    /**
     * Actualiza la cantidad del ítem y recalcula el subtotal.
     * @param nuevaCantidad cantidad nueva a asignar
     */
    public void actualizarCantidad(int nuevaCantidad) {
        setCantidad(nuevaCantidad);
        calcularSubtotal();
    }

    // Getter
    public int getId() {
        return id;
    }
    public int getIdPlato() {
        return idPlato;
    }
    public String getNombrePlato() {
        return nombrePlato;
    }
    public int getCantidad() {
        return cantidad;
    }
    public Dinero getPrecioUnitario() {
        return precioUnitario;
    }
    public Dinero getSubtotal() {
        return subtotal;
    }

    // Setter
    public void setNombrePlato(String nombrePlato) {
        if (nombrePlato == null || nombrePlato.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plato no puede estar vacío");
        }
        this.nombrePlato = nombrePlato;
    }
    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.cantidad = cantidad;
    }
    public void setPrecioUnitario(Dinero precioUnitario) {
        if (precioUnitario == null || precioUnitario.getMonto() <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser válido y mayor a 0");
        }
        this.precioUnitario = precioUnitario;
    }

    // Metodo privado de validacion
    private void validarCantidad() {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
    }
}