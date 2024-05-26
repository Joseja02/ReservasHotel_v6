package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.reservashotel.vista.texto.Consola;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ControladorVentanaHuespedes {

    private static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML private TableView<Huesped> tvHuespedes;
    @FXML private TableColumn<Huesped, String> tcNombre;
    @FXML private TableColumn<Huesped, String> tcDni;
    @FXML private TableColumn<Huesped, String> tcCorreo;
    @FXML private TableColumn<Huesped, String> tcTelefono;
    @FXML private TableColumn<Huesped, String> tcFechaNacimiento;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField tfNombre;
    @FXML private TextField tfDni;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfDniBorrar;
    @FXML private TextField tfNombreBuscar;
    @FXML private TextField tfNombreBuscarAsc;
    @FXML private TextField tfNombreBuscarDesc;

    private ObservableList<Huesped> obsHuespedes= FXCollections.observableArrayList();
    private List<Huesped> coleccionHuespedes = new ArrayList<>();

    private void cargarDatosHuespedes()
    {
        Controlador controlador = VistaGrafica.getInstancia().getControlador();
        List<Huesped> huespedesAAnadir = controlador.getHuespedes();
        coleccionHuespedes.addAll(huespedesAAnadir);
        obsHuespedes.setAll(coleccionHuespedes);
    }
    @FXML
    private void initialize(){
        cargarDatosHuespedes();
        tfNombreBuscar.textProperty().addListener((observable, oldValue, newValue) -> filtraHuespedes(newValue));
        tfNombreBuscarAsc.textProperty().addListener((observable, oldValue, newValue) -> filtraHuespedesAsc(newValue));
        tfNombreBuscarDesc.textProperty().addListener((observable, oldValue, newValue) -> filtraHuespedesDesc(newValue));
        tcNombre.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getNombre()));
        tcDni.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getDni()));
        tcCorreo.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getCorreo()));
        tcTelefono.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getTelefono()));
        tcFechaNacimiento.setCellValueFactory(huesped->new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(FORMATO_FECHA).toString()));
        tvHuespedes.setItems(obsHuespedes);
    }

    private void filtraHuespedes(String newValue)
    {
        FilteredList<Huesped> filtradoHuespedes = new FilteredList<>(obsHuespedes, huesped-> true);
        filtradoHuespedes.setPredicate(huesped -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return true;
            String cadenaFiltrado = newValue.toLowerCase();
            return huesped.getNombre().toLowerCase().startsWith(cadenaFiltrado);
        });
        tvHuespedes.setItems(filtradoHuespedes);
    }
    private void filtraHuespedesAsc(String newValue)
    {
        FilteredList<Huesped> filtradoHuespedes = new FilteredList<>(obsHuespedes, huesped-> true);
        filtradoHuespedes.setPredicate(huesped -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return true;
            String cadenaFiltrado = newValue.toLowerCase();
            return huesped.getNombre().toLowerCase().startsWith(cadenaFiltrado);
        });
        List<Huesped> listaMutable = new ArrayList<>(filtradoHuespedes);
        Collections.sort(listaMutable, Comparator.comparing(Huesped::getNombre));
        tvHuespedes.setItems(FXCollections.observableArrayList(listaMutable));
    }
    private void filtraHuespedesDesc(String newValue)
    {
        FilteredList<Huesped> filtradoHuespedes = new FilteredList<>(obsHuespedes, huesped-> true);
        filtradoHuespedes.setPredicate(huesped -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return true;
            String cadenaFiltrado = newValue.toLowerCase();
            return huesped.getNombre().toLowerCase().startsWith(cadenaFiltrado);
        });
        List<Huesped> listaMutable = new ArrayList<>(filtradoHuespedes);
        Collections.sort(listaMutable, Comparator.comparing(Huesped::getNombre).reversed());
        tvHuespedes.setItems(FXCollections.observableArrayList(listaMutable));
    }

    @FXML void insertarHuesped() {
        try {
            Huesped huesped = new Huesped(tfNombre.getText(),tfDni.getText(), tfCorreo.getText(), tfTelefono.getText(), dpFechaNacimiento.getValue());
            Controlador controlador = VistaGrafica.getInstancia().getControlador();
            if (!coleccionHuespedes.contains(huesped)) {
                controlador.insertar(huesped);
                actualizarListaHuespedes();
                Dialogos.mostrarDialogoInformacion("ReservasHotel v5 - Insertar Huesped", "Huesped insertado con exito");
            }
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Insertar Huesped", e.getMessage());
        }
    }

    @FXML void borrarHuesped(ActionEvent event){
        try {
            Controlador controlador = VistaGrafica.getInstancia().getControlador();
            String dniHuespedABorrar = tfDniBorrar.getText();
            Huesped huesped = new Huesped("Nombre Ficticio", dniHuespedABorrar, "ficticio@test.com", "123456789", LocalDate.of(2002, 8, 19));
            List<Reserva> reservasHuesped = controlador.getReservas(huesped);
            for (int i = 0; i < reservasHuesped.size(); i++) {
                if (reservasHuesped.get(i).getHuesped().equals(huesped)){
                    throw new OperationNotSupportedException("ERROR: No se puede borrar un huésped que tiene, al menos, una reserva hecha.");
                }
            }

            if (Dialogos.mostrarDialogoConfirmacion("ReservasHotel v5 - Borrar Huesped", "ADVERTENCIA: Seguro que quiere eliminar a este huesped? " + "(DNI: " + tfDniBorrar.getText() + ")"))
            {
                controlador.borrar(huesped);
                Dialogos.mostrarDialogoInformacion("ReservasHotel v5 - Borrar Huesped", "Huesped borrado con exito.");
            }
            else event.consume();
            actualizarListaHuespedes();
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Borrar Huesped", e.getMessage());
        }
    }
    @FXML private void actualizarListaHuespedes()
    {
        obsHuespedes = FXCollections.observableArrayList();
        coleccionHuespedes = new ArrayList<>();
        tvHuespedes.setItems(obsHuespedes);
        cargarDatosHuespedes();
    }
    @FXML public void mostrarReservasHuesped(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaReservasHuesped.fxml"));
        try {
            Controlador controlador = VistaGrafica.getInstancia().getControlador();
            Huesped huespedDni = new Huesped("Nombre Ficticio", tfDniBorrar.getText(), "ficticio@test.com", "123456789", LocalDate.of(2002, 8, 19));
            Huesped huesped = controlador.buscar(huespedDni);

            Parent raiz = fxmlLoader.load();
            ControladorVentanaReservasHuesped controladorReservasHuesped = fxmlLoader.getController();
            controladorReservasHuesped.recibirHuesped(huesped);

            Scene escena = new Scene(raiz, 709, 439);
            Stage ventanaReservasHuespedes = new Stage();
            ventanaReservasHuespedes.setTitle("Reservas del Huesped");
            ventanaReservasHuespedes.setScene(escena);
            ventanaReservasHuespedes.setResizable(false);
            ventanaReservasHuespedes.showAndWait();
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
            Dialogos.mostrarDialogoAdvertencia("ReservasHotel v5 - Buscar reservas de un Huesped", e.getMessage());
        }
    }
}
