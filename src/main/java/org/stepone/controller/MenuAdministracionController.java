package org.stepone.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Button btnInventario, btnVentas;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
//    @FXML
//    public void clickManejadorEventos(ActionEvent e){
//        if (e.getSource()== btnInventario) {
//            principal.getInventarioView();
//        } else if (e.getSource()== btnVentas){
//            principal.getVentasView();
//        }
//    }
    
}
