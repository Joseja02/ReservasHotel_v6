package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

public class FuenteDatosFichero implements IFuenteDatos {
    @Override
    public IHuespedes crearHuespedes() {
        return Huespedes.getInstancia();
    }
    @Override
    public IHabitaciones crearHabitaciones() {
        return Habitaciones.getInstancia();
    }
    @Override
    public IReservas crearReservas() {
        return Reservas.getInstancia();
    }
}
