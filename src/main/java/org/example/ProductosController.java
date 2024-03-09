package org.example;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductosController {

    @FXML
    private VBox almacenamiento;

    private List<Productos> listaProductos;
    private List<Productos> productosSeleccionados;

    public void inicializar(List<Productos> listaProductos) {
        this.listaProductos = listaProductos;
        this.productosSeleccionados = new ArrayList<>();


        List<Productos> productosOrdenados = listaProductos.stream()
                .sorted((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()))
                .collect(Collectors.toList());

        for (Productos producto : productosOrdenados) {
            Label labelProducto = new Label(producto.getNombre() + " - Precio: $" + producto.getPrecio());
            labelProducto.setOnMouseClicked(event -> agregarProductoSeleccionado(producto));

            almacenamiento.getChildren().add(labelProducto);
        }
    }

    private void agregarProductoSeleccionado(Productos producto) {
        productosSeleccionados.add(producto);
        System.out.println("Producto seleccionado: " + producto.getNombre() + " - Precio: $" + producto.getPrecio());
    }

    public List<Productos> getProductosSeleccionados() {
        return productosSeleccionados;
    }


}
