package com.vet.vetweb.agenda;

import java.time.LocalDate;
import java.time.LocalTime;

public record Cita(
    String mascotaNombre,
    String duenoRun,
    String veterinarioNombre,
    String especialidad,
    LocalDate fecha,
    LocalTime hora
) {}
