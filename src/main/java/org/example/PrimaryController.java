package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class PrimaryController {
    BaseDeDatos bdd;
    private static int[] esta_Ocupada = {0, 0, 0, 0, 0};
    public static int id_mesa;

    @FXML
    Button PrimeraM;
    @FXML
    Button SegundaM;
    @FXML
    Button TerceraM;
    @FXML
    Button CuartaM;
    @FXML
    Button QuintaM;

    @FXML
    ImageView imagen1;
    @FXML
    ImageView imagen2;
    @FXML
    ImageView imagen3;
    @FXML
    ImageView imagen4;
    @FXML
    ImageView imagen5;

    @FXML
    private void initialize() {
        bdd = new BaseDeDatos();
        id_mesa = 0;
        for (int i = 0; i < esta_Ocupada.length; i++) {
            if (esta_Ocupada[i] == 1) {
                Estado(i + 1);
            }
        }

    }

    @FXML
    private void switchToSecondary() throws IOException {
       App.setRoot("secondary");
    }


    public static int getId_mesa() {
        return id_mesa;
    }

    @FXML
    public void Button(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();

        if (sourceButton.equals(PrimeraM)) {
            id_mesa = 1;
        } else if (sourceButton.equals(SegundaM)) {
            id_mesa = 2;
        } else if (sourceButton.equals(TerceraM)) {
            id_mesa = 3;
        } else if (sourceButton.equals(CuartaM)) {
            id_mesa = 4;
        } else if (sourceButton.equals(QuintaM)) {
            id_mesa = 5;
        }

        switchToSecondary();
        esta_Ocupada[id_mesa - 1] = 1;
    }

    public void Estado(int id_mesa) {
        switch (id_mesa) {
            case 1:
                imagen1.setVisible(true);
                break;
            case 2:
                imagen2.setVisible(true);
                break;
            case 3:
                imagen3.setVisible(true);
                break;
            case 4:
                imagen4.setVisible(true);
                break;
            case 5:
                imagen5.setVisible(true);
                break;
        }
    }

    public static void echarMesa(int id_mesa) {
        if (id_mesa >= 1 && id_mesa <= esta_Ocupada.length) {
            esta_Ocupada[id_mesa - 1] = 0;
        } else {
            System.err.println("El id de la mesa no esta disponible: " + id_mesa);
        }
    }

    public void imprimir(ActionEvent actionEvent) {
        InputStream reportFile = getClass().getResourceAsStream("/histrorialDeMesas.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, bdd.getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
