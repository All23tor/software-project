package Model.Dominio.Pedidos;

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
        // TODO implement here
        this.idRepartidor = Integer.parseInt(idRepartidor);
        this.estado = EstadoEntrega.ASIGNADO;
        this.fechaHoraAsignacion = new Date();
    }

    public void actualizarEstado(EstadoEntrega nuevoEstado) {
        // TODO implement here
        if (this.estado == EstadoEntrega.ENTREGADO && nuevoEstado != EstadoEntrega.INCIDENCIA) {
            throw new IllegalStateException("No se puede cambiar el estado de una entrega ya completada");
        }
        this.estado = nuevoEstado;
    }

    public void registrarRecojo() {
        // TODO implement here
        this.estado EstadoEntrega.RECOGIDO;
        this.fechaHoraRecojo = new Date();
    }

    public void actualizarUbicacion(String coordenadas) {
        // TODO implement here
        this.ubicacionActualRepartidor = coordenadas;
    }
}