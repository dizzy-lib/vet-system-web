package com.vet.vetweb.agenda;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CitaService {

    private final List<Cita> citas = new CopyOnWriteArrayList<>();

    public void agendar(Cita cita) {
        citas.add(cita);
    }

    public List<Cita> listar() {
        return List.copyOf(citas);
    }

    public List<Cita> listarHoy() {
        LocalDate hoy = LocalDate.now();
        return citas.stream()
            .filter(c -> c.fecha().equals(hoy))
            .sorted(Comparator.comparing(Cita::hora))
            .toList();
    }

    public List<Cita> listarHoyPorVet(String veterinarioNombre) {
        LocalDate hoy = LocalDate.now();
        return citas.stream()
            .filter(c -> c.fecha().equals(hoy) && c.veterinarioNombre().equals(veterinarioNombre))
            .sorted(Comparator.comparing(Cita::hora))
            .toList();
    }

    public List<LocalTime> horasOcupadas(String veterinarioNombre, LocalDate fecha) {
        return citas.stream()
            .filter(c -> c.veterinarioNombre().equals(veterinarioNombre) && c.fecha().equals(fecha))
            .map(Cita::hora)
            .toList();
    }
}
