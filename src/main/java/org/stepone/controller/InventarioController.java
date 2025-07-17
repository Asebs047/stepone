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
import javafx.scene.control.TableView;
import org.stepone.system.Main;

/**
 * FXML Controller class
 *
 * @author Klopez
 */
public class InventarioController implements Initializable {
    private Main principal;
    
    @FXML
    private Button btnRegresar, btnAnterior, btnSiguiente, btnEditar, btnEliminar, btnNuevo, btnBuscar;
//    @FXML
//    private TableView<Inventario> tablaInventario;
    
    public void setPrincipal(Main principal){
        this.principal=principal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void clickManejadorEventos(ActionEvent e){
        if (e.getSource()== btnRegresar) {
            principal.getMenuAdminView();
            
        }
    }
    
    
    
}
