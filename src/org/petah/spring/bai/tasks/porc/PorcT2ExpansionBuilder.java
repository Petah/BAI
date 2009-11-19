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
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;

/**
 *
 * @author Petah
 */
public class PorcT2ExpansionBuilder extends Task {

   // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PorcT2ExpansionBuilder.updateTime", 20));
    // Class properties
    private int nextUpdate = 0;

    public PorcT2ExpansionBuilder(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group,  int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                UnitInfo info = UnitInfo.getUnitInfo(unit.getDef());
                if (info.isType(UnitType.Builder) && info.isType(UnitType.Level2) &&
                        unit.getCurrentCommands().size() == 0) {
                    DefaultCommands.queueClosestMetalExtractor(aiDelegate, unit);
                }
            }
        }
        return false;
    }
}
