package Model.Dominio.Pedidos;

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

}