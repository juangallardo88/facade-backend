package com.medicore.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PacienteRequest(
        @NotBlank(message = "El nombre completo es obligatorio") String nombreCompleto,
        @NotBlank(message = "El tipo de documento es obligatorio") String tipoDocumento,
        @NotBlank(message = "El número de documento es obligatorio") String numeroDocumento,
        @NotNull(message = "La fecha de nacimiento es obligatoria") LocalDate fechaNacimiento,
        @NotBlank(message = "El teléfono es obligatorio") String telefono,
        @NotBlank(message = "El correo electrónico es obligatorio") @Email(message = "El correo electrónico no es válido") String correo
) {
}
