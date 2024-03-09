package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Factura {
    private int[] contadores = {0, 0, 0, 0, 0};
    private String id_factura;
    private int id_mesa;
    private int id_comanda;
    private float total;
    private boolean esta_ocupada;
    private ObservableList<Productos> listaProductos;

    public Factura(String id_factura, int id_mesa, boolean esta_ocupada) {
        this.id_factura = id_factura;
        this.id_mesa = id_mesa;
        this.esta_ocupada = esta_ocupada;
        listaProductos = FXCollections.observableArrayList();
    }


    public Factura() {
        listaProductos = FXCollections.observableArrayList();
    }


    public ObservableList<Productos> getListaProductos() {

        return listaProductos;
    }

    public void setListaProductos(ObservableList<Productos> listaProductos) {

        this.listaProductos = listaProductos;
    }


    public int[] getContadores() {
        return contadores;
    }

    public void setContadores(int[] contadores) {

        this.contadores = contadores;
    }


    public boolean isEstaOcupada() {

        return esta_ocupada;
    }

    public void setEstaOcupada(boolean esta_ocupada) {

        this.esta_ocupada = esta_ocupada;
    }


    public String getIdFactura() {

        return id_factura;
    }

    public void setIdFactura(String id_factura) {

        this.id_factura = id_factura;
    }


    public int getIdMesa() {

        return id_mesa;
    }

    public void setIdMesa(int id_mesa) {

        this.id_mesa = id_mesa;
    }


    @Override
    public String toString() {
        return "Factura{" +
                "id_factura='" + id_factura + '\'' +
                ", id_mesa=" + id_mesa +
                ", total=" + total +
                ", esta_ocupada=" + esta_ocupada +
                '}';
    }
}
