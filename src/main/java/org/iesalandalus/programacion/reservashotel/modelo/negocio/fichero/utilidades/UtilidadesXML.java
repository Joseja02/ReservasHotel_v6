package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UtilidadesXML {
    public static Document xmlToDom(String rutaFicheroIn) {
        Document document = null;
        DocumentBuilder builder;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(rutaFicheroIn);
            document.getDocumentElement().normalize();
        } catch (IOException | SAXException | ParserConfigurationException e){
            System.out.println(e.getMessage());
        }
        return document;
    }

    public static void domToXml(Document document, String rutaFicheroOut){
        try {
            Source source = new DOMSource(document);
            Result result = new StreamResult(new File(rutaFicheroOut));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        } catch (TransformerException e){
            System.out.println();
        }
    }
    public static Document crearDomVacio(String raiz) {
        Document document = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.newDocument();
            Element rootElement = document.createElement(raiz);
            document.appendChild(rootElement);
            return document;
        } catch (ParserConfigurationException e){
            System.out.println(e.getMessage());
        }
        return document;
    }
}
