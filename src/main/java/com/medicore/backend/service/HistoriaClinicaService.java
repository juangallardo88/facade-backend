package com.medicore.backend.service;

import com.medicore.backend.exception.BusinessException;
import com.medicore.backend.model.HistoriaClinica;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HistoriaClinicaService {

    private final Map<Long, HistoriaClinica> historias = new ConcurrentHashMap<>();

    public HistoriaClinica obtenerHistoriaPaciente(Long pacienteId) {
        return historias.computeIfAbsent(pacienteId, HistoriaClinica::new);
    }

    public void registrarConsultaAnterior(Long pacienteId, String diagnostico) {
        if (diagnostico == null || diagnostico.isBlank()) {
            throw new BusinessException("El diagnóstico no puede estar vacío");
        }
        HistoriaClinica historia = obtenerHistoriaPaciente(pacienteId);
        historia.registrarDiagnostico(diagnostico);
    }

    public void registrarAlergia(Long pacienteId, String alergia) {
        if (alergia == null || alergia.isBlank()) {
            throw new BusinessException("La alergia no puede estar vacía");
        }
        HistoriaClinica historia = obtenerHistoriaPaciente(pacienteId);
        historia.registrarAlergia(alergia);
    }

    public List<String> consultarDiagnosticos(Long pacienteId) {
        return obtenerHistoriaPaciente(pacienteId).getDiagnosticos();
    }

    public List<String> consultarAlergias(Long pacienteId) {
        return obtenerHistoriaPaciente(pacienteId).getAlergias();
    }

    public List<String> consultarHistorialCitas(Long pacienteId) {
        return obtenerHistoriaPaciente(pacienteId).getHistorialCitas();
    }

    public HistoriaClinica consolidarHistoria(Long pacienteId) {
        return obtenerHistoriaPaciente(pacienteId);
    }

    public boolean tieneAlergiaRelacionada(Long pacienteId, List<String> medicamentos) {
        List<String> alergias = consultarAlergias(pacienteId);
        if (alergias.isEmpty()) {
            return false;
        }

        return medicamentos.stream().anyMatch(medicamento ->
                alergias.stream().anyMatch(alergia ->
                        medicamento.toLowerCase().contains(alergia.toLowerCase())
                )
        );
    }
}
