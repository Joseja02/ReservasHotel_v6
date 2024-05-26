package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControladorVentanaReservasHuesped {

    private static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML private Label lblHuesped;
    @FXML private TableView<Reserva> tvReservasHuesped;
    @FXML private TableColumn<Reserva, String> tcNombreDni;
    @FXML private TableColumn<Reserva, String> tcIdTipo;
    @FXML private TableColumn<Reserva, Double> tcImporte;
    @FXML private TableColumn<Reserva, String> tcFechaInicio;
    @FXML private TableColumn<Reserva, String> tcFechaFin;
    private Huesped huesped;
    private List<Reserva> coleccionReservasHuesped = new ArrayList<>();
    private ObservableList<Reserva> obsReservasHuesped = FXCollections.observableArrayList();
    @FXML private void initialize(){
        lblHuesped.setText("AVISO: No hay ningún huésped cargado. Pulsa el botón de abajo para cargar las reservas del huésped");
        tcNombreDni.setCellValueFactory(reserva-> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre() + ", " +  reserva.getValue().getHuesped().getDni()));
        tcIdTipo.setCellValueFactory(reserva -> {
            Habitacion habitacion = reserva.getValue().getHabitacion();
            TipoHabitacion tipoHabitacion = null;
            if (habitacion instanceof Simple) tipoHabitacion = TipoHabitacion.SIMPLE;
            if (habitacion instanceof Doble) tipoHabitacion = TipoHabitacion.DOBLE;
            if (habitacion instanceof Triple) tipoHabitacion = TipoHabitacion.TRIPLE;
            if (habitacion instanceof Suite) tipoHabitacion = TipoHabitacion.SUITE;
            String identificador = habitacion.getIdentificador();
            return new SimpleStringProperty(identificador + ", " + tipoHabitacion.name());
        });
        
        tcImporte.setCellValueFactory(reserva -> new ReadOnlyObjectWrapper<>(reserva.getValue().getPrecio()));
        tcFechaInicio.setCellValueFactory(reserva->new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(FORMATO_FECHA).toString()));
        tcFechaFin.setCellValueFactory(reserva->new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(FORMATO_FECHA).toString()));
        tvReservasHuesped.setItems(obsReservasHuesped);
    }

    public void recibirHuesped(Huesped huesped){
        this.huesped = huesped;
    }

    @FXML private void cargarDatosReservasHuesped(){
        try {
            obsReservasHuesped = FXCollections.observableArrayList();
            coleccionReservasHuesped = new ArrayList<>();
            Controlador controlador = VistaGrafica.getInstancia().getControlador();
            List<Reserva> reservasAAnadir = controlador.getReservas(huesped);
            coleccionReservasHuesped.addAll(reservasAAnadir);
            obsReservasHuesped.setAll(coleccionReservasHuesped);
            initialize();
            lblHuesped.setText(huesped.getNombre() + ", " + huesped.getDni());
        } catch (NullPointerException | IllegalArgumentException e){
            Dialogos.mostrarDialogoAdvertencia("ReservasHotel v5 - Buscar reservas de un huésped", e.getMessage());
        }
    }
}
