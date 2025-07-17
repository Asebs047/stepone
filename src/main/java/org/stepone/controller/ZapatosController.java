/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package org.stepone.controller;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.stepone.db.Conexion;
import org.stepone.model.Zapatos;
import org.stepone.system.Main;

/**
 * FXML Controller class
 *
 * @author Klopez
 */
public class ZapatosController implements Initializable {

    @FXML private Button btnAtras,btnNuevo,btnEditar,btnEliminar,btnSiguiente,btnBuscar,btnGuardar,btnCancelar,btnRegresar;
    @FXML private TableView<Zapatos> tablaZapato;
    @FXML private TableColumn colId, colNombre, colMarca, colTalla, colColor, colPrecio, colGenero;
    @FXML private TextField txtId, txtNombre, txtMarca, txtTalla, txtColor, txtPrecio, txtGenero, txtBuscar;
    @FXML private AnchorPane apFormulario;
    
    private ObservableList<Zapatos> listaZapatos;
    private Main principal;
    private Zapatos modeloZapatos;

    private enum Acciones {Agregar, Editar, Eliminar, Ninguna}
    private Acciones accion = Acciones.Ninguna;

    public void setPrincipal(Main principal) {
        this.principal = principal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarCeldas();
        cargarDatos();
        tablaZapato.setOnMouseClicked(e -> getZapatosTextField());
        txtNombre.setDisable(true);
        txtMarca.setDisable(true);
        txtTalla.setDisable(true);
        txtColor.setDisable(true);
        txtPrecio.setDisable(true);
        txtGenero.setDisable(true);
    }

    public void configurarCeldas() {
        colId.setCellValueFactory(new PropertyValueFactory <Zapatos, Integer>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory <Zapatos, String>("nombre"));
        colMarca.setCellValueFactory(new PropertyValueFactory <Zapatos, String>("apellido"));
        colTalla.setCellValueFactory(new PropertyValueFactory <Zapatos, Double>("telefono"));
        colColor.setCellValueFactory(new PropertyValueFactory <Zapatos, String>("cargo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory <Zapatos, Double>("correo"));
        colGenero.setCellValueFactory(new PropertyValueFactory <Zapatos, String>("correo"));
    }

    public void cargarDatos() {
        listaZapatos = FXCollections.observableArrayList(listarEmpleado());
        tablaZapato.setItems(listaZapatos);
        tablaZapato.getSelectionModel().selectFirst();
        getZapatosTextField();
    }

    public void getZapatosTextField(){
        Zapatos ZapatosSeleccionado = tablaZapato.getSelectionModel().getSelectedItem();
        if (ZapatosSeleccionado != null) {
            txtId.setText(String.valueOf(ZapatosSeleccionado.getId()));
            txtNombre.setText(ZapatosSeleccionado.getNombre());
            txtMarca.setText(ZapatosSeleccionado.getMarca());
            txtTalla.setText(ZapatosSeleccionado.getTalla());
            txtColor.setText(ZapatosSeleccionado.getColor());
            txtPrecio.setText(ZapatosSeleccionado.getPrecio());
            txtGenero.setText(ZapatosSeleccionado.getGenero());
        }
    }
    
    public ArrayList<Zapatos> listarEmpleado() {
        ArrayList<Zapatos> zapatos = new ArrayList<>();
        try {
            Connection conexionv = Conexion.getInstancia().getConexion();
            String sql = "{call sp_ListarEmpleados()}";
            CallableStatement enunciado = conexionv.prepareCall(sql);
            ResultSet resultado = enunciado.executeQuery(sql);
            while (resultado.next()) {
                zapatos.add(new Zapatos(
                    resultado.getInt(1),
                    resultado.getString(2),
                    resultado.getString(3),
                    resultado.getDouble(4),
                    resultado.getString(5),
                    resultado.getDouble(6),
                    resultado.getString(7)
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
        return zapatos;
    }


    private Zapatos getModeloZapatos() {
        int id;
        if (txtId.getText().isEmpty()) {
            //VERDADERO
            id = 1;
        }else{
            id = Integer.parseInt(txtId.getText());
        }
        String nombre = txtNombre.getText();
        String marca = txtMarca.getText();
        String talla = txtTalla.getText();
        String color = txtColor.getText();
        String precio = txtPrecio.getText();
        String genero = txtGenero.getText();
        Zapatos zapatos = new Zapatos(id, nombre,marca, talla, color, precio, genero);
        return zapatos;
    }

    public void agregarZapatos() {
        modeloZapatos = getModeloZapatos();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarZapato(?,?,?,?,?,?)}");
            enunciado.setString(1, modeloZapatos.getNombre());
            enunciado.setString(2, modeloZapatos.getMarca());
            enunciado.setDouble(3, modeloZapatos.getTalla());
            enunciado.setString(4, modeloZapatos.getColor());
            enunciado.setDouble(5, modeloZapatos.getPrecio());
            enunciado.setString(6, modeloZapatos.getGenero());
            enunciado.execute();
            cargarDatos();
            System.out.println("Empleado agregado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al agregar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void editarEmpleado() {
        modeloZapatos = getModeloZapatos();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarZapato(?,?,?,?,?,?,?)}");
            enunciado.setInt(1, modeloZapatos.getId());
            enunciado.setString(2, modeloZapatos.getNombre());
            enunciado.setString(3, modeloZapatos.getMarca());
            enunciado.setDouble(4, modeloZapatos.getTalla());
            enunciado.setString(5, modeloZapatos.getColor());
            enunciado.setDouble(6, modeloZapatos.getPrecio());
            enunciado.setString(7, modeloZapatos.getGenero());
            enunciado.execute();
            cargarDatos();
            System.out.println("Empleado actualizado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al editar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarEmpleado() {
        modeloZapatos = getModeloZapatos();
        try {
            CallableStatement enunciado = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarZapato(?)}");
            enunciado.setInt(1, modeloZapatos.getId());
            enunciado.execute();
            cargarDatos();
            System.out.println("Empleado eliminado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            e.printStackTrace();   
        }
    }

    @FXML
    public void cambiarNuevoEmpleado() {
        if(null != accion)
        switch (accion) {
            case Ninguna:
                limpiarTexto();
                cambiarGuardarEditar();
                accion = Acciones.Agregar;
                habilitarDeshabilitarNodo();
                break;
            case Agregar:
                if (ValidazionCampos()) {
                    agregarZapatos();
                    cambiarOriginal();
                    habilitarDeshabilitarNodo();
                }else{
                    System.out.println("Por favor, llena todos los campos antes de guardar.");
                }
                break;
            case Editar:
                if (ValidazionCampos()) {
                    editarEmpleado();
                    cambiarOriginal();
                    habilitarDeshabilitarNodo();
                }else{
                    System.out.println("Por favor, llena todos los campos antes de guardar.");
                }
                break;
            default:
                break;
        }
    }

    @FXML
    public void cambiarEdicionZapatos() {
        cambiarGuardarEditar();
        accion = Acciones.Editar;
        habilitarDeshabilitarNodo();
    }

    @FXML
    public void cambiarCancelarEliminar() {
        if (accion == Acciones.Ninguna) {
            eliminarEmpleado();
        } else {
            cambiarOriginal();
            habilitarDeshabilitarNodo();
        }
    }

    @FXML
    public void btnAnteriorAccion() {
        int indice = tablaZapato.getSelectionModel().getSelectedIndex();
        if (indice > 0) {
            tablaZapato.getSelectionModel().select(indice - 1);
            getZapatosTextField();
        }
    }

    @FXML
    public void btnSiguienteAccion() {
        int indice = tablaZapato.getSelectionModel().getSelectedIndex();
        if (indice < listaZapatos.size() - 1) {
            tablaZapato.getSelectionModel().select(indice + 1);
            getZapatosTextField();
        }
    }

    public void limpiarTexto() {
        txtId.clear();
        txtNombre.clear();
        txtMarca.clear();
        txtTalla.clear();
        txtColor.clear();
        txtPrecio.clear();
        txtGenero.clear();
    }

    private void cambiarGuardarEditar() {
        btnNuevo.setDisable(true);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        btnBuscar.setDisable(true);
        btnRegresar.setDisable(true);
        txtBuscar.setDisable(true);
    }

    private void cambiarOriginal() {
        btnNuevo.setDisable(false);
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnBuscar.setDisable(false);
        btnRegresar.setDisable(false);
        txtBuscar.setDisable(false);
        accion = Acciones.Ninguna;
    }
    
    @FXML
    private void accionguardar(){
        if(null != accion)
        switch (accion) {
            case Ninguna:
                cambiarGuardarEditar();
                accion = Acciones.Agregar;
                habilitarDeshabilitarNodo();
                break;
            case Agregar:
                if (ValidazionCampos()) {
                    agregarZapatos();
                    cambiarOriginal();
                    habilitarDeshabilitarNodo();
                }else{
                    System.out.println("Por favor, llena todos los campos antes de guardar.");
                }
                break;
            case Editar:
                if (ValidazionCampos()) {
                    editarEmpleado();
                    cambiarOriginal();
                    habilitarDeshabilitarNodo();
                }else{
                    System.out.println("Por favor, llena todos los campos antes de guardar.");
                }
                break;
            default:
                break;
        }
    }
    
    @FXML
    private void accioncancelar(){
        cambiarOriginal();
        habilitarDeshabilitarNodo();
    }

    private void cambiarEstado(boolean estado) {
        txtNombre.setDisable(estado);
        txtMarca.setDisable(estado);
        txtTalla.setDisable(estado);
        txtColor.setDisable(estado);
        txtPrecio.setDisable(estado);
        txtGenero.setDisable(estado);
    }

    private void habilitarDeshabilitarNodo() {
        boolean deshabilitado = txtNombre.isDisable();
        cambiarEstado(!deshabilitado);
        tablaZapato.setDisable(deshabilitado);
        
        
        apFormulario.setVisible(deshabilitado);
        apFormulario.setManaged(deshabilitado);
        
        Stage stage = (Stage) apFormulario.getScene().getWindow();
        stage.setWidth(deshabilitado ? 1239 : 803);
        
        btnSiguiente.setDisable(deshabilitado);
        btnAtras.setDisable(deshabilitado);
    }

    public void btnRegresarActionEvent(ActionEvent evento) {
        principal.getMenuPrincipal();
    }
    
    private boolean buscando = false;
    @FXML
    private void btnBuscarPorNombre() {
        if (!buscando) {
            // Modo buscar
            ArrayList<Zapatos> resultadoBusqueda = new ArrayList<>();
            String nombreBuscado = txtBuscar.getText();

            for (Zapatos empleado : listaZapatos) {
                if (empleado.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase())) {
                    resultadoBusqueda.add(empleado);
                }
            }
            tablaZapato.setItems(FXCollections.observableArrayList(resultadoBusqueda));
            if (resultadoBusqueda.isEmpty()) {
                tablaZapato.getSelectionModel().clearSelection();
            }
            
            btnBuscar.setText("Cancelar");
            buscando = true;
        } else {
            // Modo cancelar
            tablaZapato.setItems(FXCollections.observableArrayList(listaZapatos));
            txtBuscar.setText("");
            btnBuscar.setText("Buscar");
            buscando = false;
        }
    }
    
    @FXML
    private boolean ValidazionCampos(){
        if (txtNombre.getText().isEmpty()||
            txtMarca.getText().isEmpty()||
            txtTalla.getText().isEmpty()||
            txtColor.getText().isEmpty()||
            txtPrecio.getText().isEmpty()||
            txtGenero.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    
    
}
