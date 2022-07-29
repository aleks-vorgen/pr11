package org.example.xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class XMLProcessor {

    public static Document readXML(Path xmlData, boolean validate)
            throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setValidating(validate);
        DocumentBuilder docBuilder = domFactory.newDocumentBuilder();
        docBuilder.setErrorHandler(new XMLErrorHandler());
        Document document = docBuilder.parse(xmlData.toFile());
        document.getDocumentElement();
        return document;
    }

    public static void writeXML(Document document, OutputStream out)
            throws TransformerException {

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource domSource = new DOMSource(document);
        StreamResult result = new StreamResult(out);

        transformer.transform(domSource, result);
    }
}
