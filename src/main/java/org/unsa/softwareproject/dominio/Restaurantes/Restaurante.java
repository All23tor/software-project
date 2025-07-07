package Model.Dominio.Restaurantes;

import Model.Dominio.Usuarios.Administrador;

import java.util.List;

public class Restaurante {
    private int id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String telefono;
    private TipoCocina tipoCocina;
    private double calificacionPromedio;
    private HorarioAtencion horarioAtencion;
    private List<Plato> platos;
    public Administrador admin;

    public void añadirPlato(Plato plato) {
        // TODO implement here
    }

    public void eliminarPlato(int id) {
        // TODO implement here
    }

    public void actualizarHorario(HorarioAtencion horario) {
        // TODO implement here
    }

    public boolean estaDisponible() {
        // TODO implement here
        return false;
    }

}