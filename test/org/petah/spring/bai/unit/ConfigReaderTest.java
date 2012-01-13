///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package org.petah.spring.bai.unit;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.xml.sax.Attributes;
//
///**
// *
// * @author David
// */
//public class ConfigReaderTest {
//
//    public ConfigReaderTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of parse method, of class ConfigReader.
//     */
//    @Test
//    public void testParse() {
//        System.out.println("parse");
//        ConfigReader instance = new ConfigReader();
//        instance.parse();
//        System.out.println(UnitInfo.getUnitByName("Commander"));
//    }
//
//    /**
//     * Test of startElement method, of class ConfigReader.
//     */
//    @Test
//    public void testStartElement() throws Exception {
//        System.out.println("startElement");
//        String uri = "";
//        String localName = "";
//        String qName = "";
//        Attributes attributes = null;
//        ConfigReader instance = new ConfigReader();
//        instance.startElement(uri, localName, qName, attributes);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of endElement method, of class ConfigReader.
//     */
//    @Test
//    public void testEndElement() throws Exception {
//        System.out.println("endElement");
//        String uri = "";
//        String localName = "";
//        String qName = "";
//        ConfigReader instance = new ConfigReader();
//        instance.endElement(uri, localName, qName);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//}