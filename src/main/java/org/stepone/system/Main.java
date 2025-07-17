package org.stepone.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.stepone.controller.CatalogoController;
import org.stepone.controller.RegistrarseController;

import org.stepone.controller.IniciarSecionController;
import org.stepone.controller.MenuAdministracionController;
import org.stepone.controller.MenuPrincipalController;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
*/
/**
 *
 * @author emili
 */
public class Main extends Application {
    private Stage escenarioPrincipal;
    private Scene siguienteEscena;
    private static String URL = "/view/";
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception{
        this.escenarioPrincipal = stage;
        getMenuPrincipal();
        stage.setTitle("StepOne");
        stage.show();
    }
    
    public Initializable cambiarEscena(String fxml, double ancho, double alto) throws Exception {
        Initializable interfazDeCambio = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivoFXML = Main.class.getResourceAsStream(URL + fxml); 
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Main.class.getResource(URL + fxml));
        siguienteEscena = new Scene(cargadorFXML.load(archivoFXML), ancho, alto);
        escenarioPrincipal.setScene(siguienteEscena);
        escenarioPrincipal.sizeToScene();
        interfazDeCambio = cargadorFXML.getController();
        return interfazDeCambio;
    }
    
    public void getMenuPrincipal(){
        try {
            MenuPrincipalController control =(MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 877, 584);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a Menu Principal: "+ e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getLoginView(){
        try {
            IniciarSecionController control = (IniciarSecionController) cambiarEscena("IniciarSecionView.fxml", 520, 400);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a Login: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getCatalogoView(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("CatalogoView.fxml", 1740, 1280);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a Catalogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getNuevoUsuarioView(){
        try {
            RegistrarseController control =
                    (RegistrarseController) cambiarEscena("RegistrarseView.fxml",520,400);
            control.setPrincipal(this);
        } catch (Exception ex) {
            System.out.println("Error al ir a Inicio: "+ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void getMenuAdminView(){
        try {
            MenuAdministracionController control =
                    (MenuAdministracionController) cambiarEscena("MenuAdminView.fxml", 870, 500);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a el menu de administrador: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
