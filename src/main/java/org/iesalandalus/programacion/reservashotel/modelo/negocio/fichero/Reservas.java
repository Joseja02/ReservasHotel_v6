package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Reservas implements IReservas {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String RUTA_FICHERO = "datos/reservas.xml";
    private static final String RAIZ = "Reservas";
    private static final String RESERVA = "Reserva";
    private static final String DNI_HUESPED = "Dni";
    private static final String PLANTA_HABITACION = "Planta";
    private static final String PUERTA_HABITACION = "Puerta";
    private static final String FECHA_INICIO_RESERVA = "FechaInicioReserva";
    private static final String FECHA_FIN_RESERVA = "FechaFinReserva";
    private static final String REGIMEN = "Regimen";
    private static final String NUMERO_PERSONAS = "Personas";
    private static final String CHECKIN = "FechaCheckin";
    private static final String CHECKOUT = "FechaCheckout";
    private static final String PRECIO = "Precio";

    private List<Reserva> coleccionReservas;
    private static Reservas instancia;

    public Reservas() {
        coleccionReservas = new ArrayList<>();
    }
    public static Reservas getInstancia(){
        if (instancia == null){
            instancia = new Reservas();
        }
        return instancia;
    }

    public void comenzar(){
        leerXML();
    }
    public void terminar(){
        escribirXML();
    }

    public Reserva elementToReserva(Element elemento){
        if (elemento == null){
            return null;
        }

        String dniHuesped = elemento.getElementsByTagName(DNI_HUESPED).item(0).getTextContent();
        int plantaHabitacion = Integer.parseInt(elemento.getElementsByTagName(PLANTA_HABITACION).item(0).getTextContent());
        int puertaHabitacion = Integer.parseInt(elemento.getElementsByTagName(PUERTA_HABITACION).item(0).getTextContent());
        String regimenRecibido = elemento.getElementsByTagName(REGIMEN).item(0).getTextContent();
        LocalDate fechaInicioReserva = LocalDate.parse(elemento.getElementsByTagName(FECHA_INICIO_RESERVA).item(0).getTextContent(), FORMATO_FECHA);
        LocalDate fechaFinReserva = LocalDate.parse(elemento.getElementsByTagName(FECHA_FIN_RESERVA).item(0).getTextContent(), FORMATO_FECHA);
        int numeroPersonas = Integer.parseInt(elemento.getElementsByTagName(NUMERO_PERSONAS).toString());

        Huesped huespedDni = new Huesped("Nombre Ficticio", dniHuesped, "ficticio@test.com", "123456789", LocalDate.of(2002, 8, 19));
        Habitacion habitacionId = new Simple(plantaHabitacion, puertaHabitacion, 40);
        Regimen regimen = Regimen.valueOf(regimenRecibido);

        Huesped huesped = Huespedes.getInstancia().buscar(huespedDni);
        Habitacion habitacion = Habitaciones.getInstancia().buscar(habitacionId);

        return new Reserva(huesped, habitacion, regimen, fechaInicioReserva, fechaFinReserva, numeroPersonas);
    }

    private Element reservaToElement(Document documento, Reserva reserva) {
        if (documento == null || reserva == null){
            return null;
        }
        Element reservaElement = documento.createElement(RESERVA);

        Element huespedDniElement = documento.createElement(DNI_HUESPED);
        huespedDniElement.appendChild(documento.createAttribute(reserva.getHuesped().getDni()));
        reservaElement.appendChild(huespedDniElement);

        Element plantaHabitacionElement = documento.createElement(PLANTA_HABITACION);
        plantaHabitacionElement.appendChild(documento.createAttribute(Integer.toString(reserva.getHabitacion().getPlanta())));
        reservaElement.appendChild(plantaHabitacionElement);

        Element puertaHabitacionElement = documento.createElement(PUERTA_HABITACION);
        puertaHabitacionElement.appendChild(documento.createAttribute(Integer.toString(reserva.getHabitacion().getPuerta())));
        reservaElement.appendChild(puertaHabitacionElement);

        Element regimenElement = documento.createElement(REGIMEN);
        regimenElement.appendChild(documento.createTextNode(reserva.getRegimen().toString()));
        reservaElement.appendChild(regimenElement);

        Element fechaInicioReservaElement = documento.createElement(FECHA_INICIO_RESERVA);
        fechaInicioReservaElement.appendChild(documento.createTextNode(reserva.getFechaInicioReserva().format(FORMATO_FECHA)));
        reservaElement.appendChild(fechaInicioReservaElement);

        Element fechaFinReservaElement = documento.createElement(FECHA_FIN_RESERVA);
        fechaFinReservaElement.appendChild(documento.createTextNode(reserva.getFechaFinReserva().format(FORMATO_FECHA)));
        reservaElement.appendChild(fechaFinReservaElement);

        Element numPersonasElement = documento.createElement(NUMERO_PERSONAS);
        numPersonasElement.appendChild(documento.createTextNode(Integer.toString(reserva.getNumeroPersonas())));
        reservaElement.appendChild(numPersonasElement);

        Element fechaCheckinElement = documento.createElement(CHECKIN);
        fechaCheckinElement.appendChild(documento.createTextNode(reserva.getCheckIn().format(FORMATO_FECHA_HORA)));
        reservaElement.appendChild(fechaCheckinElement);

        Element fechaCheckoutElement = documento.createElement(CHECKOUT);
        fechaCheckoutElement.appendChild(documento.createTextNode(reserva.getCheckOut().format(FORMATO_FECHA_HORA)));
        reservaElement.appendChild(fechaCheckoutElement);

        Element precioElement = documento.createElement(PRECIO);
        precioElement.appendChild(documento.createTextNode(Double.toString(reserva.getPrecio())));
        reservaElement.appendChild(precioElement);

        return reservaElement;
    }

    private void leerXML() {
        try {
            Document document = UtilidadesXML.xmlToDom(RUTA_FICHERO);
            NodeList nodeList = document.getDocumentElement().getElementsByTagName(RESERVA);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Reserva reserva = elementToReserva(element);
                    coleccionReservas.add(reserva);
                }
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void escribirXML() {
        try {
            Document documento = UtilidadesXML.crearDomVacio(RAIZ);
            for (Reserva reserva : coleccionReservas) {
                Element reservaElement = reservaToElement(documento, reserva);
                documento.getDocumentElement().appendChild(reservaElement);
            }
            UtilidadesXML.domToXml(documento, RUTA_FICHERO);
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Reserva> get() {
        return copiaProfundaReservaes();
    }

    private List<Reserva> copiaProfundaReservaes() {

        List<Reserva> copiaReservas = new ArrayList<>();

        Iterator<Reserva> iterador = coleccionReservas.iterator();
        while (iterador.hasNext()) {
            Reserva reserva = iterador.next();
            copiaReservas.add(new Reserva(reserva));
        }
        return copiaReservas;
    }

    public int getTamano() {
        int counter = 0;
        Iterator<Reserva> iterador = coleccionReservas.iterator();
        while (iterador.hasNext()){
            iterador.next();
            counter++;
        }
        return counter;
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {

        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (coleccionReservas.contains(reserva)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        coleccionReservas.add(reserva);
    }

    public Reserva buscar(Reserva reserva) {

        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }

        if (coleccionReservas.contains(reserva)) {
            int i = coleccionReservas.indexOf(reserva);
            reserva = coleccionReservas.get(i);
            return new Reserva(reserva);
        } else {
            return null;
        }
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }

        if (!coleccionReservas.contains(reserva)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        }
        coleccionReservas.remove(reserva);
    }

    public List<Reserva> getReservas(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }
        List<Reserva> reservasHuesped = new ArrayList<>();
        Iterator<Reserva> iterador = get().iterator();
        int i = 0;
        while (iterador.hasNext()) {
            Reserva reserva = get().get(i);
            if (reserva.getHuesped().getDni().equals(huesped.getDni())) {
                reservasHuesped.add(new Reserva(reserva));
            }
            i++;
        }
        return reservasHuesped;
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        List<Reserva> reservasHuesped = new ArrayList<>();
        Iterator<Reserva> iterador = get().iterator();
        int i = 0;
        while (iterador.hasNext()) {
            Reserva reserva = get().get(i);
            if (reserva.getHabitacion() instanceof Simple && tipoHabitacion.equals(TipoHabitacion.SIMPLE)){
                reservasHuesped.add(new Reserva(reserva));
            }
            if (reserva.getHabitacion() instanceof Doble && tipoHabitacion.equals(TipoHabitacion.DOBLE)){
                reservasHuesped.add(new Reserva(reserva));
            }
            if (reserva.getHabitacion() instanceof Triple && tipoHabitacion.equals(TipoHabitacion.TRIPLE)){
                reservasHuesped.add(new Reserva(reserva));
            }
            if (reserva.getHabitacion() instanceof Suite && tipoHabitacion.equals(TipoHabitacion.SUITE)){
                reservasHuesped.add(new Reserva(reserva));
            }
            i++;
        }
        return reservasHuesped;
    }
    public List<Reserva> getReservas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nulo.");
        }
        List<Reserva> reservasHabitacion = new ArrayList<>();
        Iterator<Reserva> iterador = get().iterator();
        int i = 0;
        while (iterador.hasNext()) {
            Reserva reserva = get().get(i);
            if (reserva.getHabitacion().getIdentificador().equals(habitacion.getIdentificador())) {
                reservasHabitacion.add(new Reserva(reserva));
            }
            i++;
        }
        return reservasHabitacion;
    }
    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");

        List<Reserva> reservasHuesped = new ArrayList<>();
        Iterator<Reserva> iterador = get().iterator();
        int i = 0;
        while (iterador.hasNext()){
            Reserva reserva = get().get(i);
            if (reserva.getHabitacion().getIdentificador().equals(habitacion.getIdentificador()) &&
                    reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasHuesped.add(new Reserva(reserva));
            }
            i++;
        }
        return reservasHuesped;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha) {

        if (reserva == null){
            throw new NullPointerException("ERROR: La reserva de un Checkin no puede ser nula.");
        }
        if (fecha == null){
            throw new NullPointerException("ERROR: La fecha de un Checkin no puede ser nula.");
        }
        reserva.setCheckIn(fecha);
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null){
            throw new NullPointerException("ERROR: La reserva de un Checkout no puede ser nula.");
        }
        if (fecha == null){
            throw new NullPointerException("ERROR: La fecha de un Checkout no puede ser nula.");
        }
        if (reserva.getCheckIn() == null){
            throw new IllegalArgumentException("ERROR: No se puede realizar el Checkout sin haber hecho antes el Checkin.");
        }
        reserva.setCheckOut(fecha);
    }
}
