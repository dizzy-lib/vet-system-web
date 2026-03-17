package com.vet.vetweb.atencion;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AtencionService {

  private final Map<Integer, Atencion> atenciones = new ConcurrentHashMap<>();

  public void guardar(int index, Atencion atencion) {
    atenciones.put(index, atencion);
  }

  public Optional<Atencion> buscar(int index) {
    return Optional.ofNullable(atenciones.get(index));
  }
}
