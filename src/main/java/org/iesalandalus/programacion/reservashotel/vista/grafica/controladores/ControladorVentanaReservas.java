package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ControladorVentanaReservas {
    Controlador controlador = VistaGrafica.getInstancia().getControlador();
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String FORMATO_FECHA_HORA_RESERVA = "dd/MM/yyyy HH:mm";
    @FXML private TableView<Reserva> tvReservas;
    @FXML private TableColumn<Reserva, String> tcNombreDni;
    @FXML private TableColumn<Reserva, String> tcIdTipo;
    @FXML private TableColumn<Reserva, Double> tcImporte;
    @FXML private TableColumn<Reserva, String> tcFechaInicio;
    @FXML private TableColumn<Reserva, String> tcFechaFin;
    @FXML private TableColumn<Reserva, String> tcCheckin;
    @FXML private TableColumn<Reserva, String> tcCheckout;
    @FXML private DatePicker dpFechaInicioReserva;
    @FXML private DatePicker dpFechaFinReserva;
    @FXML private Slider slNumPersonas;
    @FXML private Label lblNumPersonas;
    @FXML private TextField tfDniHuesped;
    @FXML private ComboBox<Reserva> cbEligeReserva;
    @FXML private ChoiceBox<TipoHabitacion> chbTipoHabitacion;
    @FXML private ChoiceBox<Regimen> chbRegimen;
    @FXML private TextField tfDniReservaABorrar;
    @FXML private TextField tfDniHuespedReservas;
    @FXML private TextField tfIdHabitacionReservas;

    private ObservableList<Reserva> obsReservas = FXCollections.observableArrayList();
    private List<Reserva> coleccionReservas = new ArrayList<>();

    private void cargarDatosReservas() {
        Controlador controlador = VistaGrafica.getInstancia().getControlador();
        List<Reserva> reservasAAnadir = controlador.getReservas();
        coleccionReservas.addAll(reservasAAnadir);
        obsReservas.setAll(coleccionReservas);
    }

    @FXML
    private void initialize() {
        cargarDatosReservas();
        setSlider();
        tfDniReservaABorrar.textProperty().addListener((observable, oldValue, newValue) -> filtraReservasABorrarDni(newValue));
        tfDniHuespedReservas.textProperty().addListener((observable, oldValue, newValue) -> filtraReservasDni(newValue));
        tfIdHabitacionReservas.textProperty().addListener((observable, oldValue, newValue) -> filtraReservasId(newValue));

        tcNombreDni.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre() + ", " + reserva.getValue().getHuesped().getDni()));
        tcIdTipo.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador() + ", " + getTipoHabitacion(reserva.getValue().getHabitacion())));
        tcImporte.setCellValueFactory(reserva -> new ReadOnlyObjectWrapper<>(reserva.getValue().getPrecio()));
        tcFechaInicio.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(FORMATO_FECHA)));
        tcFechaFin.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(FORMATO_FECHA)));
        tcCheckin.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getCheckIn() == null ? "No registrado" : reserva.getValue().getCheckIn().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA))));
        tcCheckout.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getCheckOut() == null ? "No registrado" : reserva.getValue().getCheckOut().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA))));
        chbRegimen.getItems().addAll(Regimen.values());
        chbTipoHabitacion.getItems().addAll(TipoHabitacion.values());
        tvReservas.setItems(obsReservas);
    }

    @FXML
    void insertarReserva() {
        try {
            Huesped huespedABuscar = new Huesped("Nombre Ficticio", tfDniHuesped.getText(), "ficticio@test.com", "123456789", LocalDate.of(2002, 8, 19));
            Huesped huesped = controlador.buscar(huespedABuscar);
            TipoHabitacion tipoHabitacionReserva = chbTipoHabitacion.getValue();

            LocalDate fechaInicioReserva = dpFechaInicioReserva.getValue();
            LocalDate fechaFinReserva = dpFechaFinReserva.getValue();

            Habitacion habitacionDisponible = consultarDisponibilidad(tipoHabitacionReserva, fechaInicioReserva, fechaFinReserva);
            Habitacion habitacion = controlador.buscar(habitacionDisponible);
            Regimen regimen = chbRegimen.getValue();

            Reserva reserva = new Reserva(huesped, habitacion, regimen, fechaInicioReserva, fechaFinReserva, (int) slNumPersonas.getValue());

            if (habitacionDisponible != null) {
                Reserva reservaExistente = controlador.buscar(reserva);

                if (reservaExistente == null) {
                    controlador.insertar(reserva);
                    actualizarListaReservas();
                    Dialogos.mostrarDialogoInformacion("ReservasHotel v5 - Insertar Reserva", "Reserva insertada con éxito");
                } else {
                    throw new OperationNotSupportedException("ERROR: No es posible registrar esta reserva porque ya existe otra reserva para la misma fecha y habitación seleccionada");
                }
            } else {
                throw new OperationNotSupportedException("ERROR: La habitación que intentas reservar no está disponible");
            }
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Insertar Reserva", e.getMessage());
        }
    }

    @FXML
    void borrarReserva(ActionEvent event) {
        try {
            Reserva reserva = cbEligeReserva.getValue();

            if (cbEligeReserva.getValue() == null){
                throw new NullPointerException("ERROR: No has elegido ninguna reserva.");
            }
            if (!(reserva.getCheckIn() == null)){
                throw new OperationNotSupportedException("ERROR: No puedes borrar esta reserva porque ya se ha realizado un checkin anteriormente.");
            }
            if (reserva.getFechaInicioReserva().isEqual(LocalDate.now()) || reserva.getFechaInicioReserva().isBefore(LocalDate.now())){
                throw new OperationNotSupportedException("ERROR: No puedes borrar esta reserva porque su fecha de inicio es anterior a la fecha actual u hoy es su fecha de inicio.");
            }
            if (Dialogos.mostrarDialogoConfirmacion("ReservasHotel v5 - Borrar Reserva", "ADVERTENCIA: Seguro que quiere eliminar esta reserva? " +
                    "(Reserva: " + cbEligeReserva.getValue().getHuesped().getNombre()
                    + ", " + cbEligeReserva.getValue().getHuesped().getDni()
                    + ", Fecha de inicio de reserva: " + cbEligeReserva.getValue().getFechaInicioReserva() + ")")) {
                controlador.borrar(reserva);
                Dialogos.mostrarDialogoInformacion("ReservasHotel v5 - Borrar Reserva", "Reserva borrada con éxito.");
            } else event.consume();
            actualizarListaReservas();
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Borrar Reserva", e.getMessage());
        }
    }

    private void filtraReservasDni(String newValue) {
        FilteredList<Reserva> filtradoReserva = new FilteredList<>(obsReservas, reserva -> true);
        filtradoReserva.setPredicate(reserva -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return true;
            String cadenaFiltrado = newValue.toLowerCase();
            return reserva.getHuesped().getDni().toLowerCase().startsWith(cadenaFiltrado);
        });
        List<Reserva> listaMutable = new ArrayList<>(filtradoReserva);
        Collections.sort(listaMutable, Comparator.comparing(Reserva::getFechaInicioReserva));
        tvReservas.setItems(FXCollections.observableArrayList(listaMutable));
    }

    private void filtraReservasId(String newValue) {
        FilteredList<Reserva> filtradoReserva = new FilteredList<>(obsReservas, reserva -> true);
        filtradoReserva.setPredicate(reserva -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return true;
            String cadenaFiltrado = newValue.toLowerCase();
            return reserva.getHabitacion().getIdentificador().startsWith(cadenaFiltrado);
        });
        List<Reserva> listaMutable = new ArrayList<>(filtradoReserva);
        Collections.sort(listaMutable, Comparator.comparing(Reserva::getFechaInicioReserva));
        tvReservas.setItems(FXCollections.observableArrayList(listaMutable));
    }

    private void filtraReservasABorrarDni(String newValue) {
        FilteredList<Reserva> filtradoReserva = new FilteredList<>(obsReservas, reserva -> true);
        filtradoReserva.setPredicate(reserva -> {
            if (newValue.isBlank() || newValue.isEmpty() || newValue == null)
                return false;
            String cadenaFiltrado = newValue.toLowerCase();
            return reserva.getHuesped().getDni().toLowerCase().startsWith(cadenaFiltrado);
        });
        cbEligeReserva.setItems(filtradoReserva);
    }

    @FXML private void realizarCheckin(ActionEvent event) {
        try {
            if (cbEligeReserva.getValue() == null){
                throw new NullPointerException("ERROR: No has elegido ninguna reserva.");
            }
            if (!(cbEligeReserva.getValue().getCheckIn() == null)){
                throw new OperationNotSupportedException("ERROR: No puedes realizar el checkin porque ya se ha realizado anteriormente");
            }
            Reserva reservaElegida = cbEligeReserva.getValue();
                if (Dialogos.mostrarDialogoConfirmacion("ReservasHotel v5 - Realizar Checkin", "¿ADVERTENCIA: Seguro que quiere realizar el checkin para esta reserva? ")){
                    controlador.realizarCheckin(reservaElegida, LocalDateTime.now());
                    Dialogos.mostrarDialogoInformacion("ReservasHotel v5 - Realizar Checkin", "Se ha realizado el checkin correctamente");
                    cargarDatosReservas();
                } else {
                    cargarDatosReservas();
                    event.consume();
                }
                actualizarListaReservas();
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Realizar Checkin",e.getMessage());
        }
    }
    @FXML private void realizarCheckout(ActionEvent event) {
        try {
            if (cbEligeReserva.getValue() == null){
                throw new NullPointerException("ERROR: No has elegido ninguna reserva.");
            }
            if (!(cbEligeReserva.getValue().getCheckOut() == null)){
                throw new OperationNotSupportedException("ERROR: No puedes realizar el checkout porque ya se ha realizado anteriormente");
            }

            Reserva reservaElegida = cbEligeReserva.getValue();
            if (Dialogos.mostrarDialogoConfirmacion("ReservasHotel v5 - Realizar Checkout", "¿ADVERTENCIA: Seguro que quiere realizar el checkout para esta reserva? ")){
                controlador.realizarCheckout(reservaElegida, LocalDateTime.now());
                Dialogos.mostrarDialogoInformacion("ReservasHotel v5 - Realizar Checkout", "Se ha realizado el checkout correctamente");
                cargarDatosReservas();
            } else {
                cargarDatosReservas();
                event.consume();
            }
            actualizarListaReservas();
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("ReservasHotel v5 - Realizar Checkout",e.getMessage());
        }
    }

    private Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva) {
        boolean tipoHabitacionEncontrada = false;
        Habitacion habitacionDisponible = null;

        List<Habitacion> habitacionesTipoSolicitado = controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado == null)
            return habitacionDisponible;

        for (int i = 0; i < habitacionesTipoSolicitado.size() && !tipoHabitacionEncontrada; i++) {
            if (habitacionesTipoSolicitado.get(i) != null) {
                List<Reserva> reservasFuturas = controlador.getReservasFuturas(habitacionesTipoSolicitado.get(i));

                if (reservasFuturas.isEmpty()) {
                    if (habitacionesTipoSolicitado.get(i) instanceof Simple) {
                        habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                    }
                    if (habitacionesTipoSolicitado.get(i) instanceof Doble) {
                        habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                    }
                    if (habitacionesTipoSolicitado.get(i) instanceof Triple) {
                        habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                    }
                    if (habitacionesTipoSolicitado.get(i) instanceof Suite) {
                        habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                    }
                    tipoHabitacionEncontrada = true;
                } else {

                    reservasFuturas.sort(Comparator.comparing(Reserva::getFechaFinReserva).reversed());

                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())) {
                        if (habitacionesTipoSolicitado.get(i) instanceof Simple) {
                            habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                        }
                        if (habitacionesTipoSolicitado.get(i) instanceof Doble) {
                            habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                        }
                        if (habitacionesTipoSolicitado.get(i) instanceof Triple) {
                            habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                        }
                        if (habitacionesTipoSolicitado.get(i) instanceof Suite) {
                            habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                        }
                        tipoHabitacionEncontrada = true;
                    }
                    if (!tipoHabitacionEncontrada) {

                        reservasFuturas.sort(Comparator.comparing(Reserva::getFechaInicioReserva));

                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())) {
                            if (habitacionesTipoSolicitado.get(i) instanceof Simple) {
                                habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                            }
                            if (habitacionesTipoSolicitado.get(i) instanceof Doble) {
                                habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                            }
                            if (habitacionesTipoSolicitado.get(i) instanceof Triple) {
                                habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                            }
                            if (habitacionesTipoSolicitado.get(i) instanceof Suite) {
                                habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                            }
                            tipoHabitacionEncontrada = true;
                        }
                    }
                    if (!tipoHabitacionEncontrada) {
                        for (int j = 1; j < reservasFuturas.size() && !tipoHabitacionEncontrada; j++) {
                            if (reservasFuturas.get(j) != null && reservasFuturas.get(j - 1) != null) {
                                if (fechaInicioReserva.isAfter(reservasFuturas.get(j - 1).getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {
                                    if (habitacionesTipoSolicitado.get(i) instanceof Simple) {
                                        habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                                    }
                                    if (habitacionesTipoSolicitado.get(i) instanceof Doble) {
                                        habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                                    }
                                    if (habitacionesTipoSolicitado.get(i) instanceof Triple) {
                                        habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                                    }
                                    if (habitacionesTipoSolicitado.get(i) instanceof Suite) {
                                        habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                                    }
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return habitacionDisponible;
    }

    private String getTipoHabitacion(Habitacion habitacion) {
        TipoHabitacion tipoHabitacion = null;
        if (habitacion instanceof Simple) tipoHabitacion = TipoHabitacion.SIMPLE;
        if (habitacion instanceof Doble) tipoHabitacion = TipoHabitacion.DOBLE;
        if (habitacion instanceof Triple) tipoHabitacion = TipoHabitacion.TRIPLE;
        if (habitacion instanceof Suite) tipoHabitacion = TipoHabitacion.SUITE;
        return tipoHabitacion.name();
    }

    @FXML
    private void actualizarListaReservas() {
        obsReservas = FXCollections.observableArrayList();
        coleccionReservas = new ArrayList<>();
        tvReservas.setItems(obsReservas);
        cargarDatosReservas();
    }

    private void setSlider() {
        slNumPersonas.setMin(1);
        slNumPersonas.setMax(3);
        slNumPersonas.setValue(1);
        slNumPersonas.setMajorTickUnit(1);
        slNumPersonas.setMinorTickCount(0);
        slNumPersonas.setBlockIncrement(1);
        slNumPersonas.setShowTickLabels(true);
        slNumPersonas.setShowTickMarks(true);
        lblNumPersonas.setText(Integer.toString((int) slNumPersonas.getValue()));
        slNumPersonas.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblNumPersonas.setText(Integer.toString(newValue.intValue()));
        });
    }
}