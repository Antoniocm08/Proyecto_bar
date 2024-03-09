package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDeDatos {
    private Connection connection;
    private static final String URL_CONNECTION = "jdbc:mysql://127.0.0.1:3306/Proyecto_bar";
    private static final String USER = "root";
    private static final String PASSWORD = "antonio";

    public BaseDeDatos() {
        try {
            this.connection = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // Manejar la excepción si es necesario
            e.printStackTrace();
        }
    }

    public void añadirFactura(Factura factura) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO factura (id_factura, id_mesa) VALUES (?, ?)")) {
            connection.setAutoCommit(false);
            statement.setString(1, factura.getIdFactura());
            statement.setInt(2, factura.getIdMesa());
            statement.executeUpdate();
            connection.commit();
            System.out.println("Factura añadida");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void añadirComanda(int idMesa, String idFactura, int idProducto, int cantidad) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO comandas (id_mesa, id_factura, producto, cantidad, fecha_creacion) VALUES (?, ?, ?, ?, NOW())")) {
            connection.setAutoCommit(false);
            statement.setInt(1, idMesa);
            statement.setString(2, idFactura);
            statement.setInt(3, idProducto);
            statement.setInt(4, cantidad);
            statement.executeUpdate();
            connection.commit();
            System.out.println("La comanda fue añadida");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarComanda(int cantidad, int idProducto, String idFactura) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE comandas SET cantidad = ? WHERE producto = ? AND id_factura = ?")) {
            connection.setAutoCommit(false);
            statement.setInt(1, cantidad);
            statement.setInt(2, idProducto);
            statement.setString(3, idFactura);
            statement.executeUpdate();
            connection.commit();
            System.out.println("La comanda fue actualizada");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarComanda(Productos producto) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM pedidos WHERE producto = ?")) {
            statement.setInt(1, producto.getIdProducto());
            statement.executeUpdate();
            System.out.println("La comanda fue eliminada");
        } catch (SQLException e) {
            showSQLError(e);
        }
    }

    private static void showSQLError(SQLException e) {
        System.err.println("Mensaje de error SQL: " + e.getMessage());
    }
}
