package org.unsa.softwareproject.dominio.pedidos;

import java.util.Date;

public class Entrega {
    private int id;
    private int idPedido;
    private int idRepartidor;
    private EstadoEntrega estado;
    private Date fechaHoraAsignacion;
    private Date fechaHoraRecojo;
    private Date fechaHoraEntrega;
    private String ubicacionActualRepartidor;

    public void asignarRepartidor(String idRepartidor) {
        this.idRepartidor = Integer.parseInt(idRepartidor);
        this.estado = EstadoEntrega.ASIGNADO;
        this.fechaHoraAsignacion = new Date();
    }

    public void actualizarEstado(EstadoEntrega nuevoEstado) {
        if (this.estado == EstadoEntrega.ENTREGADO && nuevoEstado != EstadoEntrega.INCIDENCIA) {
            throw new IllegalStateException("No se puede cambiar el estado de una entrega ya completada");
        }
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo.");
        }
        this.estado = nuevoEstado;
    }

    public void registrarRecojo() {
        this.estado = EstadoEntrega.RECOGIDO;
        this.fechaHoraRecojo = new Date();
    }

    public void actualizarUbicacion(String coordenadas) {
        this.ubicacionActualRepartidor = coordenadas;
    }

    public int getId() {
        return id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public EstadoEntrega getEstado() {
        return estado;
    }

    public Date getFechaHoraAsignacion() {
        return fechaHoraAsignacion;
    }

    public Date getFechaHoraRecojo() {
        return fechaHoraRecojo;
    }

    public Date getFechaHoraEntrega() {
        return fechaHoraEntrega;
    }

    public String getUbicacionActualRepartidor() {
        return ubicacionActualRepartidor;
    }
}