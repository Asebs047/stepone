package org.stepone.controller;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.stepone.db.Conexion;
import org.stepone.report.Report;
import org.stepone.system.Main;

/**
 *
 * @author reyes
 */
public class MenuAdministracionController implements Initializable{
    private Main principal;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @FXML
    private Button btnReporte, btnReporteVenta;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    private Map<String, Object>  parametros;
    private InputStream cargarReporte(String urlReporte){
        InputStream reporte = null;
        try {
        reporte = Main.class.getResourceAsStream(urlReporte);
        reporte.getClass().getResource(urlReporte);           
        } catch (Exception e) {
            System.out.println("Error al cargar reporte: " + urlReporte+e.getMessage());
            e.printStackTrace();
        }
        return reporte;
    }
    public void imprimirReporte(){
        Connection conexion = Conexion.getInstancia().getConexion();
        parametros = new HashMap<String, Object>();
        Report.generarReporte(conexion, parametros, cargarReporte(
                "/reports/Inventario.jasper"));
        Report.mostrarReporte();
    }
    
    public void imprimirReporteVentas(){
        Connection conexion = Conexion.getInstancia().getConexion();
        parametros = new HashMap<String, Object>();
        Report.generarReporte(conexion, parametros, cargarReporte(
                "/reports/Ventas.jasper"));
        Report.mostrarReporte();
    }
    

    
}
