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
public class Productos implements Initializable {
    
    private Main principal;
    
    @FXML private Button btnMenuCliente;
    @FXML private Button btnMenuCitas;
    @FXML private Button btnMenuMascotas;
    @FXML private Button btnRegresar;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
//    public void clickManejadorEventos(ActionEvent evento) {
//        if(evento.getSource()==btnMenuCliente) {
//            principal.getClientesView();
//        }else if (evento.getSource()==btnMenuCitas) {
//            principal.getCitasView();
//        }else if (evento.getSource()==btnMenuMascotas) {
//            principal.getMascotaView();
//        }else if (evento.getSource()==btnRegresar) {
//            principal.getMenuPrincipalView();
//        }
//    }
   
}
