package com.vet.vetweb.agenda;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioService {

    private final List<Veterinario> veterinarios = List.of(
        new Veterinario("Dr. Carlos Mendoza", "general",    "vet@vet.cl"),
        new Veterinario("Dra. Ana Rojas",      "general",    null),
        new Veterinario("Dr. Pablo Soto",      "cardiologia", null),
        new Veterinario("Dra. María Fuentes",  "nutricion",  null),
        new Veterinario("Dr. Luis Herrera",    "cardiologia", null)
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

    public Optional<Veterinario> buscarPorUsername(String username) {
        return veterinarios.stream()
            .filter(v -> username.equals(v.username()))
            .findFirst();
    }
}
