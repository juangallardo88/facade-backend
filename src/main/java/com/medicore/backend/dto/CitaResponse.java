package com.medicore.backend.dto;

import java.time.LocalDateTime;

public record CitaResponse(
        Long id,
        Long pacienteId,
        Long medicoId,
        String medicoNombre,
        String especialidad,
        LocalDateTime fecha,
        String estado,
        String recordatorio
) {
}
