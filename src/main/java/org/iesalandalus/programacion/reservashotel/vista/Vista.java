package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

public abstract class Vista {
    private Controlador controlador;
    public void setControlador (Controlador controlador){
        if (controlador == null) {
            throw new NullPointerException("ERROR: No se puede asignar un controlador nulo");
        }
        this.controlador = controlador;
    }
    public Controlador getControlador(){
        return controlador;
    }
    public abstract void comenzar();
    public abstract void terminar();
}
