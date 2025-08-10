package com.utn.gestor.modelo;

import java.time.LocalDate;

public class Proyecto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private int porcentaje;

    public Proyecto() {}
    public Proyecto(Integer id, String nombre, String descripcion, LocalDate fi, LocalDate ff, String estado, int porcentaje) {
        this.id = id; this.nombre = nombre; this.descripcion = descripcion;
        this.fechaInicio = fi; this.fechaFin = ff; this.estado = estado; this.porcentaje = porcentaje;
    }

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getPorcentaje() { return porcentaje; }
    public void setPorcentaje(int porcentaje) { this.porcentaje = porcentaje; }

    @Override public String toString() { return nombre; }
}
