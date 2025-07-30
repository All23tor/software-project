// file: src/main/java/org/unsa/model/dominio/restaurantes/HorarioAtencion.java
package org.unsa.model.domain.restaurantes;

import jakarta.persistence.Embeddable; // Importar la anotacion Embeddable
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Transient; // Para campos no persistentes
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa el horario de atencion de un Restaurante para un dia especifico.
 * Utiliza las nuevas APIs de fecha y hora de Java (java.time).
 */
@Embeddable // Marca esta clase como un componente incrustable
public class HorarioAtencion {
    @Enumerated(EnumType.STRING) // Almacena el enum como String en la DB
    private DayOfWeek diaSemana;
    private LocalTime horaApertura;
    private LocalTime horaCierre;

    @Transient // Indica que este campo no se mapeara a la base de datos
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    @Transient // Indica que este campo no se mapeara a la base de datos
    private static final Logger logger = Logger.getLogger(HorarioAtencion.class.getName());

    /**
     * Constructor vacío para JPA.
     */
    public HorarioAtencion() {} // Necesario para JPA

    /**
     * Constructor para la clase HorarioAtencion.
     * @param diaSemana Nombre del dia de la semana en español (ej. "Lunes").
     * @param horaApertura Hora de apertura en formato HH:mm (ej. "09:00").
     * @param horaCierre Hora de cierre en formato HH:mm (ej. "22:00").
     * @throws IllegalArgumentException Si algun parametro es nulo, el dia es invalido,
     * o el formato de hora es incorrecto.
     */
    public HorarioAtencion(String diaSemana, String horaApertura, String horaCierre) {
        if (diaSemana == null || horaApertura == null || horaCierre == null) {
            logger.log(Level.SEVERE, () -> "Intento de crear HorarioAtencion con parametros nulos.");
            throw new IllegalArgumentException("Ninguno de los parametros puede ser nulo.");
        }

        try {
            this.diaSemana = parseDia(diaSemana);
            this.horaApertura = LocalTime.parse(horaApertura, FORMATO_HORA);
            this.horaCierre = LocalTime.parse(horaCierre, FORMATO_HORA);

            if (this.horaCierre.isBefore(this.horaApertura) && !this.horaCierre.equals(this.horaApertura)) {
                logger.log(Level.INFO, () -> "Horario de atencion cruza la medianoche: " + this.toString());
            } else if (this.horaCierre.equals(this.horaApertura)) {
                logger.log(Level.WARNING, () -> "Horario de atencion con hora de apertura igual a hora de cierre: " + this.toString());
            }

            logger.info(() -> "HorarioAtencion creado: " + this.toString());

        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de hora invalido. Use HH:mm. Detalles: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw e; // Re-lanzar la excepcion de dia invalido
        }
    }

    // --- Getters ---
    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    /**
     * Verifica si el restaurante esta abierto en una hora y dia especificos.
     * @param horaActualStr Hora actual en formato HH:mm.
     * @param diaActualStr Nombre del dia actual en español.
     * @return true si el restaurante esta abierto, false en caso contrario.
     */
    @Transient // Este metodo no es persistente directamente
    public boolean estaAbiertoAhora(String horaActualStr, String diaActualStr) {
        LocalTime horaActual;
        DayOfWeek diaActual;
        try {
            horaActual = LocalTime.parse(horaActualStr, FORMATO_HORA);
            diaActual = parseDia(diaActualStr);
        } catch (java.time.format.DateTimeParseException | IllegalArgumentException e) {
            logger.log(Level.WARNING, String.format("Parámetros de hora/dia inválidos para estaAbiertoAhora: %s, %s", horaActualStr, diaActualStr), e);
            return false;
        }

        if (!diaActual.equals(diaSemana)) {
            logger.fine(() -> "No esta abierto: Dia actual (" + diaActual + ") no coincide con dia de horario (" + diaSemana + ")");
            return false;
        }

        boolean abierto;
        if (horaApertura.isBefore(horaCierre)) {
            abierto = !horaActual.isBefore(horaApertura) && horaActual.isBefore(horaCierre);
            logger.fine(() -> "Horario normal. Abierto: " + abierto + " (" + horaApertura + "-" + horaCierre + ", actual: " + horaActual + ")");
        } else {
            abierto = !horaActual.isBefore(horaApertura) || horaActual.isBefore(horaCierre);
            logger.fine(() -> "Horario cruza medianoche. Abierto: " + abierto + " (" + horaApertura + "-" + horaCierre + ", actual: " + horaActual + ")");
        }
        return abierto;
    }

    /**
     * Convierte un nombre de día en español a un objeto DayOfWeek.
     * @param dia Nombre del dia en español.
     * @return Objeto DayOfWeek correspondiente.
     * @throws IllegalArgumentException Si el nombre del dia es invalido.
     */
    @Transient // Este metodo no es persistente directamente
    private DayOfWeek parseDia(String dia) {
        switch (dia.toLowerCase(Locale.ROOT)) {
            case "lunes": return DayOfWeek.MONDAY;
            case "martes": return DayOfWeek.TUESDAY;
            case "miercoles": return DayOfWeek.WEDNESDAY;
            case "jueves": return DayOfWeek.THURSDAY;
            case "viernes": return DayOfWeek.FRIDAY;
            case "sabado": return DayOfWeek.SATURDAY;
            case "domingo": return DayOfWeek.SUNDAY;
            default:
                throw new IllegalArgumentException("Día inválido: " + dia);
        }
    }

    /**
     * Representacion en cadena del objeto HorarioAtencion para depuracion.
     */
    @Override
    public String toString() {
        return diaSemana.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()) + ": " +
                horaApertura.format(FORMATO_HORA) + " - " +
                horaCierre.format(FORMATO_HORA);
    }

    /**
     * Compara si este objeto HorarioAtencion es igual a otro objeto.
     * Dos objetos HorarioAtencion se consideran iguales si tienen el mismo dia, hora de apertura y hora de cierre.
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HorarioAtencion that = (HorarioAtencion) o;
        return diaSemana == that.diaSemana &&
                Objects.equals(horaApertura, that.horaApertura) &&
                Objects.equals(horaCierre, that.horaCierre);
    }

    /**
     * Genera un codigo hash para este objeto HorarioAtencion.
     * Es consistente con el metodo equals().
     * @return El codigo hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(diaSemana, horaApertura, horaCierre);
    }
}
