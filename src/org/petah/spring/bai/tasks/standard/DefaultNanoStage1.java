/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.spring.bai.delegate.AIDelegate;
import com.springrts.ai.AIFloat3;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultNanoStage1 extends Task {

   // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultNanoStage1.updateTime", 150));
    // Class properties
    private int nextUpdate = 0;

    public DefaultNanoStage1(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group,  int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            // Get the builders
            for (CachedUnit unit : group) {
                UnitInfo info = UnitInfo.getUnitInfo(unit.getDef());
                if (info.isType(UnitType.Nano)) {
                    if (aiDelegate.getResourceManager().isEnergyOver(500)) {
                        CommandUtil.setMoveState(aiDelegate, unit, CommandUtil.MOVE_STATE_ROAM);
                        AIFloat3 pos = unit.getPos();
                        pos.x += Math.random() * 500 - 250;
                        pos.z += Math.random() * 500 - 250;
                        CommandUtil.patrol(aiDelegate, unit, pos, false);
                    } else {
                        CommandUtil.stop(aiDelegate, unit);
                    }
                }
            }
        }
        return false;
    }
}
