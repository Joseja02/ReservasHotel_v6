package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.FuenteDatosFichero;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.FuenteDatosMongoDB;

public enum FactoriaFuenteDatos {
    MEMORIA{
        public IFuenteDatos crear(){
            return new FuenteDatosMemoria();
        }
    },
    MONGODB{
        public IFuenteDatos crear(){
            return new FuenteDatosMongoDB();
        }
    },
    FICHERO{
        public IFuenteDatos crear() { return new FuenteDatosFichero(); }
    };
    public abstract IFuenteDatos crear();
}
