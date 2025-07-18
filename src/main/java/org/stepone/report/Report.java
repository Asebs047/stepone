package org.stepone.report;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author reyes
 */
public class Report {
    private static JasperReport jreport;
    private static JasperViewer jviewer;
    private static JasperPrint jprint;
    
    public static void generarReporte(Connection conexion,
            Map<String, Object> parametros, InputStream reporte){
        
        try {
            jreport = (JasperReport) JRLoader.loadObject(reporte);
            jprint = JasperFillManager.fillReport(jreport, parametros, conexion);
        } catch (JRException ex) {
            System.out.println("error al henerar un reporte: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void mostrarReporte(){
        jviewer = new JasperViewer(jprint, false);
        jviewer.setVisible(true);
    }
    
}
