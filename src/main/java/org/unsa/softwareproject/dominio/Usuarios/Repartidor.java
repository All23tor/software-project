package Model.Dominio.Usuarios;

public class Repartidor extends Usuario {
    private int id;
    private String idUsuarioPlataforma;
    private String vehiculo;
    private String licenciaConducir;
    private boolean disponible;
    private double calificacion;

    public void marcarComoDisponible(boolean disponible) {
        // TODO implement here
    }

    public void actualizarUbicacion(String coordenadas) {
        // TODO implement here
    }

    public void aceptarEntrega(String idEntrega) {
        // TODO implement here
    }

    public void recibirCalificacion(int puntuacion) {
        // TODO implement here
    }

}