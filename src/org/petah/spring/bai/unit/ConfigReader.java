/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.unit;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Petah
 */
public class ConfigReader extends DefaultHandler {

    private URI file;
    private UnitInfo unitInfo;

    public ConfigReader() {
        try {
            file = getClass().getClassLoader().getResource("org/petah/spring/bai/config/default.unit.xml").toURI();
        } catch (URISyntaxException ex) {
            Logger.getLogger(ConfigReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConfigReader(URI file) {
        this.file = file;
    }

    /**
     * Starts the parser.
     * @throws java.lang.Exception if the SAXParser cannot be instantiated.
     */
    public void parse() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(file.toString(), this);
        } catch (IOException ex) {
            Logger.getLogger(ConfigReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ConfigReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ConfigReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("unit")) {
            String name = attributes.getValue(attributes.getIndex("name"));
            String armName = attributes.getValue(attributes.getIndex("armname"));
            String coreName = attributes.getValue(attributes.getIndex("corename"));
            unitInfo = new UnitInfo(name, armName, coreName);
        } else if (unitInfo != null) {
            unitInfo.addType(getUnitType(qName));
        }
    }

    public static void main(String[] args) {
        new ConfigReader().parse();
    }

    private static UnitType getUnitType(String typeName) {
        for (UnitType unitType : UnitType.values()) {
            if (unitType.toString().equalsIgnoreCase(typeName)) {
                return unitType;
            }
        }
        throw new RuntimeException("UnitType does not exist: " + typeName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("unit")) {
            UnitInfo.addUnitInfo(unitInfo);
//            Logger.getLogger(ConfigReader.class.getName()).info("Unit loaded: " + unitInfo);
            unitInfo = null;
        }
    }

}
