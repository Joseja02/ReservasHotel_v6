package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

public class LanzadorVentanaPrincipal extends Application {

    public void comenzar() throws Exception {
        Stage escenario = new Stage();
        start(escenario);
    }
    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            Parent raiz = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/ventanaPrincipal.fxml"));
            Scene escena = new Scene(raiz, 800, 600);
            escenarioPrincipal.setTitle("Reservas Hotel v5 - Jose Javier Sierra Berdun");
            escenarioPrincipal.setResizable(false);
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.setOnCloseRequest(e->confirmarSalida(escenarioPrincipal,e));
            escenarioPrincipal.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e){
        if (Dialogos.mostrarDialogoConfirmacion("Reservas Hotel v5 - Jose Javier Sierra Berdun", "Estas seguro que quieres salirte de la aplicacion"))
        {
            escenarioPrincipal.close();
        }
        else
            e.consume();
    }
}
