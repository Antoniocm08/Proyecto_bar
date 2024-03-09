package org.example;

public class HistoricoDeMesas {
    private int idMesa;
    private String estado;
    private String fechaOcupacion;
    private String fechaDesocupacion;

    public HistoricoDeMesas(int idMesa, String estado, String fechaOcupacion, String fechaDesocupacion) {
        this.idMesa = idMesa;
        this.estado = estado;
        this.fechaOcupacion = fechaOcupacion;
        this.fechaDesocupacion = fechaDesocupacion;
    }

    public int getIdMesa() {

        return idMesa;
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaOcupacion() {
        return fechaOcupacion;
    }

    public String getFechaDesocupacion() {
        return fechaDesocupacion;
    }
}