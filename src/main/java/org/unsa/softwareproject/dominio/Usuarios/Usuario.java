package Model.Dominio.Usuarios;

import java.util.Date;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    public Date fechaRegistro;
    public boolean activo;

    public void actualizarDatosContacto(String email, String telefono) {
        // TODO implement here
    }

    public void activarCuenta() {
        // TODO implement here
    }

    public void desactivarCuenta() {
        // TODO implement here
    }

}