/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.petah.common.commandline.Argument;
import org.petah.common.commandline.ArgumentList;
import org.petah.common.commandline.ArgumentParser;
import org.petah.spring.bai.cache.CachedMap;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.listener.MessageEventListener;

/**
 *
 * @author Petah
 */
public class LocalCommandHandler implements MessageEventListener {

    private AIDelegate aiDelegate;
    private ArgumentList argumentList;

    public LocalCommandHandler(AIDelegate aiDelegate) {
        this.aiDelegate = aiDelegate;
        aiDelegate.getAIEventHandler().addMessageEventListener(this);
    }

    private boolean checkArgument(String argument) {
        if (argumentList.hasArgument(argument)) {
            if (argumentList.hasValue(argument)) {
                try {
                    int i = Integer.parseInt(argumentList.getValue(argument));
                    if (i == aiDelegate.getTeamID()) {
                        return true;
                    }
                } catch (NumberFormatException ex) {
                    aiDelegate.sendMessage(argument + " expects team ID, argument is invalid.");
                }
            } else {
                aiDelegate.sendMessage(argument + " expects team ID.");
            }
        }
        return false;
    }

    public int message(int player, String message) {
        if (message.startsWith(ArgumentParser.getArgumentPrefix())) {
            GlobalCommandHandler.processCommand(aiDelegate, player, message);
            processCommand(message);
        }
        return AIReturnCode.NORMAL;
    }

    private void processCommand(String message) {
        argumentList = ArgumentParser.parse(message.split(" "));
        if (GlobalOptions.isDebug()) {
            Logger.getLogger(LocalCommandHandler.class.getName()).info("AI processed '" + message + "'");
            Logger.getLogger(LocalCommandHandler.class.getName()).info("Commands: ");
            for (Argument a : argumentList) {
                Logger.getLogger(LocalCommandHandler.class.getName()).info(a.toString());
            }
        }
        if (checkArgument("-cheat")) {
            aiDelegate.sendMessage("Enabling Cheats");
            aiDelegate.getCallback().getCheats().setEnabled(true);
        }
        if (checkArgument("-res")) {
            aiDelegate.sendMessage("Metal: " + aiDelegate.getResourceManager().getMetalCurrent() +
                    "/" + aiDelegate.getResourceManager().getMetalStorage() +
                    " +" + aiDelegate.getResourceManager().getMetalIncome() +
                    " -" + aiDelegate.getResourceManager().getMetalUsage());
            aiDelegate.sendMessage("Energy: " + aiDelegate.getResourceManager().getEnergyCurrent() +
                    "/" + aiDelegate.getResourceManager().getEnergyStorage() +
                    " +" + aiDelegate.getResourceManager().getEnergyIncome() +
                    " -" + aiDelegate.getResourceManager().getEnergyUsage());
        }
    }
}
