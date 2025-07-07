package org.unsa.softwareproject.dominio.pedidos;

import Model.Dominio.Restaurantes.Dinero;
import Model.Dominio.Restaurantes.Restaurante;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int id;
    private int idCliente;
    private int idRestaurante;
    private Date fechaHoraCreacion;
    private EstadoPedido estado;
    private Dinero montoTotal;
    private String instruccionesEspeciales;
    private Direccion direccionEntrega;
    private Restaurante restaurante;
    private List<ItemPedido> items;
    private int idRepartidor;

    public void añadirItem(ItemPedido item) {
        // TODO implement here
        if (item == null) {
            throw new IllegalArgumentException("El ítem no puede ser nulo");
        }
        this.items.add(item);
        this.montoTotal = this.calcularMontoTotal();
    }

    public void actualizarEstado(EstadoPedido nuevoEstado) {
        // TODO implement here
        if (this.estado == EstadoPedido.ENTREGADO || this.estado == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("No se puede actualizar un pedido finalizado.");
        }
        this.estado = nuevoEstado;
    }

    public Dinero calcularMontoTotal() {
        // TODO implement here
        Dinero total = new Dinero(0.0, "PEN"); //constructor de dinero
        for (ItemPedido item : items) {
            total = total.sumar(item.calcularSubtotal());
        }
        this.montoTotal = total;
        return total;
    }

    public void asignarRepartidor(int idRepartidor) {
        // TODO implement here
        if (this.estado != EstadoPedido.LISTO_PARA_RECOGER) {
            throw new IllegalArgumentException("No se puede asignar repartidor. Estado actual." + this.estado);
        }

        if (idRepartidor <= 0) {
            throw new IllegalArgumentException("El id del repartidor debe ser positivo.");
        }

        this.idRepartidor = idRepartidor;

        this.estado = EstadoPedido.EN_CAMINO;
    }

    public void cancelar() {
        // TODO implement here
        if (this.estado == EstadoPedido.ENTREGADO){
            throw new IllegalStateException("No se puede cancelar un pedido entregado.");
        }
        this.estado = EstadoPedido.CANCELADO;
    }

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public Dinero getMontoTotal() {
        return montoTotal;
    }

    public String getInstruccionesEspeciales() {
        return instruccionesEspeciales;
    }

    public Direccion getDireccionEntrega() {
        return direccionEntrega;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public List<ItemPedido> getItems() {
        return new ArrayList<>(items);


    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo");
        }
        this.id = id;
    }

    public void setIdCliente(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser positivo");
        }
        this.idCliente = idCliente;
    }

    public void setIdRestaurante(int idRestaurante) {
        if (idRestaurante <= 0) {
            throw new IllegalArgumentException("El ID del restaurante debe ser positivo");
        }
        this.idRestaurante = idRestaurante;
    }

    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        if (fechaHoraCreacion == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula");
        }
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public void setEstado(EstadoPedido estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = estado;
    }

    public void setMontoTotal(Dinero montoTotal) {
        if (montoTotal == null) {
            throw new IllegalArgumentException("El monto no puede ser nulo");
        }
        this.montoTotal = montoTotal;
    }

    public void setInstruccionesEspeciales(String instruccionesEspeciales) {
        this.instruccionesEspeciales = instruccionesEspeciales;
    }

    public void setDireccionEntrega(Direccion direccionEntrega) {
        if (direccionEntrega == null) {
            throw new IllegalArgumentException("La dirección no puede ser nula");
        }
        this.direccionEntrega = direccionEntrega;
    }

    public void setRestaurante(Restaurante restaurante) {
        if (restaurante == null) {
            throw new IllegalArgumentException("El restaurante no puede ser nulo");
        }
        this.restaurante = restaurante;
    }

    public void setItems(List<ItemPedido> items) {
        if (items == null) {
            throw new IllegalArgumentException("La lista de ítems no puede ser nula");
        }
        this.items = new ArrayList<>(items);
    }

    public void setIdRepartidor(int idRepartidor) {
        if (idRepartidor <= 0) {
            throw new IllegalArgumentException("El ID del repartidor debe ser positivo");
        }
        this.idRepartidor = idRepartidor;
    }
}