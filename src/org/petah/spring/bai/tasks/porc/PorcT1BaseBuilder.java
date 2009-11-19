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
import org.petah.spring.bai.tasks.Task;

/**
 *
 * @author Petah
 */
public class PorcT1BaseBuilder extends Task {

   // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PorcT1BaseBuilder.updateTime", 20));
    private static Option<Integer> maxEnergyAmount = OptionsManager.getOption(
            new Option<Integer>("PorcT1BaseBuilder.maxEnergyAmount", 14));
    private static Option<Integer> nanoMinEnergyAmount = OptionsManager.getOption(
            new Option<Integer>("PorcT1BaseBuilder.nanoMinEnergyAmount", 9));
    private static Option<Integer> maxNanoAmount = OptionsManager.getOption(
            new Option<Integer>("PorcT1BaseBuilder.maxNanoAmount", 12));
    // Class properties
    private int nextUpdate = 0;

    public PorcT1BaseBuilder(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            if (group.size() > 0) {
                CachedUnit builder = group.getUnit(0);
                if (builder.getCurrentCommands().size() == 0) {
                    int energyAmount = aiDelegate.getGroupManager().getGroup("energyGenerators").size();
                    int nanoAmount = aiDelegate.getGroupManager().getGroup("nanos").size();
                    if ((energyAmount - 6 <= nanoAmount || nanoAmount >= maxNanoAmount.getValue()) &&
                            energyAmount < maxEnergyAmount.getValue()) {
                        if (energyAmount >= nanoMinEnergyAmount.getValue()) {
                            aiDelegate.getGroupManager().getGroup("nanos").clearTasks();
                            aiDelegate.getGroupManager().getGroup("nanos").addTask(new PorcNano(aiDelegate));
//                            aiDelegate.getGroupManager().getGroup("factorys").clearTasks();
//                            aiDelegate.getGroupManager().getGroup("factorys").addTask(new DefaultFactory(aiDelegate));
                        }
                        PorcCommands.queueAdvSolar(aiDelegate, builder);
                    } else if (nanoAmount < maxNanoAmount.getValue()) {
                        PorcCommands.queueNano(aiDelegate, builder);
                    }
//                    else {
//                        if (aiDelegate.getGroupManager().getGroup("t2Factorys").size() == 0) {
////                            queueT2Factory(aiDelegate, builder);
////                            aiDelegate.getGroupManager().getGroup("expansionBuilders").clearTasks();
////                            aiDelegate.getGroupManager().getGroup("expansionBuilders").addTask(new PorcExpansionBuilderStage2(aiDelegate));
//                        }
//                    }
                }
            }
        }
        return false;
    }
}
