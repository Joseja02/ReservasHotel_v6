package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControladorVentanaReservasHabitacion {

    private static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    private Label lblHabitacion;
    @FXML private TableView<Reserva> tvReservasHabitacion;
    @FXML private TableColumn<Reserva, String> tcNombreDni;
    @FXML private TableColumn<Reserva, String> tcIdTipo;
    @FXML private TableColumn<Reserva, Double> tcImporte;
    @FXML private TableColumn<Reserva, String> tcFechaInicio;
    @FXML private TableColumn<Reserva, String> tcFechaFin;
    private Habitacion habitacion;
    private List<Reserva> coleccionReservasHabitacion = new ArrayList<>();
    private ObservableList<Reserva> obsReservasHabitacion = FXCollections.observableArrayList();
    @FXML private void initialize(){
        lblHabitacion.setText("AVISO: No hay ninguna habitación cargada. Pulsa el botón de abajo para cargar las reservas de la habitación");
        tcNombreDni.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre() + ", " +  reserva.getValue().getHuesped().getDni()));
        tcIdTipo.setCellValueFactory(reserva ->  new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador() + ", " + getTipoHabitacion(reserva.getValue().getHabitacion())));
        tcImporte.setCellValueFactory(reserva -> new ReadOnlyObjectWrapper<>(reserva.getValue().getPrecio()));
        tcFechaInicio.setCellValueFactory(reserva->new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(FORMATO_FECHA).toString()));
        tcFechaFin.setCellValueFactory(reserva->new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(FORMATO_FECHA).toString()));
        tvReservasHabitacion.setItems(obsReservasHabitacion);
    }
    public void recibirHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }
    @FXML private void cargarDatosReservasHabitacion(){
        try {
            obsReservasHabitacion = FXCollections.observableArrayList();
            coleccionReservasHabitacion = new ArrayList<>();
            Controlador controlador = VistaGrafica.getInstancia().getControlador();
            List<Reserva> reservasAAnadir = controlador.getReservas(habitacion);
            coleccionReservasHabitacion.addAll(reservasAAnadir);
            obsReservasHabitacion.setAll(coleccionReservasHabitacion);
            initialize();
            lblHabitacion.setText(habitacion.getIdentificador() + ", " + getTipoHabitacion(habitacion));
        } catch (NullPointerException | IllegalArgumentException e){
            Dialogos.mostrarDialogoAdvertencia("ReservasHotel v5 - Buscar reservas de una habitación", e.getMessage());
        }
    }
    private String getTipoHabitacion(Habitacion habitacion){
        TipoHabitacion tipoHabitacion = null;
        if (habitacion instanceof Simple) tipoHabitacion = TipoHabitacion.SIMPLE;
        if (habitacion instanceof Doble) tipoHabitacion = TipoHabitacion.DOBLE;
        if (habitacion instanceof Triple) tipoHabitacion = TipoHabitacion.TRIPLE;
        if (habitacion instanceof Suite) tipoHabitacion = TipoHabitacion.SUITE;
        return tipoHabitacion.name();
    }
}
