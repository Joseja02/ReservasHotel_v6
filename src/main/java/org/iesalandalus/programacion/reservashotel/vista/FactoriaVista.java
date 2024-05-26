package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

public enum FactoriaVista {
    TEXTO{
        public Vista crear(){ return new VistaTexto(); }
    },
    GRAFICA{
        public Vista crear(){ return VistaGrafica.getInstancia(); }
    };
    public abstract Vista crear();
}
