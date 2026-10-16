package com.medicore.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitudExamen {
    private Long id;
    private Long pacienteId;
    private List<String> examenes = new ArrayList<>();
    private LocalDateTime fechaSolicitud;
    private String estado;
    private List<ResultadoExamen> resultados = new ArrayList<>();

    public SolicitudExamen() {
    }

    public SolicitudExamen(Long id, Long pacienteId, List<String> examenes, LocalDateTime fechaSolicitud, String estado) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.examenes = examenes;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public List<String> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<String> examenes) {
        this.examenes = examenes;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ResultadoExamen> getResultados() {
        return resultados;
    }

    public void setResultados(List<ResultadoExamen> resultados) {
        this.resultados = resultados;
    }
}
