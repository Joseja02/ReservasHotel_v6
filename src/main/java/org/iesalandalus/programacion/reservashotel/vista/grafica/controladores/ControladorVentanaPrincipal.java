package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.io.IOException;

public class ControladorVentanaPrincipal {
    @FXML void abrirVentanaHuespedes() {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaHuespedes.fxml"));
        ControladorVentanaHuespedes c = fxmlLoader.getController();
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz, 1022, 750);
            Stage ventanaHuespedes = new Stage();
            ventanaHuespedes.setScene(escena);
            ventanaHuespedes.initModality(Modality.WINDOW_MODAL);
            ventanaHuespedes.setTitle("Huespedes");
            ventanaHuespedes.setResizable(false);
            ventanaHuespedes.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML void abrirVentanaHabitaciones() {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaHabitaciones.fxml"));
        ControladorVentanaHabitaciones c = fxmlLoader.getController();
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz, 1022, 790);
            Stage ventanaHuespedes = new Stage();
            ventanaHuespedes.setScene(escena);
            ventanaHuespedes.initModality(Modality.WINDOW_MODAL);
            ventanaHuespedes.setTitle("Habitaciones");
            ventanaHuespedes.setResizable(false);
            ventanaHuespedes.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML void abrirVentanaReservas() {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaReservas.fxml"));
        ControladorVentanaReservas c = fxmlLoader.getController();
        try {
            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz, 1022, 807);
            Stage ventanaHuespedes = new Stage();
            ventanaHuespedes.setScene(escena);
            ventanaHuespedes.initModality(Modality.WINDOW_MODAL);
            ventanaHuespedes.setTitle("Reservas");
            ventanaHuespedes.setResizable(false);
            ventanaHuespedes.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML void salir(ActionEvent event) {
        if (Dialogos.mostrarDialogoConfirmacion("Reservas Hotel v5 - Jose Javier Sierra Berdún", "¿Seguro que quieres salir de la aplicación"))
        {
            System.exit(0);
        }
        else
            event.consume();
    }
}
