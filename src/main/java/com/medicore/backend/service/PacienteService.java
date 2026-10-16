package com.medicore.backend.service;

import com.medicore.backend.dto.PacienteRequest;
import com.medicore.backend.dto.PacienteResponse;
import com.medicore.backend.exception.BusinessException;
import com.medicore.backend.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PacienteService {

    private final Map<Long, Paciente> pacientes = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    public Paciente registrarPaciente(PacienteRequest request) {
        String numeroDocumento = request.numeroDocumento() == null ? "" : request.numeroDocumento().trim();
        if (numeroDocumento.isBlank()) {
            throw new BusinessException("El número de documento es obligatorio");
        }

        boolean duplicado = pacientes.values().stream()
                .anyMatch(paciente -> paciente.getNumeroDocumento().equalsIgnoreCase(numeroDocumento));
        if (duplicado) {
            throw new BusinessException("Ya existe un paciente registrado con ese documento");
        }

        Paciente paciente = new Paciente(
                sequence.getAndIncrement(),
                request.nombreCompleto().trim(),
                request.tipoDocumento().trim(),
                numeroDocumento,
                request.fechaNacimiento(),
                request.telefono().trim(),
                request.correo().trim()
        );

        pacientes.put(paciente.getId(), paciente);
        return paciente;
    }

    public Paciente consultarPaciente(Long pacienteId) {
        Paciente paciente = pacientes.get(pacienteId);
        if (paciente == null) {
            throw new BusinessException("Paciente no encontrado con ID " + pacienteId);
        }
        return paciente;
    }

    public PacienteResponse consultarPerfil(Long pacienteId) {
        Paciente paciente = consultarPaciente(pacienteId);
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNombreCompleto(),
                paciente.getTipoDocumento(),
                paciente.getNumeroDocumento(),
                paciente.getFechaNacimiento(),
                paciente.getTelefono(),
                paciente.getCorreo()
        );
    }

    public void validarPaciente(Long pacienteId) {
        if (pacienteId == null || !pacientes.containsKey(pacienteId)) {
            throw new BusinessException("El paciente indicado no existe");
        }
    }

    public boolean existePaciente(Long pacienteId) {
        return pacientes.containsKey(pacienteId);
    }

    public void guardarPaciente(Paciente paciente) {
        pacientes.put(paciente.getId(), paciente);
    }
}
