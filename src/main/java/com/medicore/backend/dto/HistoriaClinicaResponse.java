package com.medicore.backend.dto;

import java.util.List;

public record HistoriaClinicaResponse(
        Long pacienteId,
        List<String> diagnosticos,
        List<String> alergias,
        List<String> historialCitas,
        List<String> prescripciones,
        List<String> laboratorio
) {
}
