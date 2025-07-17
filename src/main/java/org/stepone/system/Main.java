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
            System.out.println("Error al ir a Inicio"+ex.getMessage());
            ex.printStackTrace();
        }
    }
//    --------------------------------------------------------------------------
    public void getP01View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P01.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P01: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP02View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P02.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P02: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP03View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P03.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P03: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP04View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P04.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P04: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP05View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P05.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P05: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP06View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P06.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P06: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP07View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P07.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P07: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP08View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P08.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P08: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP09View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P09.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P09: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP10View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P10.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P10: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP11View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P11.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P11: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP12View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P12.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P12: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP13View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P13.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P13: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP14View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P14.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P14: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP15View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P15.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P15: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP16View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P16.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P16: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP17View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P17.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P17: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP18View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P18.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P18: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP19View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P19.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P19: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP20View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("CatalogoView.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a Catalogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP21View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P21.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P21: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP22View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P22.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P22: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP23View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P23.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P23: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP24View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P24.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P24: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP25View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P25.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P25: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP26View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P26.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P26: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP27View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P27.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P27: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP28View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P28.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P28: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP29View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P29.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P29: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void getP30View(){
        try {
            CatalogoController control = (CatalogoController) cambiarEscena("P30.fxml", 920, 619);
            control.setPrincipal(this);
        } catch (Exception e) {
            System.out.println("Error al ir a P30: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
}
