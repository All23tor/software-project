package org.unsa.softwareproject.Dominio.Pedidos;

import Model.Dominio.Restaurantes.Dinero;

public class ItemPedido {
    private int id;
    private int idPlato;
    private String nombrePlato;
    private int cantidad;
    private Dinero precioUnitario;
    private Dinero subtotal;

    public Dinero calcularSubtotal() {
        // TODO implement here
        this.subtotal = precioUnitario.multiplicar(cantidad); //falta crear el metodo multiplicar en dinero
        return this.subtotal;
    }

    public void actualizarCantidad(int nuevaCantidad) {
        // TODO implement here
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