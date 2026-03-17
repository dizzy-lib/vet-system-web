package com.vet.vetweb.agenda;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioService {

    private final CitaService citaService;

    private final List<Veterinario> veterinarios = List.of(
        new Veterinario("Dr. Carlos Mendoza", "general",    "c.mendoza@vet.cl"),
        new Veterinario("Dr. Pablo Soto",      "cardiologia", "p.soto@vet.cl"),
        new Veterinario("Dra. María Fuentes",  "nutricion",  "m.fuentes@vet.cl")
    );

    private final List<LocalTime> horasDisponibles = List.of(
        LocalTime.of(9, 0),
        LocalTime.of(10, 0),
        LocalTime.of(11, 0),
        LocalTime.of(14, 0),
        LocalTime.of(15, 0),
        LocalTime.of(16, 0)
    );

    public VeterinarioService(@Lazy CitaService citaService) {
        this.citaService = citaService;
    }

    public List<DisponibilidadVeterinario> buscarDisponibles(String especialidad, LocalDate fecha) {
        return veterinarios.stream()
            .filter(v -> v.especialidad().equals(especialidad))
            .map(v -> {
                List<LocalTime> ocupadas = citaService.horasOcupadas(v.nombre(), fecha);
                List<LocalTime> libres = horasDisponibles.stream()
                    .filter(h -> !ocupadas.contains(h))
                    .toList();
                return new DisponibilidadVeterinario(v, libres);
            })
            .toList();
    }

    public Optional<Veterinario> buscarPorUsername(String username) {
        return veterinarios.stream()
            .filter(v -> username.equals(v.username()))
            .findFirst();
    }
}
