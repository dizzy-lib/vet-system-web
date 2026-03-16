package com.vet.vetweb.paciente;

import org.springframework.stereotype.Service;

import java.util.List;
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
}
