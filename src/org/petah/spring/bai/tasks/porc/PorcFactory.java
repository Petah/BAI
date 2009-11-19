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
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class PorcFactory extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PorcFactory.updateTime", 20));
    private static Option<Integer> maxBuilders = OptionsManager.getOption(
            new Option<Integer>("PorcFactory.updateTime", 4));
    // Class properties
    private int nextUpdate = 0;

    public PorcFactory(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                UnitInfo info = UnitInfo.getUnitInfo(unit.getDef());
                if (info.isType(UnitType.Factory)) {
                    if (unit.getCurrentCommands().size() == 0) {
                        if (info.getName().equals("KbotFactory")) {
                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "KT1Builder", unit);
                            }
                        } else if (info.getName().equals("T2KbotFactory")) {
                            if (aiDelegate.getGroupManager().getGroup("t2Builders").size() < maxBuilders.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "KT2Builder", unit);
                            }
                        } else if (info.getName().equals("AircraftFactory")) {
                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "AT1Builder", unit);
                            }
                        } else if (info.getName().equals("T2AircraftFactory")) {
                            if (aiDelegate.getGroupManager().getGroup("t2Builders").size() < maxBuilders.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "AT2Builder", unit);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
