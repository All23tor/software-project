package org.unsa.softwareproject.dominio.pedidos;

public class Direccion {
    private String calle;
    private int numero;
    private String ciudad;
    private String distrito;
    private String coordenadasGeograficas;

    // Constructor
    public Direccion(String calle, int numero, String ciudad, String distrito, String coordenadasGeograficas) {
        setCalle(calle);
        setNumero(numero);
        setCiudad(ciudad);
        setDistrito(distrito);
        setCoordenadas(coordenadasGeograficas);
    }

    /**
     Verifica que todos los campos de la dirección estén correctamente establecidos.
     **/
    public boolean esValida() {
        return validarCalle() &&
                validarNumero() &&
                validarCiudad() &&
                validarDistrito() &&
                validarCoordenadas();
    }

    // Getters
    public String getCalle() {
        return calle;
    }
    public int getNumero() {
        return numero;
    }
    public String getCiudad() {
        return ciudad;
    }
    public String getDistrito() {
        return distrito;
    }
    public String getCoordenadas() {
        return coordenadasGeograficas;
    }

    // Setters
    public void setCalle(String calle) {
        if (calle == null || calle.trim().isEmpty()) {
            throw new IllegalArgumentException("La calle no puede estar vacía");
        }
        this.calle = calle;
    }
    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El número debe ser mayor a 0");
        }
        this.numero = numero;
    }
    public void setCiudad(String ciudad) {
        if (ciudad == null || ciudad.trim().isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía");
        }
        this.ciudad = ciudad;
    }
    public void setDistrito(String distrito) {
        if (distrito == null || distrito.trim().isEmpty()) {
            throw new IllegalArgumentException("El distrito no puede estar vacío");
        }
        this.distrito = distrito;
    }
    public void setCoordenadas(String coordenadasGeograficas) {
        if (coordenadasGeograficas == null || coordenadasGeograficas.trim().isEmpty()) {
            throw new IllegalArgumentException("Las coordenadas no pueden estar vacías");
        }
        this.coordenadasGeograficas = coordenadasGeograficas;
    }

    // Metodos privados de validacion
    private boolean validarCalle() {
        return calle != null && !calle.trim().isEmpty();
    }
    private boolean validarNumero() {
        return numero > 0;
    }
    private boolean validarCiudad() {
        return ciudad != null && !ciudad.trim().isEmpty();
    }
    private boolean validarDistrito() {
        return distrito != null && !distrito.trim().isEmpty();
    }
    private boolean validarCoordenadas() {
        return coordenadasGeograficas != null && !coordenadasGeograficas.trim().isEmpty();
    }
}