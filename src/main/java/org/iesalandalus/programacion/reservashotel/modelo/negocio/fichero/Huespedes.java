package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Huespedes implements IHuespedes {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final String RUTA_FICHERO = "datos/huespedes.xml";
    private static final String RAIZ = "Huespedes";
    private static final String HUESPED = "Huesped";
    private static final String NOMBRE = "Nombre";
    private static final String DNI = "Dni";
    private static final String CORREO = "Correo";
    private static final String TELEFONO = "Telefono";
    private static final String FECHA_NACIMIENTO = "FechaNacimiento";

    private List<Huesped> coleccionHuespedes;
    private static Huespedes instancia;

    public Huespedes() {
        coleccionHuespedes = new ArrayList<>();
    }

    public static Huespedes getInstancia(){
        if (instancia == null){
            instancia = new Huespedes();
        }
        return instancia;
    }
    public void comenzar(){
        leerXML();
    }
    public void terminar(){
        escribirXML();
    }
    public Huesped elementToHuesped(Element elemento){
        String nombre = elemento.getElementsByTagName(NOMBRE).item(0).getTextContent();
        String dni = elemento.getElementsByTagName(DNI).item(0).getTextContent();
        String correo = elemento.getElementsByTagName(CORREO).item(0).getTextContent();
        String telefono = elemento.getElementsByTagName(TELEFONO).item(0).getTextContent();
        LocalDate fechaNacimiento = LocalDate.parse(elemento.getElementsByTagName(FECHA_NACIMIENTO).item(0).getTextContent(), FORMATO_FECHA);

        return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
    }
    private Element huespedToElement(Document documento, Huesped huesped) {
        Element huespedElement = documento.createElement(HUESPED);

        Element nombreElement = documento.createElement(NOMBRE);
        nombreElement.appendChild(documento.createTextNode(huesped.getNombre()));
        huespedElement.appendChild(nombreElement);

        Element dniElement = documento.createElement(DNI);
        dniElement.appendChild(documento.createTextNode(huesped.getDni()));
        huespedElement.appendChild(dniElement);

        Element correoElement = documento.createElement(CORREO);
        correoElement.appendChild(documento.createTextNode(huesped.getCorreo()));
        huespedElement.appendChild(correoElement);

        Element telefonoElement = documento.createElement(TELEFONO);
        telefonoElement.appendChild(documento.createTextNode(huesped.getTelefono()));
        huespedElement.appendChild(telefonoElement);

        Element fechaNacimientoElement = documento.createElement(FECHA_NACIMIENTO);
        fechaNacimientoElement.appendChild(documento.createTextNode(huesped.getFechaNacimiento().format(FORMATO_FECHA)));
        huespedElement.appendChild(fechaNacimientoElement);

        return huespedElement;
    }
    private void leerXML() {
        try {
            Document document = UtilidadesXML.xmlToDom(RUTA_FICHERO);
            NodeList nodeList = document.getDocumentElement().getElementsByTagName(HUESPED);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Huesped huesped = elementToHuesped(element);
                    coleccionHuespedes.add(huesped);
                }
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void escribirXML() {
        try {
            Document documento = UtilidadesXML.crearDomVacio(RAIZ);
            for (Huesped huesped : coleccionHuespedes) {
                Element huespedElement = huespedToElement(documento, huesped);
                documento.getDocumentElement().appendChild(huespedElement);
            }
            UtilidadesXML.domToXml(documento, RUTA_FICHERO);
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public List<Huesped> get() {
        return copiaProfundaHuespedes();
    }

    private List<Huesped> copiaProfundaHuespedes() {

        List<Huesped> copiaHuespedes = new ArrayList<>();

        Iterator<Huesped> iterador = coleccionHuespedes.iterator();
        while (iterador.hasNext()) {
            Huesped huesped = iterador.next();
            copiaHuespedes.add(new Huesped(huesped));
        }
        return copiaHuespedes;
    }

    public int getTamano() {
        int counter = 0;
        Iterator<Huesped> iterador = coleccionHuespedes.iterator();
        while (iterador.hasNext()){
            iterador.next();
            counter++;
        }
        return counter;
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if (coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        coleccionHuespedes.add(huesped);
    }

    public Huesped buscar(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }

        if (coleccionHuespedes.contains(huesped)) {
            int i = coleccionHuespedes.indexOf(huesped);
            huesped = coleccionHuespedes.get(i);
            return new Huesped(huesped);
        } else {
            return null;
        }
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {

        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }

        if (!coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
        coleccionHuespedes.remove(huesped);
    }

}
