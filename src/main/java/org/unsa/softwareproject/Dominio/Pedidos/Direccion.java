package org.unsa.softwareproject.Dominio.Pedidos;

public class Direccion {
    private String calle;
    private int numero;
    private String ciudad;
    private String distrito;
    private String coordenadas;

    //public Pedido 1;

    public boolean esValida() {
        // TODO implement here
        return calle != null && !calle.isEmpty()
            && numero > 0
            && ciudad != null
            && distrito != null;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}