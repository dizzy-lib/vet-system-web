package com.vet.vetweb.medicamento;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {

  private final List<Medicamento> stock = List.of(
      new Medicamento("Amoxicilina 500mg", 3500),
      new Medicamento("Metronidazol 250mg", 4500),
      new Medicamento("Ibuprofeno 200mg", 2000),
      new Medicamento("Antiparasitario oral", 8000),
      new Medicamento("Antipulgas spot-on", 12000),
      new Medicamento("Vitamina B12", 5000),
      new Medicamento("Suero fisiológico 500ml", 3000),
      new Medicamento("Ciprofloxacino 250mg", 6000)
  );

  public List<Medicamento> listar() {
    return stock;
  }

  public Optional<Medicamento> buscarPorNombre(String nombre) {
    return stock.stream().filter(m -> m.nombre().equals(nombre)).findFirst();
  }
}
