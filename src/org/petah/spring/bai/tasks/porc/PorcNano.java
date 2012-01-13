/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.porc;

import com.springrts.ai.oo.AIFloat3;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class PorcNano extends Task {

   // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PorcNano.updateTime", 150));
    // Class properties
    private int nextUpdate = 0;

    public PorcNano(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                CachedUnit reclaim = null;
                if (!aiDelegate.getResourceManager().isMetalOver(0.25f)) {
                    if (reclaim == null) {
                        reclaim = getReclaimableEnergy(aiDelegate, unit);
                    }
                }
                if (reclaim != null) {
                    unit.stop();
                    System.err.println("Reclaiming");
                    unit.reclaimUnit(reclaim.getUnit());
                } else if (aiDelegate.getResourceManager().isEnergyOver(500)) {
                    unit.setMoveState(CachedUnit.MOVE_STATE_ROAM);
                    AIFloat3 pos = unit.getPos();
                    pos.x += Math.random() * 500 - 250;
                    pos.z += Math.random() * 500 - 250;
                    System.err.println("Patroling");
                    unit.patrolTo(pos);
                } else {
                    System.err.println("Stoping");
                    unit.stop();
                }
            }
//            }
        }
        return false;
    }

    public static CachedUnit getReclaimableEnergy(AIDelegate aiDelegate, CachedUnit unit) {
        CachedUnit closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (CachedUnit building : aiDelegate.getGroupManager().getGroup("energyGenerators")) {
            UnitInfo buildingInfo = building.getUnitInfo();
            if (buildingInfo.getName().equals("Solar") || buildingInfo.getName().equals("Wind")) {
                AIFloat3 unitPos = unit.getPos();
                AIFloat3 buildingPos = building.getPos();
                float distance = GameMath.pointDistance(unitPos.x, unitPos.y, buildingPos.x, buildingPos.y);
                if (closest == null || distance < closestDistance) {
                    if (unit.getDef().getBuildDistance() > distance) {
                        closest = building;
                        closestDistance = distance;
                    }
                }
            }
        }
        return closest;
    }
}
