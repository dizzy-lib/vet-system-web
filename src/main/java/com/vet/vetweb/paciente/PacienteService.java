package com.vet.vetweb.paciente;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class PacienteService {

    private final List<Paciente> pacientes = new CopyOnWriteArrayList<>();

    public void registrar(Paciente paciente) {
        pacientes.add(paciente);
    }

    public List<Paciente> listar() {
        return List.copyOf(pacientes);
    }

    public List<Paciente> buscar(String nombre, String run) {
        return pacientes.stream()
            .filter(p ->
                (nombre != null && !nombre.isBlank() && p.mascota().nombre().toLowerCase().contains(nombre.toLowerCase())) ||
                (run != null && !run.isBlank() && p.dueno().run().toLowerCase().contains(run.toLowerCase()))
            )
            .toList();
    }

    public Optional<Paciente> buscarPorNombreYRun(String mascotaNombre, String duenoRun) {
        return pacientes.stream()
            .filter(p -> p.mascota().nombre().equals(mascotaNombre) && p.dueno().run().equals(duenoRun))
            .findFirst();
    }
}
