// file: src/main/java/org/unsa/model/dtos/ActualizarUsuarioRequest.java
package org.unsa.model.dtos;

import lombok.Getter;
import lombok.Setter;
import org.unsa.model.domain.usuarios.Direccion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para la actualizacion de un usuario existente.
 * Los campos son opcionales ya que no todos se actualizaran siempre.
 */
@Setter
@Getter
public class ActualizarUsuarioRequest {
    // Constructor, Getters y Setters
    private String nombre;
    private String apellido;
    @Email(message = "El formato del email no es válido")
    private String email;
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    private String telefono;
    private Direccion direccionPrincipal; // Para actualizar la dirección
    private Boolean activo; // Para activar/desactivar el usuario

    @Override
    public String toString() {
        return "ActualizarUsuarioRequest{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", activo=" + activo +
                '}';
    }
}