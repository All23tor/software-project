package org.unsa.model.domain.usuarios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas Unitarias para la Entidad de Dominio Usuario (Capa de Dominio).
 * Estas pruebas verifican los invariantes y el comportamiento de la lógica de negocio.
 * (Integrante 4: Fase A - Actividad 7 del Laboratorio).
 */
public class UsuarioTest {

    // Instancia de la entidad que usaremos como base para las pruebas
    private Usuario usuarioBase;
    private static final Integer ID_BASE = 1;
    private static final String NOMBRE_BASE = "Carlos Test";
    private static final String EMAIL_BASE = "carlos@unsa.com";
    private static final String TELEFONO_BASE = "987654321";

    /**
     * Se ejecuta antes de cada método de prueba (@Test) para asegurar un objeto limpio.
     */
    @BeforeEach
    void setUp() {
        // Arrange: Inicializar el objeto con datos válidos
        usuarioBase = new Usuario(ID_BASE, NOMBRE_BASE, EMAIL_BASE, TELEFONO_BASE);
    }

    // --- PRUEBAS DE INVARIANTES (Comprobando Excepciones) ---

    /**
     * Test Case 1: Verifica que el invariante de email se cumpla en el constructor.
     * Si el email es inválido (no tiene '@' o es nulo), debe lanzar una excepción.
     */
    @Test
    void emailInvalidoLanzaExcepcionEnElConstructor() {
        // Test 1.1: Email sin '@'
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(2, "Test Fallo", "email.invalido.com", "999");
        }, "Debe lanzar IllegalArgumentException si el email no contiene '@'.");

        // Test 1.2: Email nulo
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(3, "Test Fallo Nulo", null, "999");
        }, "Debe lanzar IllegalArgumentException si el email es nulo.");
    }

    /**
     * Test Case 2: Verifica la validación de email al usar el setter.
     */
    @Test
    void setEmailInvalidoLanzaExcepcion() {
        // Arrange: Usuario base creado en setUp()

        // Assert: Esperamos que se lance la excepción al intentar actualizar el email
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioBase.setEmail("nuevo-email-invalido");
        }, "El setter de email debe validar el formato y lanzar excepción.");
    }


    /**
     * Test Case 3: Verifica el comportamiento de desactivarCuenta().
     */
    @Test
    void desactivarCuentaCambiaElEstadoCorrectamente() {
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
    void actualizarDatosContacto_conDatosValidos_actualizaCorrectamente() {
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
    void actualizarDatosContacto_conEmailInvalido_noActualizaNada() {
        String emailOriginal = usuarioBase.getEmail();
        String telefonoOriginal = usuarioBase.getTelefono();

        // Act: Llamamos al método con un email inválido
        usuarioBase.actualizarDatosContacto("email-invalido", "555555555");

        // Assert: Verificamos que los datos ORIGINALES se mantengan
        assertEquals(emailOriginal, usuarioBase.getEmail(), "Si falla la validación del email, el email original debe mantenerse.");
        assertEquals(telefonoOriginal, usuarioBase.getTelefono(), "Si falla la validación del email, el teléfono tampoco debe actualizarse.");
    }
}
