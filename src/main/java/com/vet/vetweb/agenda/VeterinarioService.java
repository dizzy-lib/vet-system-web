package com.vet.vetweb.agenda;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class VeterinarioService {

    private final List<Veterinario> veterinarios = List.of(
        new Veterinario("Dr. Carlos Mendoza", "general"),
        new Veterinario("Dra. Ana Rojas", "general"),
        new Veterinario("Dr. Pablo Soto", "cardiologia"),
        new Veterinario("Dra. María Fuentes", "nutricion"),
        new Veterinario("Dr. Luis Herrera", "cardiologia")
    );

    private final List<LocalTime> horasDisponibles = List.of(
        LocalTime.of(9, 0),
        LocalTime.of(10, 0),
        LocalTime.of(11, 0),
        LocalTime.of(14, 0),
        LocalTime.of(15, 0),
        LocalTime.of(16, 0)
    );

    public List<DisponibilidadVeterinario> buscarDisponibles(String especialidad, LocalDate fecha) {
        return veterinarios.stream()
            .filter(v -> v.especialidad().equals(especialidad))
            .map(v -> new DisponibilidadVeterinario(v, horasDisponibles))
            .toList();
    }
}
