/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.porc;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.standard.DefaultCommands;
import org.petah.spring.bai.tasks.Task;

/**
 *
 * @author Petah
 */
public class PorcCommanderStage1 extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PorcCommanderStage1.updateTime", 20));
    // Class properties
    private int nextUpdate = 0;
    // TODO: convert to options
    private static final int MEX_AMOUNT = 3;
    private static final int ENERGY_PRE_AMOUNT = 3;
    private static final int ENERGY_POST_AMOUNT = 6;

    public PorcCommanderStage1(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            if (group.size() > 0) {
                // TODO: replace deprecated calls
                CachedUnit builder = group.getUnit(0);
                if (builder.getCurrentCommands().size() == 0) {
                    if (aiDelegate.getGroupManager().getGroup("metalExtractors").size() < MEX_AMOUNT) {
                        DefaultCommands.queueClosestMetalExtractor(aiDelegate, builder);
                    } else if (aiDelegate.getGroupManager().getGroup("energyGenerators").size() < ENERGY_PRE_AMOUNT) {
                        PorcCommands.queueSolarGenerator(aiDelegate, builder);
                    } else if (aiDelegate.getGroupManager().getGroup("factorys").size() < 1) {
                        if (aiDelegate.getGroupManager().getGroup("energyStorage").size() == 0) {
                            PorcCommands.queueEnergyStorage(aiDelegate, builder);
                        } else {
                            PorcCommands.queueFactory(aiDelegate, builder);
                        }
                    } else if (aiDelegate.getGroupManager().getGroup("energyGenerators").size() < ENERGY_POST_AMOUNT) {
                        PorcCommands.queueEnergyGenerator(aiDelegate, builder);
                    } else {
                        group.addTask(new PorcCommanderStage2(aiDelegate));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
