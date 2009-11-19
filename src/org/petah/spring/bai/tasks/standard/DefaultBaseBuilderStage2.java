/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.tasks.*;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultBaseBuilderStage2 extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultBaseBuilderStage2.updateTime", 20));
    private static Option<Integer> maxNanos = OptionsManager.getOption(
            new Option<Integer>("DefaultBaseBuilderStage2.maxNanos", 20));
    private static Option<Integer> minMetalForT2Factory = OptionsManager.getOption(
            new Option<Integer>("DefaultBaseBuilderStage2.minMetalForT2Factory", 1500));
    private static Option<Integer> minEnergyForT2Factory = OptionsManager.getOption(
            new Option<Integer>("DefaultBaseBuilderStage2.minEnergyForT2Factory", 4000));
    private static Option<Integer> minEnergyIncomeForT2Factory = OptionsManager.getOption(
            new Option<Integer>("DefaultBaseBuilderStage2.minEnergyIncomeForT2Factory", 450));
    private static Option<Float> buildFusionIfEnergyUnder = OptionsManager.getOption(
            new Option<Float>("DefaultBaseBuilderStage2.buildFusionIfEnergyUnder", 0.5f));
    // Class properties
    private int nextUpdate = 0;

    public DefaultBaseBuilderStage2(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                UnitInfo info = UnitInfo.getUnitInfo(unit.getDef());
                if (info.isType(UnitType.Builder) && unit.getCurrentCommands().size() == 0) {

                    if (aiDelegate.getGroupManager().getGroup("Factorys").size() == 0 &&
                            aiDelegate.getGroupManager().getGroup("T2Factorys").size() == 0) {
                        DefaultCommands.queueT2Factory(aiDelegate, unit);
                    } else if (unit.getUnitInfo().isType(UnitType.Level2)) {
                        if (!aiDelegate.getResourceManager().isEnergyOver(buildFusionIfEnergyUnder.getValue())) {
                            DefaultCommands.queueFusion(aiDelegate, unit);
                        }
                    } else {
                        if (aiDelegate.getGroupManager().getGroup("Nanos").size() < maxNanos.getValue()) {
                            DefaultCommands.queueNano(aiDelegate, unit);
                        }
                    }
//                    if (aiDelegate.getGroupManager().getGroup("T2Factorys").size() > 0 &&
//                            aiDelegate.getGroupManager().getGroup("Nanos").size() < maxNanos.getValue()) {
//                        DefaultCommands.queueNano(aiDelegate, unit);
//                    }if (aiDelegate.getGroupManager().getGroup("Factorys").size() == 0 &&
//                            aiDelegate.getGroupManager().getGroup("T2Factorys").size() == 0 &&
//                            aiDelegate.getResourceManager().isEnergyOver(minEnergyForT2Factory.getValue(), minEnergyIncomeForT2Factory.getValue()) &&
//                            aiDelegate.getResourceManager().isMetalOver(minMetalForT2Factory.getValue())) {
//                        DefaultCommands.queueT2Factory(aiDelegate, unit);
//                    } else if (unit.getUnitInfo().isType(UnitType.Level2)) {
//                        if (!aiDelegate.getResourceManager().isEnergyOver(buildFusionIfEnergyUnder.getValue())) {
//                            DefaultCommands.queueFusion(aiDelegate, unit);
//                        }
//                    } else if (aiDelegate.getGroupManager().getGroup("T2Factorys").size() > 0 &&
//                            aiDelegate.getGroupManager().getGroup("Nanos").size() < maxNanos.getValue()) {
//                        DefaultCommands.queueNano(aiDelegate, unit);
//                    }
                }
            }
        }
        return false;
    }
}
