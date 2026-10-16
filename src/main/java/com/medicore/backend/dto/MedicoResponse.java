package com.medicore.backend.dto;

import java.time.LocalTime;

public record MedicoResponse(
        Long id,
        String nombre,
        String especialidad,
        LocalTime horaInicio,
        LocalTime horaFin,
        boolean datosDemostracion
) {
}
