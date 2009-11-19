/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.tasks.*;
import com.springrts.ai.AIFloat3;
import java.util.LinkedList;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.map.control.ControlZone;
import org.petah.spring.bai.util.CommandUtil;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class DefaultScout extends Task {

    // Scouting options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultScout.updateTime", 200));
    private static Option<Integer> controlZoneMinAge = OptionsManager.getOption(
            new Option<Integer>("DefaultScout.controlZoneMinAge", -5));
    // Class properties
    private float nextUpdate = 0;

    public DefaultScout(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            scoutClosestZone(group);
        }
        return false;
    }

    private void scoutRandomZone(UnitGroup group) {
        List<ControlZone> zones = aiDelegate.getTeamDelegate().getControlMap().getNeutralControlZones();
        List<ControlZone> oldZones = new LinkedList<ControlZone>();
        for (ControlZone zone : zones) {
            if (zone.getAge() < controlZoneMinAge.getValue()) {
                oldZones.add(zone);
            }
        }
        for (CachedUnit unit : group) {
            if (oldZones.size() > 0) {
                ControlZone controlZone = oldZones.get((int) (oldZones.size() * Math.random()));
                CommandUtil.move(aiDelegate, unit, new AIFloat3(controlZone.getTerrainCenterX(), 0, controlZone.getTerrainCenterZ()), false);
            }
        }
    }

    private void scoutClosestZone(UnitGroup group) {
        List<ControlZone> zones = aiDelegate.getNeutralControlZones();
        zones.addAll(aiDelegate.getEnemyControlZones());
        for (CachedUnit unit : group) {
            if (unit.getCurrentCommands().size() == 0) {
                ControlZone controlZone = getClosestControlZone(zones, unit.getPos(), controlZoneMinAge.getValue());
                if (controlZone != null) {
                    AIFloat3 moveTo = new AIFloat3(controlZone.getTerrainX(), 0, controlZone.getTerrainZ());
                    moveTo.x += Math.random() * MapUtil.mapToTerrain(ControlZone.getSize());
                    moveTo.z += Math.random() * MapUtil.mapToTerrain(ControlZone.getSize());
                    CommandUtil.move(aiDelegate, unit, moveTo, false);
                }
            }
        }
    }

    public static ControlZone getClosestControlZone(List<ControlZone> zones, AIFloat3 pos, int maxAge) {
        ControlZone closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (ControlZone zone : zones) {
            if (zone.getAge() < maxAge) {
                float distance = GameMath.pointDistance(zone.getTerrainCenterX(), zone.getTerrainCenterZ(), pos.x, pos.z);
                if (closest == null ||
                        distance < closestDistance) {
                    closestDistance = distance;
                    closest = zone;
                }
            }
        }
        return closest;
    }
}

