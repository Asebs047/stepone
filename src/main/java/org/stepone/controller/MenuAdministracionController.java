package org.stepone.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
