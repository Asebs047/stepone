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
import javafx.scene.control.MenuItem;
import org.stepone.system.Main;

/**
 * FXML Controller class
 *
 * @author Klopez
 */
public class CatalogoController implements Initializable {
    private Main principal;

    public void setPrincipal(Main principal){
        this.principal=principal;
    }
    
    @FXML private Button btnP01, btnP02, btnP03, btnP04, btnP05;
    @FXML private Button btnP06, btnP07, btnP08, btnP09, btnP10;
    @FXML private Button btnP11, btnP12, btnP13, btnP14, btnP15;
    @FXML private Button btnP16, btnP17, btnP18, btnP19, btnP20;
    @FXML private Button btnP21, btnP22, btnP23, btnP24, btnP25;
    @FXML private Button btnP26, btnP27, btnP28, btnP29, btnP30, btnsalir;
    @FXML private MenuItem brnRegresar, btnCatalogo;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void clickManejadorEventos(ActionEvent evento){
        if (evento.getSource()== btnCatalogo){
            principal.getCatalogoView();
        } else if(evento.getSource()== brnRegresar) {
            principal.getMenuPrincipal();
        }else if (evento.getSource()==btnP01) {
            principal.getP01View();
        }else if (evento.getSource()==btnP02) {
            principal.getP02View();
        }else if (evento.getSource()==btnP03) {
            principal.getP03View();
        }else if (evento.getSource()==btnP04) {
            principal.getP04View();
        }else if (evento.getSource()==btnP05) {
            principal.getP05View();
        }else if (evento.getSource()==btnP06) {
            principal.getP06View();
        }else if (evento.getSource()==btnP07) {
            principal.getP07View();
        }else if (evento.getSource()==btnP08) {
            principal.getP08View();
        }else if (evento.getSource()==btnP09) {
            principal.getP09View();
        }else if (evento.getSource()==btnP10) {
            principal.getP10View();
        }else if (evento.getSource()==btnP11) {
            principal.getP11View();
        }else if (evento.getSource()==btnP12) {
            principal.getP12View();
        }else if (evento.getSource()==btnP13) {
            principal.getP13View();
        }else if (evento.getSource()==btnP14) {
            principal.getP14View();
        }else if (evento.getSource()==btnP15) {
            principal.getP15View();
        }else if (evento.getSource()==btnP16) {
            principal.getP16View();
        }else if (evento.getSource()==btnP17) {
            principal.getP17View();
        }else if (evento.getSource()==btnP18) {
            principal.getP18View();
        }else if (evento.getSource()==btnP19) {
            principal.getP19View();
        }else if (evento.getSource()==btnP20) {
            principal.getP20View();
        }else if (evento.getSource()==btnP21) {
            principal.getP21View();
        }else if (evento.getSource()==btnP22) {
            principal.getP22View();
        }else if (evento.getSource()==btnP23) {
            principal.getP23View();
        }else if (evento.getSource()==btnP24) {
            principal.getP24View();
        }else if (evento.getSource()==btnP25) {
            principal.getP25View();
        }else if (evento.getSource()==btnP26) {
            principal.getP26View();
        }else if (evento.getSource()==btnP27) {
            principal.getP27View();
        }else if (evento.getSource()==btnP28) {
            principal.getP28View();
        }else if (evento.getSource()==btnP29) {
            principal.getP29View();
        }else if (evento.getSource()==btnP30) {
            principal.getP30View();
        }else if (evento.getSource()== btnsalir){
            principal.getCatalogoView();
        }
    }
    
    
}
