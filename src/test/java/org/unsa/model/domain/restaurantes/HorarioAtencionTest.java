package org.unsa.model.domain.restaurantes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class HorarioAtencionTest {
  @Test
  void test1_EntradaInvalida_HoraInvalida() {
    // Arrange
    HorarioAtencion horario = new HorarioAtencion("Lunes", "09:00", "22:00");

    // Act
    boolean resultado = horario.estaAbiertoAhora("25:70", "Lunes");

    // Assert
    assertFalse(resultado);
  }

  @Test
  void test2_EntradaInvalida_DiaInvalido() {
    // Arrange
    HorarioAtencion horario = new HorarioAtencion("Lunes", "09:00", "22:00");

    // Act
    boolean resultado = horario.estaAbiertoAhora("15:00", "DiaInvalido");

    // Assert
    assertFalse(resultado);
  }

  @Test
  void test3_DiaNoCoincide() {
    // Arrange
    HorarioAtencion horario = new HorarioAtencion("Lunes", "09:00", "22:00");

    // Act
    boolean resultado = horario.estaAbiertoAhora("15:00", "Martes");

    // Assert
    assertFalse(resultado);
  }

  @Test
  void test4_HorarioNormal_DentroHorario() {
    // Arrange
    HorarioAtencion horario = new HorarioAtencion("Lunes", "09:00", "22:00");

    // Act
    boolean resultado = horario.estaAbiertoAhora("15:00", "Lunes");

    // Assert
    assertTrue(resultado);
  }

  @Test
  void test5_HorarioNormal_FueraHorario() {
    // Arrange
    HorarioAtencion horario = new HorarioAtencion("Lunes", "09:00", "22:00");

    // Act
    boolean resultado = horario.estaAbiertoAhora("08:00", "Lunes");

    // Assert
    assertFalse(resultado);
  }


}