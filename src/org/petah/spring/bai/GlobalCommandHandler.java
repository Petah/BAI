/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.petah.common.commandline.ArgumentList;
import org.petah.common.commandline.ArgumentParser;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.gui.GUIManager;
import org.petah.spring.bai.util.CustomOptionIO;

/**
 *
 * @author Petah
 */
public class GlobalCommandHandler {

    private static ArgumentList argumentList;
    private static CurrentCommand currentCommand;

    private static boolean processOnce(int player, String argument) {
        if (argumentList.hasArgument(argument)) {
            CurrentCommand newCommand = new CurrentCommand(player, argument);
            if (currentCommand == null || !currentCommand.equals(newCommand)) {
                currentCommand = newCommand;
                ThreadManager.run("GlobalCommandHandler.processOnce()", new Runnable() {

                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GlobalCommandHandler.class.getName()).log(Level.SEVERE, "GlobalCommandHandler command reset timer interupted.", ex);
                        }
                        currentCommand = null;
                    }
                });
                return true;
            }
        }
        return false;
    }

    private static boolean processAll(String argument) {
        if (argumentList.hasArgument(argument)) {
            return true;
        }
        return false;
    }

    public static void processCommand(AIDelegate aiDelegate, int player, String message) {
        argumentList = ArgumentParser.parse(message.split(" "));
        if (processOnce(player, "-savesettings")) {
            CustomOptionIO.save(InformationLogger.getSettingsFile());
            aiDelegate.sendMessage("Options saved to: " + InformationLogger.getSettingsFile().getAbsolutePath());
        }
        if (processOnce(player, "-showgui")) {
            aiDelegate.sendMessage("Showing GUI");
            GUIManager.showGUI();
        }
        if (processOnce(player, "-hidegui")) {
            aiDelegate.sendMessage("Hiding GUI");
            GUIManager.hideGUI();
        }
        if (processOnce(player, "-dir")) {
            aiDelegate.sendMessage("Current directory: " + new File(".").getAbsolutePath());
            File jarFile = new File(AIDelegate.class.getProtectionDomain().getCodeSource().getLocation().getFile());
            aiDelegate.sendMessage("Jar file: " + jarFile.getAbsolutePath());
            aiDelegate.sendMessage("AI directory: " + InformationLogger.getAiDirectory().getAbsoluteFile());
            aiDelegate.sendMessage("Log directory: " + InformationLogger.getLogDirectory().getAbsoluteFile());
            aiDelegate.sendMessage("Log archive directory: " + InformationLogger.getLogArchiveDirectory().getAbsoluteFile());
            aiDelegate.sendMessage("Settings directory: " + InformationLogger.getSettingsDirectory().getAbsoluteFile());
        }
//        if (processOnce(player, "-map")) {
//            aiDelegate.sendMessage("Map size: " + CachedMap.getWidth() + ", " + CachedMap.getHeight());
//        }
//        if (processOnce(player, "-movedata")) {
//            for (CachedUnitDef def : CacheManager.getUnitDefs()) {
//                System.err.println(def.getHumanName());
//                System.err.println(def.getMoveData().getMaxSlope());
//                System.err.println(def.getMoveData().getName());
//            }
//        }
//        if (processOnce(player, "-unitdef")) {
//            ObjectOutputStream outputStream = null;
//            try {
//                File file = new File("unitdef.bin");
//                aiDelegate.sendMessage("Writing unit defs to: " + file.getAbsolutePath());
//                outputStream = new ObjectOutputStream(new FileOutputStream(file));
//                outputStream.writeObject(CacheManager.getUnitDefArray());
//            } catch (IOException ex) {
//                Logger.getLogger(LocalCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                try {
//                    outputStream.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(LocalCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        if (processOnce(player, "-slope")) {
//            ObjectOutputStream outputStream = null;
//            try {
//                File file = new File("slopemap.bin");
//                aiDelegate.sendMessage("Writing slope map to: " + file.getAbsolutePath());
//                outputStream = new ObjectOutputStream(new FileOutputStream(file));
//                outputStream.writeObject(CachedMap.getSlopeMap());
//            } catch (IOException ex) {
//                Logger.getLogger(LocalCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                try {
//                    outputStream.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(LocalCommandHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }

        if (processAll("-hi")) {
            aiDelegate.sendMessage("Hello World");
        }
        if (processAll("-allcheat")) {
            aiDelegate.sendMessage("Enabling Cheats");
            aiDelegate.getCallback().getCheats().setEnabled(true);
        }
    }
}

class CurrentCommand {

    private int player;
    private String message;

    public CurrentCommand(int player, String message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public String toString() {
        return "CurrentCommand[" + player + "," + message + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CurrentCommand other = (CurrentCommand) obj;
        if (this.player != other.player) {
            return false;
        }
        if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.player;
        hash = 79 * hash + (this.message != null ? this.message.hashCode() : 0);
        return hash;
    }
}
