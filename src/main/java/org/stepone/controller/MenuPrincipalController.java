/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.stepone.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.stepone.system.Main;

/**
 * FXML Controller class
 *
 * @author Klopez
 */
public class MenuPrincipalController implements Initializable {
    private Main principal;

    public void setPrincipal(Main principal){
        this.principal=principal;
    }
    
    @FXML
    private Button btnLogin, btnCatalogo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
    }    
    
    @FXML
    public void clickManejadorEventos(ActionEvent e){
        if (e.getSource()== btnLogin) {
            principal.getLoginView();
        } else if (e.getSource()== btnCatalogo){
            principal.getCatalogoView();
        }
    }
    
   
    
}
