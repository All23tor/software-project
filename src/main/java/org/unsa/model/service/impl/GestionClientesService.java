package org.unsa.model.service.impl;

import org.unsa.model.domain.usuarios.Cliente; // Importación corregida para Cliente

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase de servicio para la gestion de clientes.
 * Contiene la lógica de negocio relacionada con las operaciones de clientes.
 */
public class GestionClientesService {

    private static final Logger logger = Logger.getLogger(GestionClientesService.class.getName());

    /**
     * Constructor por defecto para la clase GestionClientesService.
     */
    public GestionClientesService() {
        logger.info(() -> "Servicio de GestionClientesService inicializado.");
    }

    /**
     * Registra un nuevo cliente en el sistema.
     * En una aplicación real, esto interactuaria con un repositorio de clientes
     * para persistir el objeto Cliente en una base de datos.
     *
     * @param cliente El objeto Cliente a registrar.
     * @throws IllegalArgumentException Si el objeto cliente es nulo.
     */
    public void registrarCliente(Cliente cliente) { // Nombre del metodo corregido a camelCase
        if (cliente == null) {
            logger.log(Level.SEVERE, () -> "Intento de registrar un cliente nulo.");
            throw new IllegalArgumentException("El cliente a registrar no puede ser nulo.");
        }

        logger.info(() -> "Cliente registrado exitosamente (simulacion): " + cliente.getNombre() + " (ID: " + cliente.getId() + ")");
    }
}
