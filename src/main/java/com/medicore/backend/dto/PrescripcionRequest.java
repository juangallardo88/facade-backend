package com.medicore.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PrescripcionRequest(
        @NotNull(message = "El paciente es obligatorio") Long pacienteId,
        @NotBlank(message = "El médico responsable es obligatorio") String medicoResponsable,
        @NotNull(message = "Debe indicar al menos un medicamento") List<@NotBlank String> medicamentos,
        @NotBlank(message = "La dosis es obligatoria") String dosis,
        @NotBlank(message = "La frecuencia es obligatoria") String frecuencia,
        @NotBlank(message = "La duración del tratamiento es obligatoria") String duracion,
        String observaciones
) {
}
