package com.vet.vetweb.agenda;

import java.time.LocalTime;
import java.util.List;

public record DisponibilidadVeterinario(Veterinario veterinario, List<LocalTime> horas) {}
