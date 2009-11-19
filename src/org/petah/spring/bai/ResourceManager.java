/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import com.springrts.ai.AICommand;
import com.springrts.ai.command.SendResourcesAICommand;
import com.springrts.ai.oo.Economy;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.Resource;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.listener.UpdateEventListener;

/**
 *
 * @author Petah
 */
public class ResourceManager implements UpdateEventListener {

    // Resource share levels
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("ResourceManager.updateTime", 30));
    private static Option<Float> energyShareLevel = OptionsManager.getOption(
            new Option<Float>("ResourceManager.energyShareLevel", 0.7f));
    private static Option<Float> metalShareLevel = OptionsManager.getOption(
            new Option<Float>("ResourceManager.metalShareLevel", 0.4f));
    // Class properties
    private AIDelegate aiDelegate;
    private Economy economy;
    private int nextUpdate = 0;
    // Resources
    private static Resource metal;
    private static Resource energy;
    private static float minWind;
    private static float maxWind;

    public ResourceManager(AIDelegate aiDelegate) {
        this.aiDelegate = aiDelegate;
        economy = aiDelegate.getEconomy();
        aiDelegate.getAIEventHandler().addUpdateEventListener(this);
    }

    public static void parseResources(OOAICallback callback) {
        Logger.getLogger(ResourceManager.class.getName()).entering(ResourceManager.class.getName(), "init()");
        minWind = callback.getMap().getMinWind();
        maxWind = callback.getMap().getMaxWind();
        // Parse resources
        List<Resource> resources = callback.getResources();
        for (Resource resource : resources) {
            if (resource.getName().equals("Metal")) {
                metal = resource;
            } else if (resource.getName().equals("Energy")) {
                energy = resource;
            } else {
                Logger.getLogger(ResourceManager.class.getName()).log(Level.WARNING, "Cannot parse resource: " + resource.getName());
            }
        }
    }

    public int update(int frame) {
        Profiler.start(ResourceManager.class, "update()");
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            float maxEnergy = aiDelegate.getResourceManager().getEnergyStorage() * energyShareLevel.getValue();
            float maxMetal = aiDelegate.getResourceManager().getMetalStorage() * metalShareLevel.getValue();
            for (AIDelegate other : aiDelegate.getTeamDelegate().getAIDelegates()) {
                if (aiDelegate == other) {
                    continue;
                }
                if (aiDelegate.getResourceManager().getEnergyCurrent() > maxEnergy &&
                        other.getResourceManager().getEnergyCurrent() < maxEnergy) {
                    float shareMax = maxEnergy - other.getResourceManager().getEnergyCurrent();
                    float avalibleShare = aiDelegate.getResourceManager().getEnergyCurrent() - maxEnergy;
                    int shareAmount = (int) Math.min(shareMax, avalibleShare);
                    if (shareAmount > 0) {
                        AICommand command = new SendResourcesAICommand(ResourceManager.getEnergy().getResourceId(),
                                shareAmount, other.getTeamID(), false);
                        try {
                            aiDelegate.handleCommand(command);
                        } catch (Exception e) {
                        }
                    }
                }
                if (aiDelegate.getResourceManager().getMetalCurrent() > maxMetal &&
                        other.getResourceManager().getMetalCurrent() < maxMetal) {
                    float shareMax = maxMetal - other.getResourceManager().getMetalCurrent();
                    float avalibleShare = aiDelegate.getResourceManager().getMetalCurrent() - maxMetal;
                    int shareAmount = (int) Math.min(shareMax, avalibleShare);
                    if (shareAmount > 0) {
                        AICommand command = new SendResourcesAICommand(ResourceManager.getMetal().getResourceId(),
                                shareAmount, other.getTeamID(), false);
                        try {
                            aiDelegate.handleCommand(command);
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        Profiler.stop(ResourceManager.class, "update()");
        return AIReturnCode.NORMAL;
    }
    // Metal

    public boolean isMetalOver(int amount) {
        return getMetalCurrent() > amount;
    }

    public boolean isMetalOver(int amount, int income) {
        return getMetalCurrent() > amount && getMetalIncome() > income;
    }

    public boolean isMetalOver(float percent) {
        return isMetalOver((int) (getMetalStorage() * percent));
    }

    public boolean isMetalOver(float percent, int income) {
        return isMetalOver((int) (getMetalStorage() * percent), income);
    }

    // Energy
    public boolean isEnergyOver(int amount) {
        return getEnergyCurrent() > amount;
    }

    public boolean isEnergyOver(int amount, int income) {
        return getEnergyCurrent() > amount && getEnergyIncome() > income;
    }

    public boolean isEnergyOver(float percent) {
        return isEnergyOver((int) (getEnergyStorage() * percent));
    }

    public boolean isEnergyOver(float percent, int income) {
        return isEnergyOver((int) (getEnergyStorage() * percent), income);
    }

    // Energy economy
    public float getEnergyUsage() {
        return economy.getUsage(energy);
    }

    public float getEnergyStorage() {
        return economy.getStorage(energy);
    }

    public float getEnergyIncome() {
        return economy.getIncome(energy);
    }

    public float getEnergyCurrent() {
        return economy.getCurrent(energy);
    }

    // Metal economy
    public float getMetalUsage() {
        return economy.getUsage(metal);
    }

    public float getMetalStorage() {
        return economy.getStorage(metal);
    }

    public float getMetalIncome() {
        return economy.getIncome(metal);
    }

    public float getMetalCurrent() {
        return economy.getCurrent(metal);
    }

    // Static getters
    public static Resource getEnergy() {
        return energy;
    }

    public static Resource getMetal() {
        return metal;
    }

    public static float getMaxWind() {
        return maxWind;
    }

    public static float getMinWind() {
        return minWind;
    }

    public static float getAverageWind() {
        return maxWind / 2 + minWind / 2;
    }
}
