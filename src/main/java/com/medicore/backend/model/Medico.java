package com.medicore.backend.model;

import java.time.LocalTime;

public class Medico {
    private Long id;
    private String nombre;
    private String especialidad;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean datosDemostracion;

    public Medico() {
    }

    public Medico(Long id, String nombre, String especialidad, LocalTime horaInicio, LocalTime horaFin, boolean datosDemostracion) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.datosDemostracion = datosDemostracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isDatosDemostracion() {
        return datosDemostracion;
    }

    public void setDatosDemostracion(boolean datosDemostracion) {
        this.datosDemostracion = datosDemostracion;
    }
}
