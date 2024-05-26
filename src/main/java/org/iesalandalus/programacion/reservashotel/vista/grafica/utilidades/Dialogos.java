package org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;

import java.util.Optional;

public class Dialogos {

    private Dialogos() {
        //Evitamos que se puedan instanciar objetos
    }

    public static void mostrarDialogoError(String titulo, String contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.ERROR);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        if (propietario != null) {
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.initOwner(propietario);
        }
        dialogo.showAndWait();
        if (propietario != null)
            propietario.close();
    }

    public static void mostrarDialogoError(String titulo, String contenido) {
        Dialogos.mostrarDialogoError(titulo, contenido, null);
    }

    public static void mostrarDialogoInformacion(String titulo, String contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.INFORMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        if (propietario != null) {
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.initOwner(propietario);
        }
        dialogo.showAndWait();
        if (propietario != null)
            propietario.close();
    }

    public static void mostrarDialogoInformacion(String titulo, String contenido) {
        Dialogos.mostrarDialogoInformacion(titulo, contenido, null);
    }

    public static void mostrarDialogoInformacionPersonalizado(String titulo, Pane contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.INFORMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(null);
        dialogo.getDialogPane().setHeader(contenido);
        if (propietario != null) {
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.initOwner(propietario);
        }
        dialogo.showAndWait();
        if (propietario != null)
            propietario.close();
    }

    public static void mostrarDialogoInformacionPersonalizado(String titulo, Pane contenido) {
        Dialogos.mostrarDialogoInformacionPersonalizado(titulo, contenido, null);
    }

    public static void mostrarDialogoAdvertencia(String titulo, String contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.WARNING);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        if (propietario != null) {
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.initOwner(propietario);
        }
        dialogo.showAndWait();
        if (propietario != null)
            propietario.close();
    }

    public static void mostrarDialogoAdvertencia(String titulo, String contenido) {
        Dialogos.mostrarDialogoAdvertencia(titulo, contenido, null);
    }

    public static String mostrarDialogoTexto(String titulo, String contenido) {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setGraphic(null);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);

        Optional<String> respuesta = dialogo.showAndWait();
        return (respuesta.isPresent() ? respuesta.get() : null);
    }

    public static boolean mostrarDialogoConfirmacion(String titulo, String contenido, Stage propietario) {
        Alert dialogo = new Alert(AlertType.CONFIRMATION);
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(null);
        dialogo.setContentText(contenido);
        if (propietario != null) {
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.initOwner(propietario);
        }

        Optional<ButtonType> respuesta = dialogo.showAndWait();
        return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
    }

    public static boolean mostrarDialogoConfirmacion(String titulo, String contenido) {
        return mostrarDialogoConfirmacion(titulo, contenido, null);
    }

}