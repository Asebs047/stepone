/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.stepone.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.stepone.system.Main;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;

import javafx.stage.Stage;

import org.stepone.db.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 *
 * @author Klopez
 */
public class IniciarSecionController implements Initializable {
    private Main principal;
    
    @FXML private Button BtnCancelar, BtnLogin;
    @FXML private TextField TxtUsuario;
    @FXML private PasswordField PassContraseña;
    @FXML private Label LabMensaje;
    
    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void LoginOnActionEvent(ActionEvent e){
        if (TxtUsuario.getText().isBlank() == false && PassContraseña.getText().isBlank() == false) {
            validarLogin();
        } else {
            LabMensaje.setText("Por favor ingresa un Usuario y contraseña");
        }
    }
      
    public void CancelarOnActionEvent(ActionEvent e){
        Stage stage = (Stage) BtnCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void validarLogin(){
        Conexion conexionLogin = new Conexion();
        Connection conexionDB = conexionLogin.getConexion();
        
        String usuario = TxtUsuario.getText();
        String contrasena = PassContraseña.getText();   
    
        String verificarLogin = "select count(1) from Usuarios where nombre = ? and contrasena = ?";
        
        try {
            PreparedStatement enunciado = conexionDB.prepareStatement(verificarLogin);
            enunciado.setString(1, usuario);
            enunciado.setString(2, contrasena);

            ResultSet resultado = enunciado.executeQuery();

            if (resultado.next()) {
                if (resultado.getInt(1) == 1) {
                    LabMensaje.setText("Bienvenido!");
                    principal.getCatalogoView();
                } else {
                    LabMensaje.setText("Datos Invalidos, intente de nuevo.");
                }
            }            
        } catch (Exception e) {
            e.printStackTrace();
            LabMensaje.setText("Error al conectar con la base de datos");
        } finally {
            try {
                if (conexionDB != null) conexionDB.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void CrearUsuarioActionEvent(ActionEvent e){
        principal.getNuevoUsuarioView();
    }  
    
}
