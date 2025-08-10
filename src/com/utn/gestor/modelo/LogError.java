package com.utn.gestor.modelo;

public class LogError {
    private Integer id;
    private String origen;
    private String mensaje;
    private String traza;

    public LogError(){}
    public LogError(String origen, String mensaje, String traza) {
        this.origen = origen; this.mensaje = mensaje; this.traza = traza;
    }

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getTraza() { return traza; }
    public void setTraza(String traza) { this.traza = traza; }
}
