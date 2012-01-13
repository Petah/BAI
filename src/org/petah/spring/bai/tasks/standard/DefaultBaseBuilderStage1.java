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
public class DefaultBaseBuilderStage1 extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultBaseBuilderStage1.updateTime", 20));
    // Class properties
    private int nextUpdate = 0;

    public DefaultBaseBuilderStage1(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            CachedUnit guardUnit = null;
            for (CachedUnit unit : group) {
                UnitInfo info = UnitInfo.getUnitInfo(unit.getDef());
                if (info.isType(UnitType.Builder) && unit.getCurrentCommands().size() == 0) {
                    if (guardUnit == null) {
                        guardUnit = unit;
                        if (aiDelegate.getResourceManager().isEnergyOver(4000, 450) &&
                                aiDelegate.getResourceManager().isMetalOver(1600)) {
                            aiDelegate.getGroupManager().getGroup("Nanos").clearTasks();
                            aiDelegate.getGroupManager().getGroup("Nanos").addTask(new DefaultNanoStage2(aiDelegate));
                            aiDelegate.getGroupManager().getGroup("Commanders").clearTasks();
                            aiDelegate.getGroupManager().getGroup("Commanders").addTask(new DefaultCommanderStage2(aiDelegate));
                            group.addTask(new DefaultBaseBuilderStage2(aiDelegate));
                            return true;
                        } else if (!aiDelegate.getResourceManager().isEnergyOver(0.5f, 150)) {
                            DefaultCommands.queueAdvSolar(aiDelegate, unit);
                        } else if (aiDelegate.getResourceManager().isMetalOver(250) &&
                                aiDelegate.getGroupManager().getGroup("nanos").size() < 12) {
                            DefaultCommands.queueNano(aiDelegate, unit);
                        } else {
//                        InitBuild.guardFactory(aiDelegate, unit);
                        }
                    } else {
                        unit.guard(guardUnit.getUnit());
                    }
                }
            }
        }
        return false;
    }
}
