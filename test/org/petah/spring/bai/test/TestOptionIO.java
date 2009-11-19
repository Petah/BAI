///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.petah.spring.bai.test;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.petah.common.option.OptionsManager;
//import org.petah.common.xml.XMLOutputStream;
//import org.petah.spring.bai.DefaultOptions;
//
///**
// *
// * @author Petah
// */
//public class TestOptionIO {
//
//    public static void main(String[] args) {
//        XMLOutputStream outputStream = null;
//        try {
//            DefaultOptions.aiDirectory.getValue();
//            outputStream = new XMLOutputStream(new FileOutputStream("options.xml"));
//            outputStream.writeObject(OptionsManager.getOptions());
//        } catch (IOException ex) {
//            Logger.getLogger(TestOptionIO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                outputStream.close();
//            } catch (IOException ex) {
//                Logger.getLogger(TestOptionIO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//}
