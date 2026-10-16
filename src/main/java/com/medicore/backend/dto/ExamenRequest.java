package com.medicore.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ExamenRequest(
        @NotNull(message = "El paciente es obligatorio") Long pacienteId,
        @NotNull(message = "Debe indicar al menos un examen") List<@NotBlank String> examenes
) {
}
