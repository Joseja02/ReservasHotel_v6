package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HabitacionTest {
    private static final String CADENA_NO_ESPERADA = "La cadena devuelta no es la esperada.";
    private static final String PLANTA_NO_ESPERADA = "El número de planta devuelta no es la misma que la pasada al constructor.";
    private static final String PUERTA_NO_ESPERADA = "El número de puerta devuelta no es la misma que la pasada al constructor.";
    private static final String PRECIO_HABITACION_NO_ESPERADO = "El precio devuelto no es el mismo que el pasada al constructor.";
    private static final String IDENTIFICADOR_HABITACION_NO_ESPERADO = "El identificador de la habitación no es el esperado.";

    private static final int PLANTA_1=1;
    private static final int PUERTA_0=0;
    private static final int PUERTA_10=10;

    private static final double PRECIO_HABITACION_VALIDO=50;
    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto habitación.";
    private static final String TIPO_EXCEPCION_NO_CORRECTA = "El tipo de la excepción no es correcto.";

    private static final String PLANTA_INCORRECTA = "Debería haber saltado una excepción indicando que la planta es incorrecta.";
    private static final String ERROR_PLANTA_INCORRECTA= "ERROR: No se puede establecer como planta de una habitación un valor menor que " + Habitacion.MIN_NUMERO_PLANTA + " ni mayor que " + Habitacion.MAX_NUMERO_PLANTA + ".";
    private static final String PUERTA_INCORRECTA="Debería haber saltado una excepción indicando que la puerta es incorrecta.";
    private static final String ERROR_PUERTA_INCORRECTA= "ERROR: No se puede establecer como puerta de una habitación un valor menor que " + Habitacion.MIN_NUMERO_PUERTA + " ni mayor que " + Habitacion.MAX_NUMERO_PUERTA + ".";

    private static final String PRECIO_HABITACION_INCORRECTO= "Debería haber saltado una excepción indicando que el precio de la habitación es incorrecto.";
    private static final String ERROR_PRECIO_HABITACION_INCORRECTO= "ERROR: No se puede establecer como precio de una habitación un valor menor que " + Habitacion.MIN_PRECIO_HABITACION + " ni mayor que " + Habitacion.MAX_PRECIO_HABITACION + ".";
    private static final String HABITACION_NO_ESPERADA = "La habitación copiada debería ser la misma que la pasada como parámetro.";

    private static final String HABITACION_NULA = "Debería haber saltado una excepción indicando que no se puede copiar una habitación nula.";
    private static final String ERROR_COPIAR_HABITACION_NULA = "ERROR: No es posible copiar una habitación nula.";

    private static final String NUM_MAXIMO_PERSONAS_NO_ESPERADO="ERROR: El número máximo de personas no es el esperado.";

    private static final String NUM_BANOS_INCORRECTO="ERROR: El número de baños de la habitación no es el correcto.";

    private static final String NUM_CAMAS_INCORRECTO="ERROR: El número de camas de la habitación no es el correcto.";
    private static final String ERROR_NUM_BANOS_HABITACION_SUITE_INCORRECTO="ERROR: El número de baños no puede ser inferior a " + Suite.MIN_NUM_BANOS + " ni superior a " + Suite.MAX_NUM_BANOS;
    private static final String ERROR_NUM_BANOS_HABITACION_TRIPLE_INCORRECTO="ERROR: El número de baños no puede ser inferior a " + Triple.MIN_NUM_BANOS +" ni superior a " + Triple.MAX_NUM_BANOS;

    private static final String ERROR_NUM_CAMAS_INDIVIDUALES_HABITACION_DOBLE_INCORRECTO="ERROR: El número de camas individuales de una habitación doble no puede ser inferior a " + Doble.MIN_NUM_CAMAS_INDIVIDUALES + " ni mayor que " + Doble.MAX_NUM_CAMAS_INDIVIDUALES;

    private static final String ERROR_NUM_CAMAS_DOBLE_HABITACION_DOBLE_INCORRECTO="ERROR: El número de camas dobles de una habitación doble no puede ser inferior a " + Doble.MIN_NUM_CAMAS_DOBLES + " ni mayor que " + Doble.MAX_NUM_CAMAS_DOBLES;

    private static final String ERROR_NUM_CAMAS_INDIVIDUALES_HABITACION_TRIPLE_INCORRECTO="ERROR: El número de camas individuales de una habitación triple no puede ser inferior a " + Triple.MIN_NUM_CAMAS_INDIVIDUALES + " ni mayor que " + Triple.MAX_NUM_CAMAS_INDIVIDUALES;

    private static final String ERROR_NUM_CAMAS_DOBLE_HABITACION_TRIPLE_INCORRECTO="ERROR: El número de camas dobles de una habitación triple no puede ser inferior a " + Triple.MIN_NUM_CAMAS_DOBLES + " ni mayor que " + Triple.MAX_NUM_CAMAS_DOBLES;

    private static final String ERROR_DISTRIBUCION_CAMAS_HABITACION_DOBLE_INCORRECTO="ERROR: La distribución de camas en una habitación doble tiene que ser " + Doble.MAX_NUM_CAMAS_INDIVIDUALES +" camas individuales y " + Doble.MIN_NUM_CAMAS_DOBLES + " doble o " + Doble.MIN_NUM_CAMAS_INDIVIDUALES + " camas individuales y " + Doble.MAX_NUM_CAMAS_DOBLES + " doble";

    private static final String ERROR_DISTRIBUCION_CAMAS_HABITACION_TRIPLE_INCORRECTO="ERROR: La distribución de camas en una habitación triple tiene que ser " + Triple.MAX_NUM_CAMAS_INDIVIDUALES +" camas individuales y " + Triple.MIN_NUM_CAMAS_DOBLES + " doble o " + Triple.MIN_NUM_CAMAS_INDIVIDUALES + " cama individual y " + Triple.MAX_NUM_CAMAS_DOBLES + " doble";

    @Test
    public void constructorPlantaValidaPuertaValidaPrecioValidoCreaHabitacionSimpleCorrectamente() {
        Simple habitacion = new Simple(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO);
        assertEquals(PLANTA_1, habitacion.getPlanta(), PLANTA_NO_ESPERADA);
        assertEquals(PUERTA_0, habitacion.getPuerta(), PUERTA_NO_ESPERADA);
        assertEquals(PRECIO_HABITACION_VALIDO, habitacion.getPrecio(), PRECIO_HABITACION_NO_ESPERADO);
        //assertEquals(TIPO_HABITACION_VALIDA, habitacion.getTipoHabitacion(), TIPO_HABITACION_NO_ESPERADA);
        assertEquals(String.valueOf(PLANTA_1)+PUERTA_0, habitacion.getIdentificador(), IDENTIFICADOR_HABITACION_NO_ESPERADO);

        habitacion = new Simple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO);
        assertEquals(PLANTA_1, habitacion.getPlanta(), PLANTA_NO_ESPERADA);
        assertEquals(PUERTA_10, habitacion.getPuerta(), PUERTA_NO_ESPERADA);
        assertEquals(PRECIO_HABITACION_VALIDO, habitacion.getPrecio(), PRECIO_HABITACION_NO_ESPERADO);
        //assertEquals(TIPO_HABITACION_VALIDA, habitacion.getTipoHabitacion(), TIPO_HABITACION_NO_ESPERADA);
        assertEquals(String.valueOf(PLANTA_1)+PUERTA_10, habitacion.getIdentificador(), IDENTIFICADOR_HABITACION_NO_ESPERADO);

    }

    @Test
    public void constructorPlantaNoValidaPuertaValidaPrecioValidoLanzaExcepcion() {
        Habitacion habitacion = null;
        try {
            habitacion = new Simple(-2, PUERTA_10, PRECIO_HABITACION_VALIDO);
            fail(PLANTA_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_PLANTA_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Simple(0, PUERTA_10, PRECIO_HABITACION_VALIDO);
            fail(PLANTA_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_PLANTA_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Simple(4, PUERTA_10, PRECIO_HABITACION_VALIDO);
            fail(PLANTA_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_PLANTA_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void constructorHabitacionNumPersonasNoValidaLanzaExcepcion() {
        Habitacion habitacion;

        habitacion = new Simple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO);
        assertEquals(1, habitacion.getNumeroMaximoPersonas(), NUM_MAXIMO_PERSONAS_NO_ESPERADO);

        habitacion=new Doble(PLANTA_1,PUERTA_10,PRECIO_HABITACION_VALIDO,2,0);
        assertEquals(2, habitacion.getNumeroMaximoPersonas(), NUM_MAXIMO_PERSONAS_NO_ESPERADO);

        habitacion=new Triple(PLANTA_1,PUERTA_10,PRECIO_HABITACION_VALIDO,2,3,0);
        assertEquals(3, habitacion.getNumeroMaximoPersonas(), NUM_MAXIMO_PERSONAS_NO_ESPERADO);

        habitacion=new Suite(PLANTA_1,PUERTA_10,PRECIO_HABITACION_VALIDO,2,true);
        assertEquals(4, habitacion.getNumeroMaximoPersonas(), NUM_MAXIMO_PERSONAS_NO_ESPERADO);

    }

    @Test
    public void constructorNumBanosNoValidoLanzaExcepcion() {
        Habitacion habitacion=null;
        try {
            habitacion = new Suite(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,Suite.MAX_NUM_BANOS+1,true);
            fail(NUM_BANOS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_BANOS_HABITACION_SUITE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Suite(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,Suite.MIN_NUM_BANOS-1,true);
            fail(NUM_BANOS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_BANOS_HABITACION_SUITE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,Triple.MAX_NUM_BANOS+1,3,0);
            fail(NUM_BANOS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_BANOS_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,Triple.MIN_NUM_BANOS-1,3,0);
            fail(NUM_BANOS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_BANOS_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void constructorNumCamasNoValidoLanzaExcepcion() {
        Habitacion habitacion=null;
        try {
            habitacion = new Doble(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,Doble.MIN_NUM_CAMAS_INDIVIDUALES-1,0);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_CAMAS_INDIVIDUALES_HABITACION_DOBLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Doble(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,Doble.MAX_NUM_CAMAS_INDIVIDUALES+1,0);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_CAMAS_INDIVIDUALES_HABITACION_DOBLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Doble(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,1,Doble.MIN_NUM_CAMAS_DOBLES-1);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_CAMAS_DOBLE_HABITACION_DOBLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Doble(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,1,Doble.MAX_NUM_CAMAS_DOBLES+1);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_CAMAS_DOBLE_HABITACION_DOBLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,2,Triple.MIN_NUM_CAMAS_INDIVIDUALES-1,0);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_CAMAS_INDIVIDUALES_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,2,Triple.MAX_NUM_CAMAS_INDIVIDUALES+1,0);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_CAMAS_INDIVIDUALES_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,2,1,Triple.MIN_NUM_CAMAS_DOBLES-1);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_CAMAS_DOBLE_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,2,1,Triple.MAX_NUM_CAMAS_DOBLES+1);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NUM_CAMAS_DOBLE_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Doble(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,1,0);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DISTRIBUCION_CAMAS_HABITACION_DOBLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Doble(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,1,1);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DISTRIBUCION_CAMAS_HABITACION_DOBLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Doble(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,2,1);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DISTRIBUCION_CAMAS_HABITACION_DOBLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

       try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,2,1,0);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DISTRIBUCION_CAMAS_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,2,2,0);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DISTRIBUCION_CAMAS_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Triple(PLANTA_1, PUERTA_10, PRECIO_HABITACION_VALIDO,2,3,1);
            fail(NUM_CAMAS_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DISTRIBUCION_CAMAS_HABITACION_TRIPLE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }






    }



    @Test
    public void constructorPlantaValidaPuertaNoValidaPrecioValidoTipoHabitacionValidaLanzaExcepcion() {
        Habitacion habitacion = null;
        try {
            habitacion = new Simple(PLANTA_1, -2, PRECIO_HABITACION_VALIDO);
            fail(PUERTA_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_PUERTA_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Simple(PLANTA_1, 15, PRECIO_HABITACION_VALIDO);
            fail(PUERTA_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_PUERTA_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

    }

    @Test
    public void constructorPlantaValidaPuertaValidaPrecioNoValidoTipoHabitacionValidaLanzaExcepcion() {
        Habitacion habitacion = null;
        try {
            habitacion = new Simple(PLANTA_1, PUERTA_0, -2);
            fail(PRECIO_HABITACION_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_PRECIO_HABITACION_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            habitacion = new Simple(PLANTA_1, PUERTA_0, 155);
            fail(PRECIO_HABITACION_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_PRECIO_HABITACION_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

    }

    @Test
    public void constructorHabitacionValidaCopiaHabitacionCorrectamente() {
        Habitacion habitacion1 = new Simple(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO);
        Habitacion habitacion2 = new Simple((Simple)habitacion1);
        assertEquals(habitacion1, habitacion2, HABITACION_NO_ESPERADA);
        assertEquals(PLANTA_1, habitacion2.getPlanta(), PLANTA_NO_ESPERADA);
        assertEquals(PUERTA_0, habitacion2.getPuerta(), PUERTA_NO_ESPERADA);
        assertEquals(PRECIO_HABITACION_VALIDO, habitacion2.getPrecio(), PRECIO_HABITACION_NO_ESPERADO);
        //assertEquals(TIPO_HABITACION_VALIDA, habitacion2.getTipoHabitacion(), TIPO_HABITACION_NO_ESPERADA);
        assertEquals(String.valueOf(PLANTA_1)+PUERTA_0, habitacion2.getIdentificador(), IDENTIFICADOR_HABITACION_NO_ESPERADO);
    }

    @Test
    public void constructorHabitacionNulaLanzaExcepcion() {
        Habitacion habitacion = null;

        try {
            habitacion = new Simple(null);
            fail(HABITACION_NULA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_COPIAR_HABITACION_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, habitacion, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }



    @Test
    public void toStringDevuelveLaCadenaEsperadaHabitacionSimple() {
        Simple habitacion = new Simple(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO);

        assertEquals(String.format("identificador=%s (%d-%d), precio habitación=%s, habitación simple, capacidad=%d personas",
                habitacion.getIdentificador(), habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(),
                habitacion.getNumeroMaximoPersonas()), habitacion.toString(), CADENA_NO_ESPERADA);
    }

    @Test
    public void toStringDevuelveLaCadenaEsperadaHabitacionDoble() {
        Doble habitacion = new Doble(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO,2,0);

        assertEquals(String.format("identificador=%s (%d-%d), precio habitación=%s, habitación doble, capacidad=%d personas, " +
                        "camas individuales=%d, camas dobles=%d",
                habitacion.getIdentificador(), habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(),
                habitacion.getNumeroMaximoPersonas(),habitacion.getNumCamasIndividuales(),habitacion.getNumCamasDobles()), habitacion.toString(), CADENA_NO_ESPERADA);
    }

    @Test
    public void toStringDevuelveLaCadenaEsperadaHabitacionTriple() {
        Triple habitacion = new Triple(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO,2, 3,0);

        assertEquals(String.format("identificador=%s (%d-%d), precio habitación=%s, habitación triple, capacidad=%d personas, " +
                        "baños=%d, camas individuales=%d, camas dobles=%d",
                habitacion.getIdentificador(), habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(),
                habitacion.getNumeroMaximoPersonas(),habitacion.getNumBanos(),habitacion.getNumCamasIndividuales(),habitacion.getNumCamasDobles()), habitacion.toString(), CADENA_NO_ESPERADA);
    }

    @Test
    public void toStringDevuelveLaCadenaEsperadaHabitacionSuite() {
        Suite habitacion = new Suite(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO,2, true);

        assertEquals(String.format("identificador=%s (%d-%d), precio habitación=%s, habitación suite, capacidad=%d personas, " +
                        "baños=%d, con Jacuzzi",
                habitacion.getIdentificador(), habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(),
                habitacion.getNumeroMaximoPersonas(),habitacion.getNumBanos()), habitacion.toString(), CADENA_NO_ESPERADA);

        habitacion = new Suite(PLANTA_1, PUERTA_0, PRECIO_HABITACION_VALIDO,2, false);

        assertEquals(String.format("identificador=%s (%d-%d), precio habitación=%s, habitación suite, capacidad=%d personas, " +
                        "baños=%d, sin Jacuzzi",
                habitacion.getIdentificador(), habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(),
                habitacion.getNumeroMaximoPersonas(),habitacion.getNumBanos()), habitacion.toString(), CADENA_NO_ESPERADA);
    }
}
