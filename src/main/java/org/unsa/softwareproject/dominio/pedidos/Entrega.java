package org.unsa.softwareproject.dominio.pedidos;

import java.util.Date;

public class Entrega {
    private final int id;
    private final int idPedido;
    private int idRepartidor;
    private EstadoEntrega estado;
    private Date fechaHoraAsignacion;
    private Date fechaHoraRecojo;
    private Date fechaHoraEntrega;
    private String ubicacionActualRepartidor;

    // Constructor
    public Entrega(int id, int idPedido) {
        this.id = id;
        this.idPedido = idPedido;
        this.estado = EstadoEntrega.PENDIENTE;
    }

    // Metodos publicos
    public void asignarRepartidor(String idRepartidor) {
        if (idRepartidor == null || !idRepartidor.matches("\\d+")) {
            throw new IllegalArgumentException("El ID del repartidor debe ser un número válido.");
        }
        this.idRepartidor = Integer.parseInt(idRepartidor);
        this.estado = EstadoEntrega.ASIGNADO;
        this.fechaHoraAsignacion = new Date();
    }

    /**
     * Actualiza el estado de la entrega, validando que el nuevo estado sea válido
     * y que no se modifique una entrega ya completada.
     */
    public void actualizarEstado(EstadoEntrega nuevoEstado) {
        validarCambioDeEstado(nuevoEstado);
        this.estado = nuevoEstado;
    }
    public void registrarRecojo() {
        this.estado = EstadoEntrega.RECOGIDO;
        this.fechaHoraRecojo = new Date();
    }
    public void actualizarUbicacion(String coordenadas) {
        this.ubicacionActualRepartidor = coordenadas;
    }

    // Getters
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

    // Metodos Privados
    private void validarCambioDeEstado(EstadoEntrega nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser nulo.");
        }
        if (this.estado == EstadoEntrega.ENTREGADO && nuevoEstado != EstadoEntrega.INCIDENCIA) {
            throw new IllegalStateException("No se puede cambiar el estado de una entrega ya completada");
        }
    }
}