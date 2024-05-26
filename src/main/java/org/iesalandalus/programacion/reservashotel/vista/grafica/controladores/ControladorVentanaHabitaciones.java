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
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ControladorVentanaHabitaciones {

    @FXML private TableView<Habitacion> tvHabitaciones;
    @FXML private TableColumn<Habitacion, String> tcIdentificador;
    @FXML private TableColumn<Habitacion, String> tcPlantaPuerta;
    @FXML private TableColumn<Habitacion, String> tcTipoHabitacion;
    @FXML private TableColumn<Habitacion, String> tcDatosHabitacion;
    @FXML private TextField tfPlanta;
    @FXML private TextField tfPuerta;
    @FXML private TextField tfPrecio;
    @FXML private ChoiceBox<TipoHabitacion> cbTipoHabitacion;
    @FXML private TextField tfCamasIndividuales;
    @FXML private TextField tfCamasDobles;
    @FXML private TextField tfBanos;
    @FXML private CheckBox chkbJacuzziSi;
    @FXML private TextField tfIdBorrar;
    @FXML private TextField tfIdBuscar;
    @FXML private TextField tfIdBuscarAsc;
    @FXML private TextField tfIdBuscarDesc;
    private ObservableList<Habitacion> obsHabitaciones= FXCollections.observableArrayList();
    private List<Habitacion> coleccionHabitaciones = new ArrayList<>();


    private void cargarDatosHabitaciones()
    {
        Controlador controlador = VistaGrafica.getInstancia().getControlador();
        List<Habitacion> huespedesAAnadir = controlador.getHabitaciones();
        coleccionHabitaciones.addAll(huespedesAAnadir);
        obsHabitaciones.setAll(coleccionHabitaciones);
    }
    @FXML
    private void initialize(){
        cargarDatosHabitaciones();
        tfIdBuscar.textProperty().addListener((observable, oldValue, newValue) -> filtraHabitaciones(newValue));
        tfIdBuscarAsc.textProperty().addListener((observable, oldValue, newValue) -> filtraHabitacionesAsc(newValue));
        tfIdBuscarDesc.textProperty().addListener((observable, oldValue, newValue) -> filtraHabitacionesDesc(newValue));
        tcIdentificador.setCellValueFactory(habitacion-> new SimpleStringProperty(habitacion.getValue().getIdentificador()));
        tcPlantaPuerta.setCellValueFactory(habitacion -> new SimpleStringProperty("Planta: " + habitacion.getValue().getPlanta() + ", " + "Puerta: " + habitacion.getValue().getPuerta()));
        tcTipoHabitacion.setCellValueFactory(habitacion -> new SimpleStringProperty(habitacion.getValue().getClass().getSimpleName()));
        tcDatosHabitacion.setCellValueFactory(habitacion -> {
            String datosHabitacion = "";
            if (habitacion.getValue() instanceof Simple) {
                Simple simple = (Simple) habitacion.getValue();
                datosHabitacion = "Precio de la habitación: " + simple.getPrecio() + "€";
            } else if (habitacion.getValue() instanceof Doble) {
                Doble doble = (Doble) habitacion.getValue();
                datosHabitacion = "Precio de la habitación: " + doble.getPrecio() + "€" +  ", Camas individuales: " + doble.getNumCamasIndividuales() + ", Camas dobles: " + doble.getNumCamasDobles();
            } else if (habitacion.getValue() instanceof Triple) {
                Triple triple = (Triple) habitacion.getValue();
                datosHabitacion = "Precio de la habitación: " + triple.getPrecio() + "€" + ", Camas individuales: " + triple.getNumCamasIndividuales() + ", Camas dobles: " + triple.getNumCamasDobles() + ", Baños: " + triple.getNumBanos();
            } else if (habitacion.getValue() instanceof Suite) {
                Suite suite = (Suite) habitacion.getValue();
                datosHabitacion = "Precio de la habitación: " + suite.getPrecio() + "€" + ", Baños: " + suite.getNumBanos() + ", Tiene Jacuzzi: " + (suite.isTieneJacuzzi() ? "Si" : "No");
            }
            return new SimpleStringProperty(datosHabitacion);
        });
        cbTipoHabitacion.getItems().addAll(TipoHabitacion.values());
        tvHabitaciones.setItems(obsHabitaciones);
    }
    private void filtraHabitaciones(String newValue)
    {
        FilteredList<Habitacion> filtradoHabitaciones = new FilteredList<>(obsHabitaciones, habitacion-> true);
        filtradoHabitaciones.setPredicate(habitacion -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return true;
            String cadenaFiltrado = newValue.toLowerCase();
            return habitacion.getIdentificador().toLowerCase().startsWith(cadenaFiltrado);
        });
        tvHabitaciones.setItems(filtradoHabitaciones);
    }
    private void filtraHabitacionesAsc(String newValue)
    {
        FilteredList<Habitacion> filtradoPersonas = new FilteredList<>(obsHabitaciones, habitacion-> true);
        filtradoPersonas.setPredicate(habitacion -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return true;
            String cadenaFiltrado = newValue.toLowerCase();
            return habitacion.getIdentificador().toLowerCase().startsWith(cadenaFiltrado);
        });
        List<Habitacion> listaMutable = new ArrayList<>(filtradoPersonas);
        Collections.sort(listaMutable, Comparator.comparing(Habitacion::getIdentificador));
        tvHabitaciones.setItems(FXCollections.observableArrayList(listaMutable));
    }
    private void filtraHabitacionesDesc(String newValue)
    {
        FilteredList<Habitacion> filtradoPersonas = new FilteredList<>(obsHabitaciones, habitacion-> true);
        filtradoPersonas.setPredicate(habitacion -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return true;
            String cadenaFiltrado = newValue.toLowerCase();
            return habitacion.getIdentificador().toLowerCase().startsWith(cadenaFiltrado);
        });
        List<Habitacion> listaMutable = new ArrayList<>(filtradoPersonas);
        Collections.sort(listaMutable, Comparator.comparing(Habitacion::getIdentificador).reversed());
        tvHabitaciones.setItems(FXCollections.observableArrayList(listaMutable));
    }

    @FXML void insertarHabitacion() {
        try {
            Controlador controlador = VistaGrafica.getInstancia().getControlador();
            Habitacion habitacion;

            if (cbTipoHabitacion.getValue() == TipoHabitacion.values()[1]){
                if (tfPlanta.getText().isBlank() || tfPuerta.getText().isBlank() || tfPrecio.getText().isBlank()){
                    throw new NullPointerException("ERROR: Algún dato sobre la habitación está vacío. No se pueden introducír datos nulos. Introduce los datos necesarios para una habitación Simple.");
                }
                habitacion = new Simple(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()));

            } else if (cbTipoHabitacion.getValue() == TipoHabitacion.values()[2]){
                if (tfPlanta.getText().isBlank() || tfPuerta.getText().isBlank() || tfPrecio.getText().isBlank() || tfCamasIndividuales.getText().isBlank() || tfCamasDobles.getText().isBlank()){
                    throw new NullPointerException("ERROR: Algún dato sobre la habitación está vacío. No se pueden introducír datos nulos. Introduce los datos necesarios para una habitación Doble.");
                }
                habitacion = new Doble(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()), Integer.parseInt(tfCamasIndividuales.getText()), Integer.parseInt(tfCamasDobles.getText()));

            } else if (cbTipoHabitacion.getValue() == TipoHabitacion.values()[3]){
                if (tfPlanta.getText().isBlank() || tfPuerta.getText().isBlank() || tfPrecio.getText().isBlank() || tfCamasIndividuales.getText().isBlank() || tfCamasDobles.getText().isBlank() || tfBanos.getText().isBlank()){
                    throw new NullPointerException("ERROR: Algún dato sobre la habitación está vacío. No se pueden introducír datos nulos. Introduce los datos necesarios para una habitación Triple.");
                }
                habitacion = new Triple(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()), Integer.parseInt(tfBanos.getText()), Integer.parseInt(tfCamasIndividuales.getText()), Integer.parseInt(tfCamasDobles.getText()));

            } else if (cbTipoHabitacion.getValue() == TipoHabitacion.values()[0]){
                if (tfPlanta.getText().isBlank() || tfPuerta.getText().isBlank() || tfPrecio.getText().isBlank() || tfBanos.getText().isBlank()){
                    throw new NullPointerException("ERROR: Algún dato sobre la habitación está vacío. No se pueden introducír datos nulos. Introduce los datos necesarios para una habitación Suite.");
                }
                habitacion = new Suite(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()), Integer.parseInt(tfBanos.getText()), (chkbJacuzziSi.isSelected()));

            } else {
                throw new NullPointerException("ERROR: El tipo de habitación no puede ser nulo");
            }

            if (!coleccionHabitaciones.contains(habitacion)) {
                controlador.insertar(habitacion);
                actualizarListaHabitaciones();
                Dialogos.mostrarDialogoInformacion("ReservasHotel v5 - Insertar Habitación", "Habitación insertada con éxito");
            }
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Insertar Habitación", e.getMessage());
        }
    }

    @FXML void borrarHabitacion(ActionEvent event){
        try {
            Controlador controlador = VistaGrafica.getInstancia().getControlador();
            Habitacion habitacion = null;
            if (tfIdBorrar.getText().isBlank() || !tfIdBorrar.getText().matches("\\d+")){
                throw new IllegalArgumentException("ERROR: El identificador tiene un formato no válido o es nulo");
            }

            String idHabitacionABorrar = tfIdBorrar.getText();
            int planta = 0, puerta = 0;
            int entero = Integer.parseInt(idHabitacionABorrar);
            if (entero >= 10 && entero <= 99) {
                planta = entero / 10;
                puerta = entero % 10;
            } else if (entero >= 100 && entero <= 999) {
                planta = entero / 100;
                puerta = entero % 100;
            }

            habitacion = new Simple(planta, puerta, 40);

            List<Reserva> reservasHabitacion = controlador.getReservas(habitacion);
            for (int i = 0; i < reservasHabitacion.size(); i++) {
                if (reservasHabitacion.get(i).getHabitacion().equals(habitacion)){
                    throw new OperationNotSupportedException("ERROR: No se puede borrar una habitación que tiene, al menos, una reserva asociada.");
                }
            }
            if (Dialogos.mostrarDialogoConfirmacion("ReservasHotel v5 - Borrar Habitación", "ADVERTENCIA: Seguro que quiere eliminar a este habitación? " + "(Identificador: " + tfIdBorrar.getText() + ")"))
            {
                controlador.borrar(habitacion);
                Dialogos.mostrarDialogoInformacion("ReservasHotel v5 - Borrar Habitación", "Habitación borrada con éxito.");
            }
            else event.consume();
            actualizarListaHabitaciones();
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Borrar Huesped", e.getMessage());
        }
    }
    @FXML private void actualizarListaHabitaciones()
    {
        obsHabitaciones = FXCollections.observableArrayList();
        coleccionHabitaciones = new ArrayList<>();
        tvHabitaciones.setItems(obsHabitaciones);
        cargarDatosHabitaciones();
    }
    @FXML public void mostrarReservasHabitacion(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaReservasHabitaciones.fxml"));
        try {
            Controlador controlador = VistaGrafica.getInstancia().getControlador();
            Habitacion habitacionId;
            if (tfIdBorrar.getText().isBlank() || !tfIdBorrar.getText().matches("\\d+")){
                throw new IllegalArgumentException("ERROR: El identificador tiene un formato no válido o es nulo");
            }
            String idHabitacionABorrar = tfIdBorrar.getText();
            int planta = 0, puerta = 0;
            int entero = Integer.parseInt(idHabitacionABorrar);
            if (entero >= 10 && entero <= 99) {
                planta = entero / 10;
                puerta = entero % 10;
            } else if (entero >= 100 && entero <= 999) {
                planta = entero / 100;
                puerta = entero % 100;
            }

            habitacionId = new Simple(planta, puerta, 40);
            Habitacion habitacion = controlador.buscar(habitacionId);

            Parent raiz = fxmlLoader.load();
            ControladorVentanaReservasHabitacion controladorReservasHabitacion = fxmlLoader.getController();
            controladorReservasHabitacion.recibirHabitacion(habitacion);

            Scene escena = new Scene(raiz, 709, 439);
            Stage ventanaReservasHuespedes = new Stage();
            ventanaReservasHuespedes.setTitle("Reservas de la Habitación");
            ventanaReservasHuespedes.setScene(escena);
            ventanaReservasHuespedes.setResizable(false);
            ventanaReservasHuespedes.showAndWait();
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Buscar reservas de una Habitación", e.getMessage());
        }
    }
}