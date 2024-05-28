package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private static final String RUTA_FICHERO = "datos/habitaciones.xml";
    private static final String RAIZ = "Habitaciones";
    private static final String HABITACION = "Habitacion";
    private static final String IDENTIFICADOR = "Identificador";
    private static final String PLANTA = "Planta";
    private static final String PUERTA = "Puerta";
    private static final String PRECIO = "Precio";
    private static final String CAMAS_INDIVIDUALES = "CamasIndividuales";
    private static final String CAMAS_DOBLES = "CamasDobles";
    private static final String BANOS = "Banos";
    private static final String JACUZZI = "Jacuzzi";
    private static final String TIPO = "Tipo";
    private static final String SIMPLE = "Simple";
    private static final String DOBLE = "Doble";
    private static final String TRIPLE = "Triple";
    private static final String SUITE = "Suite";

    private List<Habitacion> coleccionHabitaciones;
    private static Habitaciones instancia;

    public Habitaciones() {
        coleccionHabitaciones = new ArrayList<>();
        comenzar();
    }
    public static Habitaciones getInstancia(){
        if (instancia == null){
            instancia = new Habitaciones();
        }
        return instancia;
    }

    public void comenzar(){
        leerXML();
    }
    public void terminar(){
        escribirXML();
    }
    public Habitacion elementToHabitacion(Element elemento){
        if (elemento == null) {
            return null;
        }
        Habitacion habitacion = null;
        String tipo = elemento.getAttribute(TIPO);

        switch (tipo) {
            case SIMPLE -> {
                int planta = Integer.parseInt(elemento.getElementsByTagName(PLANTA).item(0).getTextContent());
                int puerta = Integer.parseInt(elemento.getElementsByTagName(PUERTA).item(0).getTextContent());
                double precio = Double.parseDouble(elemento.getElementsByTagName(PRECIO).item(0).getTextContent());
                return new Simple(planta, puerta, precio);
            }
            case DOBLE -> {
                int planta = Integer.parseInt(elemento.getElementsByTagName(PLANTA).item(0).getTextContent());
                int puerta = Integer.parseInt(elemento.getElementsByTagName(PUERTA).item(0).getTextContent());
                double precio = Double.parseDouble(elemento.getElementsByTagName(PRECIO).item(0).getTextContent());
                int numCamasIndividuales = Integer.parseInt(elemento.getElementsByTagName(CAMAS_INDIVIDUALES).item(0).getTextContent());
                int numCamasDobles = Integer.parseInt(elemento.getElementsByTagName(CAMAS_DOBLES).item(0).getTextContent());
                return new Doble(planta, puerta, precio, numCamasIndividuales, numCamasDobles);
            }
            case TRIPLE -> {
                int planta = Integer.parseInt(elemento.getElementsByTagName(PLANTA).item(0).getTextContent());
                int puerta = Integer.parseInt(elemento.getElementsByTagName(PUERTA).item(0).getTextContent());
                double precio = Double.parseDouble(elemento.getElementsByTagName(PRECIO).item(0).getTextContent());
                int numBanos = Integer.parseInt(elemento.getElementsByTagName(BANOS).item(0).getTextContent());
                int numCamasIndividuales = Integer.parseInt(elemento.getElementsByTagName(CAMAS_INDIVIDUALES).item(0).getTextContent());
                int numCamasDobles = Integer.parseInt(elemento.getElementsByTagName(CAMAS_DOBLES).item(0).getTextContent());
                return new Triple(planta, puerta, precio, numBanos, numCamasIndividuales, numCamasDobles);
            }
            case SUITE -> {
                int planta = Integer.parseInt(elemento.getElementsByTagName(PLANTA).item(0).getTextContent());
                int puerta = Integer.parseInt(elemento.getElementsByTagName(PUERTA).item(0).getTextContent());
                double precio = Double.parseDouble(elemento.getElementsByTagName(PRECIO).item(0).getTextContent());
                int numBanos = Integer.parseInt(elemento.getElementsByTagName(BANOS).item(0).getTextContent());
                boolean tieneJacuzzi = Boolean.parseBoolean(elemento.getElementsByTagName(JACUZZI).item(0).getTextContent());
                return new Suite(planta, puerta, precio, numBanos, tieneJacuzzi);
            }
        }
        return habitacion;
    }
    private Element habitacionToElement(Document documento, Habitacion habitacion) {
        if (documento == null || habitacion == null) {
            return null;
        }
        Element habitacionElement = documento.createElement(HABITACION);

        habitacionElement.setAttribute(IDENTIFICADOR, habitacion.getIdentificador());

        Element plantaElement = documento.createElement(PLANTA);
        plantaElement.appendChild(documento.createTextNode(Integer.toString(habitacion.getPlanta())));
        habitacionElement.appendChild(plantaElement);

        Element puertaElement = documento.createElement(PUERTA);
        puertaElement.appendChild(documento.createTextNode(Integer.toString(habitacion.getPuerta())));
        habitacionElement.appendChild(puertaElement);

        Element precioElement = documento.createElement(PRECIO);
        precioElement.appendChild(documento.createTextNode(Double.toString(habitacion.getPrecio())));
        habitacionElement.appendChild(precioElement);

        if (habitacion instanceof Simple){
            habitacionElement.setAttribute(TIPO, SIMPLE);
        }
        if (habitacion instanceof Doble){
            habitacionElement.setAttribute(TIPO, DOBLE);
            Element dobleElement = documento.createElement(DOBLE);
            habitacionElement.appendChild(dobleElement);

            Element numCamasIndividualesElement = documento.createElement(CAMAS_INDIVIDUALES);
            numCamasIndividualesElement.appendChild(documento.createTextNode(Integer.toString(((Doble) habitacion).getNumCamasIndividuales())));
            dobleElement.appendChild(numCamasIndividualesElement);

            Element numCamasDoblesElement = documento.createElement(CAMAS_DOBLES);
            numCamasDoblesElement.appendChild(documento.createTextNode(Integer.toString(((Doble) habitacion).getNumCamasDobles())));
            dobleElement.appendChild(numCamasDoblesElement);
        }

        if (habitacion instanceof Triple){
            habitacionElement.setAttribute(TIPO, TRIPLE);
            Element tripleElement = documento.createElement(TRIPLE);
            habitacionElement.appendChild(tripleElement);

            Element numBanosElement = documento.createElement(BANOS);
            numBanosElement.appendChild(documento.createTextNode(Integer.toString(((Triple) habitacion).getNumBanos())));
            tripleElement.appendChild(numBanosElement);

            Element numCamasIndividualesElement = documento.createElement(CAMAS_INDIVIDUALES);
            numCamasIndividualesElement.appendChild(documento.createTextNode(Integer.toString(((Triple) habitacion).getNumCamasIndividuales())));
            tripleElement.appendChild(numCamasIndividualesElement);

            Element numCamasDoblesElement = documento.createElement(CAMAS_DOBLES);
            numCamasDoblesElement.appendChild(documento.createTextNode(Integer.toString(((Triple) habitacion).getNumCamasDobles())));
            tripleElement.appendChild(numCamasDoblesElement);
        }
        if (habitacion instanceof Suite){
            habitacionElement.setAttribute(TIPO, SUITE);
            Element suiteElement = documento.createElement(SUITE);
            habitacionElement.appendChild(suiteElement);

            Element numBanosElement = documento.createElement(BANOS);
            numBanosElement.appendChild(documento.createTextNode(Integer.toString(((Suite) habitacion).getNumBanos())));
            suiteElement.appendChild(numBanosElement);

            Element tieneJacuzziElement = documento.createElement(JACUZZI);
            tieneJacuzziElement.appendChild(documento.createTextNode(Boolean.toString(((Suite) habitacion).isTieneJacuzzi())));
            suiteElement.appendChild(tieneJacuzziElement);
        }
        return habitacionElement;
    }
    private void leerXML() {
        Document document;
        NodeList habitaciones;
        Node habitacionNodo;

        try {
            document = UtilidadesXML.xmlToDom(RUTA_FICHERO);
            if (document == null){
                document = UtilidadesXML.crearDomVacio(RAIZ);
            }
            document.getDocumentElement().normalize();

            habitaciones = document.getElementsByTagName(HABITACION);

            for (int i = 0; i < habitaciones.getLength(); i++) {
                habitacionNodo = habitaciones.item(i);

                if (habitacionNodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) habitacionNodo;
                    Habitacion habitacion = elementToHabitacion(elemento);
                    coleccionHabitaciones.add(habitacion);
                }
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    private void escribirXML() {
        try {
            Document documento;
            documento = UtilidadesXML.crearDomVacio(RAIZ);
            for (Habitacion habitacion : coleccionHabitaciones) {
                Element habitacionElement = habitacionToElement(documento, habitacion);
                documento.getDocumentElement().appendChild(habitacionElement);
            }
            UtilidadesXML.domToXml(documento, RUTA_FICHERO);
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    public List<Habitacion> get() {
        return copiaProfundaHabitaciones();
    }

    public List<Habitacion> get(TipoHabitacion tipoHabitacion) {

        if (tipoHabitacion == null){
            throw new NullPointerException("ERROR: El tipo de habitación no puede ser nulo");
        }
        List<Habitacion> habitacionesTipo = new ArrayList<>();

        Iterator<Habitacion> iterador = coleccionHabitaciones.iterator();
        while (iterador.hasNext()) {
            Habitacion habitacion = iterador.next();
            if (habitacion instanceof Simple && tipoHabitacion.equals(TipoHabitacion.SIMPLE)) {
                habitacionesTipo.add(habitacion);
            } else if (habitacion instanceof Doble && tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
                habitacionesTipo.add(habitacion);
            }else if (habitacion instanceof Triple && tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
                habitacionesTipo.add(habitacion);
            }else if (habitacion instanceof Suite && tipoHabitacion.equals(TipoHabitacion.SUITE)) {
                habitacionesTipo.add(habitacion);
            }
        }
        return habitacionesTipo;
    }

    private List<Habitacion> copiaProfundaHabitaciones() {

        List<Habitacion> copiaHabitaciones = new ArrayList<>();

        Iterator<Habitacion> iterador = coleccionHabitaciones.iterator();
        while (iterador.hasNext()) {
            Habitacion habitacion = iterador.next();
            if (habitacion instanceof Simple) {
                copiaHabitaciones.add(new Simple (habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio()));
            } else if (habitacion instanceof Doble) {
                copiaHabitaciones.add(new Doble (habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Doble) habitacion).getNumCamasIndividuales(), ((Doble) habitacion).getNumCamasDobles()));
            } else if (habitacion instanceof Triple) {
                copiaHabitaciones.add(new Triple (habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Triple) habitacion).getNumBanos(), ((Triple) habitacion).getNumCamasIndividuales(), ((Triple) habitacion).getNumCamasDobles()));
            } else if (habitacion instanceof Suite) {
                copiaHabitaciones.add(new Suite (habitacion.getPlanta(), habitacion.getPuerta(), habitacion.getPrecio(), ((Suite) habitacion).getNumBanos(), ((Suite) habitacion).isTieneJacuzzi()));
            }
        }
        return copiaHabitaciones;
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if (coleccionHabitaciones.contains(habitacion)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {

        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }
        if (coleccionHabitaciones.contains(habitacion)) {
            int i = coleccionHabitaciones.indexOf(habitacion);
            return coleccionHabitaciones.get(i);
        } else {
            return null;
        }
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede borrar una habitaci�n nula.");
        }

        if (!coleccionHabitaciones.contains(habitacion)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitaci�n como la indicada.");
        }
        coleccionHabitaciones.remove(habitacion);
    }

    public int getTamano() {
        int counter = 0;
        Iterator<Habitacion> iterador = coleccionHabitaciones.iterator();
        while (iterador.hasNext()){
            iterador.next();
            counter++;
        }
        return counter;
    }
}
