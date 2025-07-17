package org.stepone.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author reyes
 */
public class Conexion {
    private static Conexion instancia;

    private Connection conexion;

    private String URL = "jdbc:mysql://127.0.0.1:3306/DBStepOne?useSSL=false";
    private String user = "quintov";
    private String password = "admin";
    private String driver = "com.mysql.jdbc.Driver";

    public Conexion() {
        conectar();
    }

    public void conectar() {
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(URL, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (conexion != null || conexion.isClosed()) {
                conectar();
            }
        } catch (SQLException e) {
            System.out.println("Error al reconectar: " + e.getSQLState());
        }
        return conexion;
    }
}
