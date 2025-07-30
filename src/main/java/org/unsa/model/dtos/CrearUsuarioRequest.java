// file: src/main/java/org/unsa/model/dtos/CrearUsuarioRequest.java
package org.unsa.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.unsa.model.domain.usuarios.Direccion;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para la creacion de un nuevo usuario.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CrearUsuarioRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    private String email;
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos") // Ejemplo para 9 dígitos
    private String telefono;

    // Puedes añadir mas campos si tu usuario tiene otros atributos
    // private String tipoUsuario; // "CLIENTE", "REPARTIDOR", "ADMIN"

    // Si la dirección se crea con el usuario principal
    @NotNull(message = "La dirección principal no puede ser nula")
    private Direccion direccionPrincipal;


    @Override
    public String toString() {
        return "CrearUsuarioRequest{" + "nombre='" + nombre + '\'' + ", apellido='" + apellido + '\'' + ", email='" + email + '\'' + ", telefono='" + telefono + '\'' + '}';
    }
}