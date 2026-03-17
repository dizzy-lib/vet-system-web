package com.vet.vetweb.atencion;

import java.util.List;

public record Atencion(
    String diagnostico,
    String tratamiento,
    List<String> medicamentosSeleccionados,
    String notasMedicas
) {}
