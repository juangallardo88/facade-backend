package com.medicore.backend.model;

import java.time.LocalDateTime;

public class CitaMedica {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private String medicoNombre;
    private String especialidad;
    private LocalDateTime fechaHora;
    private String estado;
    private String recordatorio;

    public CitaMedica() {
    }

    public CitaMedica(Long id, Long pacienteId, Long medicoId, String medicoNombre, String especialidad,
                      LocalDateTime fechaHora, String estado, String recordatorio) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.medicoNombre = medicoNombre;
        this.especialidad = especialidad;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.recordatorio = recordatorio;
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

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public void setMedicoNombre(String medicoNombre) {
        this.medicoNombre = medicoNombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }
}
