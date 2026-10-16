package com.medicore.backend.model;

public class ResultadoExamen {
    private String nombreExamen;
    private Double valor;
    private String unidad;
    private Double rangoMinimo;
    private Double rangoMaximo;
    private String estado;
    private boolean simulado;

    public ResultadoExamen() {
    }

    public ResultadoExamen(String nombreExamen, Double valor, String unidad, Double rangoMinimo, Double rangoMaximo,
                           String estado, boolean simulado) {
        this.nombreExamen = nombreExamen;
        this.valor = valor;
        this.unidad = unidad;
        this.rangoMinimo = rangoMinimo;
        this.rangoMaximo = rangoMaximo;
        this.estado = estado;
        this.simulado = simulado;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Double getRangoMinimo() {
        return rangoMinimo;
    }

    public void setRangoMinimo(Double rangoMinimo) {
        this.rangoMinimo = rangoMinimo;
    }

    public Double getRangoMaximo() {
        return rangoMaximo;
    }

    public void setRangoMaximo(Double rangoMaximo) {
        this.rangoMaximo = rangoMaximo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isSimulado() {
        return simulado;
    }

    public void setSimulado(boolean simulado) {
        this.simulado = simulado;
    }
}
