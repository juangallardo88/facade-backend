package com.medicore.backend.service;

import com.medicore.backend.exception.BusinessException;
import com.medicore.backend.model.ResultadoExamen;
import com.medicore.backend.model.SolicitudExamen;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LaboratorioService {

    private final Map<Long, List<SolicitudExamen>> solicitudesPorPaciente = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    public SolicitudExamen registrarSolicitud(Long pacienteId, List<String> examenes) {
        if (pacienteId == null) {
            throw new BusinessException("El paciente es obligatorio");
        }

        List<String> examenesValidos = examenes == null ? List.of() : examenes.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(nombre -> !nombre.isBlank())
                .toList();

        if (examenesValidos.isEmpty()) {
            throw new BusinessException("Debe indicar al menos un examen de laboratorio");
        }

        Long id = sequence.getAndIncrement();
        SolicitudExamen solicitud = new SolicitudExamen(id, pacienteId, examenesValidos, LocalDateTime.now(), "PENDIENTE");
        solicitud.setResultados(generarResultadosSimulados(examenesValidos));
        solicitudesPorPaciente.computeIfAbsent(pacienteId, key -> new ArrayList<>()).add(solicitud);
        return solicitud;
    }

    public List<SolicitudExamen> consultarSolicitudesPorPaciente(Long pacienteId) {
        return solicitudesPorPaciente.getOrDefault(pacienteId, List.of());
    }

    private List<ResultadoExamen> generarResultadosSimulados(List<String> examenes) {
        List<ResultadoExamen> resultados = new ArrayList<>();
        for (String examen : examenes) {
            resultados.add(generarResultadoPorExamen(examen));
        }
        return resultados;
    }

    private ResultadoExamen generarResultadoPorExamen(String examen) {
        String nombre = examen.trim();
        ResultadoExamen resultado;
        switch (nombre.toLowerCase()) {
            case "hemograma" -> resultado = new ResultadoExamen("Hemograma", 7.5, "x10^9/L", 4.0, 10.5, "Dentro del rango", true);
            case "glicemia" -> resultado = new ResultadoExamen("Glicemia", 98.0, "mg/dL", 70.0, 100.0, "Dentro del rango", true);
            case "perfil lipídico" -> resultado = new ResultadoExamen("Perfil lipídico", 185.0, "mg/dL", 120.0, 200.0, "Dentro del rango", true);
            default -> resultado = new ResultadoExamen(nombre, 0.0, "u", 0.0, 0.0, "No aplica", true);
        }
        return resultado;
    }
}
