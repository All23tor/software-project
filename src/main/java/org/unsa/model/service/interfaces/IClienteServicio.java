// file: src/main/java/org/unsa/model/service/Interfaces/IClienteServicio.java
package org.unsa.model.service.interfaces;

import org.unsa.model.domain.usuarios.Cliente;

import java.util.List;

/**
 * Interfaz para el servicio de gestion de clientes.
 * Define las operaciones de negocio relacionadas con los clientes.
 */
public interface IClienteServicio {
    void registrarCliente(Cliente cliente);

    Cliente obtenerClientePorId(String idCliente);

    List<Cliente> obtenerTodosClientes();

    Cliente actualizarCliente(String idCliente, String nombre, String email, String telefono, String preferenciasAlimentarias);

    void eliminarCliente(String idCliente);
}
