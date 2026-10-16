package com.medicore.backend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescripcion {
    private Long id;
    private Long pacienteId;
    private String medicoResponsable;
    private LocalDate fecha;
    private List<String> medicamentos = new ArrayList<>();
    private String dosis;
    private String frecuencia;
    private String duracion;
    private String observaciones;

    public Prescripcion() {
    }

    public Prescripcion(Long id, Long pacienteId, String medicoResponsable, LocalDate fecha, List<String> medicamentos,
                        String dosis, String frecuencia, String duracion, String observaciones) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoResponsable = medicoResponsable;
        this.fecha = fecha;
        this.medicamentos = medicamentos;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.observaciones = observaciones;
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

    public String getMedicoResponsable() {
        return medicoResponsable;
    }

    public void setMedicoResponsable(String medicoResponsable) {
        this.medicoResponsable = medicoResponsable;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<String> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<String> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
