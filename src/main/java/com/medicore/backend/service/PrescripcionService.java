package com.medicore.backend.service;

import com.medicore.backend.exception.BusinessException;
import com.medicore.backend.model.Prescripcion;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PrescripcionService {

    private final Map<Long, List<Prescripcion>> prescripcionesPorPaciente = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    public Prescripcion generarPrescripcion(Long pacienteId, String medicoResponsable, List<String> medicamentos,
                                           String dosis, String frecuencia, String duracion, String observaciones) {
        if (pacienteId == null) {
            throw new BusinessException("El paciente es obligatorio");
        }

        List<String> medicamentosValidos = medicamentos == null ? List.of() : medicamentos.stream()
                .filter(medicamento -> medicamento != null && !medicamento.isBlank())
                .map(String::trim)
                .toList();

        if (medicamentosValidos.isEmpty()) {
            throw new BusinessException("Debe indicar al menos un medicamento para la prescripción");
        }

        Prescripcion prescripcion = new Prescripcion(
                sequence.getAndIncrement(),
                pacienteId,
                medicoResponsable,
                LocalDate.now(),
                medicamentosValidos,
                dosis,
                frecuencia,
                duracion,
                observaciones
        );

        prescripcionesPorPaciente.computeIfAbsent(pacienteId, key -> new ArrayList<>()).add(prescripcion);
        return prescripcion;
    }

    public List<Prescripcion> consultarPorPaciente(Long pacienteId) {
        return prescripcionesPorPaciente.getOrDefault(pacienteId, List.of());
    }

    public void validarAlergias(Long pacienteId, List<String> medicamentos, List<String> alergias) {
        if (alergias == null || alergias.isEmpty() || medicamentos == null || medicamentos.isEmpty()) {
            return;
        }

        for (String medicamento : medicamentos) {
            String nombreMedicamento = medicamento == null ? "" : medicamento.trim().toLowerCase();
            for (String alergia : alergias) {
                String nombreAlergia = alergia == null ? "" : alergia.trim().toLowerCase();
                if (!nombreAlergia.isEmpty() && nombreMedicamento.contains(nombreAlergia)) {
                    throw new BusinessException(
                            "Se detectó una alergia relacionada con el medicamento '" + medicamento + "'. No se puede continuar con la prescripción."
                    );
                }
            }
        }
    }
}
