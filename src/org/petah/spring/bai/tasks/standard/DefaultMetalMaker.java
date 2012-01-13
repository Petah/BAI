/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.util.CommandUtil;
// TODO: check this is working

/**
 *
 * @author Petah
 */
public class DefaultMetalMaker extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultMetalMaker.updateTime", 30));
    // Class properties
    private int nextUpdate = 0;

    public DefaultMetalMaker(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
//                        (aiDelegate.getResourceManager().getEnergyIncome() < aiDelegate.getResourceManager().getEnergyUsage() ||
//                if (!aiDelegate.getResourceManager().isEnergyOver(0.5f)) {
//                    if (unit.getEnergyUse() > 0) {
//                        CommandUtil.setOnOff(aiDelegate, unit, false);
//                        break;
//                    }
//                } else if (aiDelegate.getResourceManager().getEnergyIncome() > aiDelegate.getResourceManager().getEnergyUsage() + 60 &&
//                        unit.getEnergyUse() == 0 && aiDelegate.getResourceManager().isEnergyOver(0.5f)) {
//                    CommandUtil.setOnOff(aiDelegate, unit, true);
//                    break;
//                }
                if (!aiDelegate.getResourceManager().isEnergyOver(0.4f)) {
                    unit.setOn(false);
                } else if (aiDelegate.getResourceManager().isEnergyOver(0.6f)) {
                    unit.setOn(true);
                }
            }
        }
        return false;
    }
}
