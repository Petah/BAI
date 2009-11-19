/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.option.gui.POptionTable;
import org.petah.common.util.profiler.gui.PMemoryMonitor;
import org.petah.spring.bai.GlobalOptions;
import org.petah.spring.bai.ThreadManager;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.delegate.TeamDelegate;

/**
 *
 * @author Petah
 */
public class GUIManager {

    // Options
    private static Option<Boolean> enabled = OptionsManager.getOption(
            new Option<Boolean>("GlobalOptions.guiEnabled", true),
            new Option<String>("description", "Enable GUI window"),
            new Option<String>("values", "true, false"));
    // Static properties
    private static String logOutput = "";
    private static String profileOutput = "";
    private static MainFrame frame;
    private static PConsole console;
    private static PMemoryMonitor memoryMonitor;

    public static void init() {
        Logger.getLogger(GUIManager.class.getName()).entering(GUIManager.class.getName(), "init()");
        if (enabled.getValue()) {
            frame = new MainFrame("BAI");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setSize(800, 600);
            addTab("Output Console", console = new PConsole());
            addTab("Memory", memoryMonitor = new PMemoryMonitor());
            ThreadManager.run("PMemoryMonitor.update()", new Runnable() {

                public void run() {
                    try {
                        while (true) {
                            memoryMonitor.update();
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException ex) {
                    }
                }
            });
            addTab("Options", new POptionTable());
            addTab("Island Map", new PIslandMap());
            Logger.getLogger(GUIManager.class.getName()).info("GUI initialized.");
        }
    }

    public static void addAIDelegate(final AIDelegate aiDelegate) {
        if (frame != null) {
            addTab(aiDelegate.getPrefix() + "Groups", new PGroupList(aiDelegate));
        }
    }

    public static void addTeamDelegate(final TeamDelegate teamDelegate) {
        if (frame != null) {
            addTab(teamDelegate.getPrefix() + "Metal Map", new PMetalMap(GlobalDelegate.getCachedMetalMap(), teamDelegate.getMetalSpotManager()));
            addTab(teamDelegate.getPrefix() + "Unit Cache", new PUnitList(teamDelegate));
            addTab(teamDelegate.getPrefix() + "Control Map", new PControlMap(teamDelegate.getControlMap()));
            addTab(teamDelegate.getPrefix() + "Target Map", new PTargetMap(teamDelegate.getTargetMap()));
        }
    }

    public static void addTab(final String name, final Component component) {
        if (frame != null) {
            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    frame.addTab(name, component);
                }
            });
        }
    }

    public static void showGUI() {
        if (frame != null) {
            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    frame.setVisible(true);
                }
            });
        }
    }

    public static void hideGUI() {
        if (frame != null) {
            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    frame.setVisible(false);
                }
            });
        }
    }

    public static void shutDown() {
        if (frame != null) {
            hideGUI();
            frame.dispose();
            frame = null;
        }
    }

    public static void log(Object o) {
        System.out.println(o);
        if (enabled.getValue()) {
            logOutput += o + "\n";
            if (console != null) {
                console.setLogText(logOutput);
            }
        }
    }

    public static void profile(Object o) {
        System.out.println(o);
        if (enabled.getValue()) {
            profileOutput += o + "\n";
            if (console != null) {
                console.setProfileText(profileOutput);
            }
        }
    }
//
//    public static void err(Object o) {
//        System.err.println(o);
//        if (console != null) {
//            console.err(o);
//        }
//    }
}
