package org.unsa.model.domain.usuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas Unitarias para la Entidad de Dominio Usuario (Capa de Dominio).
 * Estas pruebas verifican los invariantes y el comportamiento de la lógica de negocio.
 */
public class UsuarioTest {

    private Usuario usuarioBase;
    private static final Integer ID_BASE = 1;
    private static final String NOMBRE_BASE = "Carlos Test";
    private static final String EMAIL_BASE = "carlos@unsa.com";
    private static final String TELEFONO_BASE = "987654321";
    @BeforeEach
    public void setUp() { // Método de setup en JUnit 5 también puede ser público (o simplemente void)
        // Arrange: Inicializar el objeto con datos válidos
        usuarioBase = new Usuario(ID_BASE, NOMBRE_BASE, EMAIL_BASE, TELEFONO_BASE);
    }

    // --- PRUEBAS DE INVARIANTES (Comprobando Excepciones) ---

    /**
     * Test Case 1: Verifica que el invariante de email se cumpla en el constructor.
     * Si el email es inválido (no tiene '@' o es nulo), debe lanzar una excepción.
     */
    @Test
    public void emailInvalidoLanzaExcepcionEnElConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(2, "Test Fallo", "email.invalido.com", "999");
        }, "Debe lanzar IllegalArgumentException si el email no contiene '@'.");

        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(3, "Test Fallo Nulo", null, "999");
        }, "Debe lanzar IllegalArgumentException si el email es nulo.");
    }

    /**
     * Test Case 2: Verifica la validación de email al usar el setter.
     */
    @Test
    public void setEmailInvalidoLanzaExcepcion() {

        assertThrows(IllegalArgumentException.class, () -> {
            usuarioBase.setEmail("nuevo-email-invalido");
        }, "El setter de email debe validar el formato y lanzar excepción.");
    }


    /**
     * Test Case 3: Verifica el comportamiento de desactivarCuenta().
     */
    @Test
    public void desactivarCuentaCambiaElEstadoCorrectamente() {
        // Arrange: El usuario inicia activo por defecto (true)

        // Act: Ejecutamos el comportamiento de la entidad
        usuarioBase.desactivarCuenta();

        // Assert: Verificamos el estado esperado después de la acción
        assertFalse(usuarioBase.isActivo(), "La cuenta debe estar inactiva (false) después de la desactivación.");
    }

    /**
     * Test Case 4: Verifica el comportamiento de actualizarDatosContacto con datos válidos.
     */
    @Test
    public void actualizarDatosContacto_conDatosValidos_actualizaCorrectamente() {
        String nuevoEmail = "carlos.nuevo@example.org";
        String nuevoTelefono = "111222333";

        // Act: Llamamos al método de comportamiento
        usuarioBase.actualizarDatosContacto(nuevoEmail, nuevoTelefono);

        // Assert: Verificamos que los atributos se hayan actualizado
        assertEquals(nuevoEmail, usuarioBase.getEmail(), "El email debe haberse actualizado.");
        assertEquals(nuevoTelefono, usuarioBase.getTelefono(), "El teléfono debe haberse actualizado.");
    }

    /**
     * Test Case 5: Verifica que la actualización de datos falle si el email es inválido.
     */
    @Test
    public void actualizarDatosContacto_conEmailInvalido_noActualizaNada() {
        String emailOriginal = usuarioBase.getEmail();
        String telefonoOriginal = usuarioBase.getTelefono();

        usuarioBase.actualizarDatosContacto("email-invalido", "555555555");

        assertEquals(emailOriginal, usuarioBase.getEmail(), "Si falla la validación del email, el email original debe mantenerse.");
        assertEquals(telefonoOriginal, usuarioBase.getTelefono(), "Si falla la validación del email, el teléfono tampoco debe actualizarse.");
    }
}



