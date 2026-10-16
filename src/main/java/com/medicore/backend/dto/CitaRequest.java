package com.medicore.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CitaRequest(
        @NotNull(message = "El identificador del paciente es obligatorio") Long pacienteId,
        @NotBlank(message = "La especialidad es obligatoria") String especialidad,
        @NotNull(message = "La fecha y hora de la cita es obligatoria") LocalDateTime fecha
) {
}
