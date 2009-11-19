/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on 22/09/2009, 1:26:18 PM
 */
package org.petah.spring.bai.gui;

import java.awt.Component;
import java.awt.EventQueue;

/**
 *
 * @author Petah
 */
public class MainFrame extends javax.swing.JFrame {

    /** Creates new form MainFrame */
    public MainFrame(String title) {
        super(title);
        initComponents();
    }

    public void addTab(final String name, final Component component) {
        tabbedPane.add(name, component);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lHeader = new javax.swing.JLabel();
        tabbedPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lHeader.setFont(new java.awt.Font("Tahoma", 1, 36));
        lHeader.setText("BAI");
        getContentPane().add(lHeader, java.awt.BorderLayout.PAGE_START);

        tabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        getContentPane().add(tabbedPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lHeader;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
