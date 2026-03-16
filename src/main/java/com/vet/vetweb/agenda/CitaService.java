package com.vet.vetweb.agenda;

import org.springframework.stereotype.Service;

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
}
