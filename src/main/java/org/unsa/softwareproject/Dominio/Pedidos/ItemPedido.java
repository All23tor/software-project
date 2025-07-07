package org.unsa.softwareproject.Dominio.Pedidos;

import Model.Dominio.Restaurantes.Dinero;

public class ItemPedido {
    private int id;
    private int idPlato;
    private String nombrePlato;
    private int cantidad;
    public Dinero precioUnitario;
    private Dinero subtotal;

    public Dinero calcularSubtotal() {
        // TODO implement here
        this.subtotal = precioUnitario.multiplicar(cantidad) //falta crear el metodo multiplicar en dinero
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

}