package com.medicore.backend.dto;

import java.time.LocalDate;

public record PacienteResponse(
        Long id,
        String nombreCompleto,
        String tipoDocumento,
        String numeroDocumento,
        LocalDate fechaNacimiento,
        String telefono,
        String correo
) {
}
