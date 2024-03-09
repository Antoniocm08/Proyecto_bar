package org.example;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;


public class SecondaryController {
    @FXML
    TextArea Total;
    private double total;
    PrimaryController primaryController;
    @FXML ListView<Productos> listacoman;
    private static List<Factura> listaFacturas = new ArrayList<>();
    private BaseDeDatos baseDeDatos;
    private Factura factura;
    private int id_mesa = PrimaryController.getId_mesa();

    @FXML private Button Hamburguesa;
    @FXML private Button Cerveza;
    @FXML private Button Pizza;
    @FXML private Button Patatas_Fritas;
    @FXML private Button Ensalada;

    @FXML
    private void initialize() throws IOException {

    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }


    @FXML
    public void añadirAlimento(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();
        if (sourceButton.equals(Hamburguesa)) {
            actualizarComanda(19.99, 1, "Hamburguesa");
        } else if (sourceButton.equals(Cerveza)) {
            actualizarComanda(25.50, 2, "Cerveza");
        } else if (sourceButton.equals(Pizza)) {
            actualizarComanda(15.75, 3, "Pizza");
        } else if (sourceButton.equals(Patatas_Fritas)) {
            actualizarComanda(8.99, 4, "Patatas fritas");
        } else if (sourceButton.equals(Ensalada)) {
            actualizarComanda(12.50, 5, "Ensalada");
        }
    }


    @FXML
    public void actualizarComanda(double precio, int idProducto, String nombreProducto) {
        factura.getContadores()[idProducto - 1] += 1;
        total += precio;
        Total.setText(String.valueOf((double) Math.round(total * 100d) / 100d));

        if (factura.getContadores()[idProducto - 1] > 1) {
            baseDeDatos.actualizarComanda(
                    factura.getContadores()[idProducto - 1],
                    idProducto,
                    factura.getIdFactura()
            );

            for (int i = 0; i < factura.getListaProductos().size(); i++) {
                if (idProducto == factura.getListaProductos().get(i).getIdProducto()) {
                    factura.getListaProductos().remove(i);
                    break;
                }
            }

            Productos producto = new Productos(idProducto, nombreProducto, precio, factura.getContadores()[idProducto - 1]);
            factura.getListaProductos().add(producto);

        } else {
            Productos producto = new Productos(idProducto, nombreProducto, precio, factura.getContadores()[idProducto - 1]);
            baseDeDatos.actualizarComanda(factura.getContadores()[idProducto - 1], idProducto, factura.getIdFactura());
            factura.getListaProductos().add(producto);
        }

        listacoman.setItems(factura.getListaProductos());
    }


    @FXML
    public void Pago() throws IOException {
        Alert a = new Alert(Alert.AlertType.NONE);

        a.setAlertType(Alert.AlertType.CONFIRMATION);

        a.setHeaderText("Confirmar");
        a.setContentText("¿Confirmar pago?");

        Optional<ButtonType> okCancel = a.showAndWait();
        if (okCancel.get() != ButtonType.CANCEL) {
            if(factura.getListaProductos().size()>0){
                pagar();
            }else{
                PrimaryController.echarMesa(id_mesa);
                switchToPrimary();
                listaFacturas.remove(factura);
            }
        }
    }

    @FXML
    public void eliminar() {
        if (listacoman.getSelectionModel().getSelectedItem() != null) {
            Productos productoSeleccionado = listacoman.getSelectionModel().getSelectedItem();

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setHeaderText("Confirmar");
            confirmacion.setContentText("¿Eliminar?");
            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                int idProducto = productoSeleccionado.getIdProducto();

                if (factura.getContadores()[idProducto - 1] > 1) {
                    factura.getContadores()[idProducto - 1]--;
                    productoSeleccionado.setCantidad(productoSeleccionado.getCantidad() - 1);

                    baseDeDatos.actualizarComanda(factura.getContadores()[idProducto - 1], idProducto, factura.getIdFactura());
                } else {

                    baseDeDatos.eliminarComanda(productoSeleccionado);
                    factura.getContadores()[idProducto - 1] = 0;
                    factura.getListaProductos().remove(productoSeleccionado);
                }

                total -= productoSeleccionado.getPrecio();
                Total.setText(String.valueOf((double) Math.round(total * 100d) / 100d));
            }
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error");
            error.setContentText("agrega un producto.");
            error.show();
        }
    }




    public void pagar() throws IOException {
        PrimaryController.echarMesa(id_mesa);
        switchToPrimary();
        listaFacturas.remove(factura);

        try {
            double totalAmount = Double.parseDouble(Total.getText());
            imprimirFactura(totalAmount);
        } catch (NumberFormatException e) {

            System.err.println("Error : " + e.getMessage());
        }
    }

    @FXML
    private void imprimirFactura(double total) {
        InputStream reportFile = getClass().getResourceAsStream("/factura.jrxml");
        try {
            BaseDeDatos baseDeDatos = new BaseDeDatos();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);
            Map<String, Object> parametro = new HashMap<>();
            parametro.put("TOTAL", total);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, baseDeDatos.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }






}