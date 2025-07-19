package org.unsa.softwareproject.dominio.pedidos;

import org.unsa.softwareproject.dominio.Restaurantes.Dinero;

public class ItemPedido {
    private int id;
    private int idPlato;
    private String nombrePlato;
    private int cantidad;
    private Dinero precioUnitario;
    private Dinero subtotal;

    public Dinero calcularSubtotal() {
        validarCantidad();
        this.subtotal = precioUnitario.multiplicar(cantidad);
        return this.subtotal;
    }

    private void validarCantidad() {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
    }


    public void actualizarCantidad(int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.cantidad = nuevaCantidad;
        this.calcularSubtotal();
    }

    public int getId() {
        return id;
    }

    public int getIdPlato() {
        return idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Dinero getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Dinero precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Dinero getSubtotal() {
        return subtotal;
    }
}