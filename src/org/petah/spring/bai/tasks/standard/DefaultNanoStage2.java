/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.spring.bai.delegate.AIDelegate;
import com.springrts.ai.oo.AIFloat3;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.tasks.porc.PorcNano;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultNanoStage2 extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultNanoStage2.updateTime", 150));
    // Class properties
    private int nextUpdate = 0;

    public DefaultNanoStage2(AIDelegate aiDelegate) {
        super(aiDelegate);
    }
// FIXME: Teching not working

    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                CachedUnit factory = getReclaimableFactory(aiDelegate, unit);
                CachedUnit energy = PorcNano.getReclaimableEnergy(aiDelegate, unit);
                if (factory != null) {
                    unit.reclaimUnit(factory.getUnit());
                } else if (!aiDelegate.getResourceManager().isMetalOver(0.2f) && energy != null) {
                    unit.reclaimUnit(energy.getUnit());
                } else if (aiDelegate.getResourceManager().isEnergyOver(500)) {
                    unit.setMoveState(CommandUtil.MOVE_STATE_ROAM);
                    AIFloat3 pos = unit.getPos();
                    pos.x += Math.random() * 500 - 250;
                    pos.z += Math.random() * 500 - 250;
                    unit.patrolTo(pos);
                } else {
                    unit.stop();
                }
            }
        }
        return false;
    }

    public static CachedUnit getReclaimableFactory(AIDelegate aiDelegate, CachedUnit unit) {
        CachedUnit closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (CachedUnit building : aiDelegate.getGroupManager().getGroup("factorys")) {
            UnitInfo buildingInfo = building.getUnitInfo();
            if (buildingInfo.getName().equals("KbotFactory") ||
                    buildingInfo.getName().equals("AircraftFactory") ||
                    buildingInfo.getName().equals("VehicleFactory") ||
                    buildingInfo.getName().equals("ShipFactory")) {
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
