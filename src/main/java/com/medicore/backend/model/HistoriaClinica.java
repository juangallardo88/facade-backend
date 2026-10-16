package com.medicore.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoriaClinica {
    private Long pacienteId;
    private List<String> diagnosticos = new ArrayList<>();
    private List<String> alergias = new ArrayList<>();
    private List<String> historialCitas = new ArrayList<>();

    public HistoriaClinica() {
    }

    public HistoriaClinica(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public List<String> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(List<String> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public List<String> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }

    public List<String> getHistorialCitas() {
        return historialCitas;
    }

    public void setHistorialCitas(List<String> historialCitas) {
        this.historialCitas = historialCitas;
    }

    public void registrarDiagnostico(String diagnostico) {
        this.diagnosticos.add(diagnostico);
    }

    public void registrarAlergia(String alergia) {
        if (!this.alergias.contains(alergia)) {
            this.alergias.add(alergia);
        }
    }

    public void registrarCita(String detalleCita) {
        this.historialCitas.add(detalleCita + " | " + LocalDate.now());
    }
}
