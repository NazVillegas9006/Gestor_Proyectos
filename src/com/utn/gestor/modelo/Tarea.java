package com.utn.gestor.modelo;

import java.time.LocalDate;

public class Tarea {
    private Integer id;
    private Integer proyectoId;
    private Integer asignadoA;
    private String titulo;
    private String descripcion;
    private String prioridad;
    private String estado;
    private int avance;
    private LocalDate fechaVencimiento;

    public Tarea(){}

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getProyectoId() { return proyectoId; }
    public void setProyectoId(Integer proyectoId) { this.proyectoId = proyectoId; }
    public Integer getAsignadoA() { return asignadoA; }
    public void setAsignadoA(Integer asignadoA) { this.asignadoA = asignadoA; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getAvance() { return avance; }
    public void setAvance(int avance) { this.avance = avance; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
}
