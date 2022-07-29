package org.example.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        exception.printStackTrace();
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println("In file: " + exception.getSystemId());
        System.out.println("On line: " + exception.getLineNumber() + ", column: " + exception.getColumnNumber());
        System.out.println("Error: " + exception.getLocalizedMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        exception.printStackTrace();
    }
}
