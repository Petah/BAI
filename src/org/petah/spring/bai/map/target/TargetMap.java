/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.target;

import com.springrts.ai.oo.AIFloat3;
import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import org.petah.common.event.UpdateAdapter;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.AIReturnCode;
import org.petah.spring.bai.ThreadManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.TeamDelegate;
import org.petah.spring.bai.listener.UpdateEventListener;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;

/**
 *
 * @author Petah
 */
public class TargetMap extends UpdateAdapter implements UpdateEventListener {

    // Target map options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("TargetMap.updateTime", 40));
    private static Option<Integer> maxDistance = OptionsManager.getOption(
            new Option<Integer>("TargetMap.maxDistance", 1000));
    // Class properties
    private TeamDelegate teamDelegate;
    private Map<TargetType, CopyOnWriteArrayList<TargetZone>> targetZones = new ConcurrentHashMap<TargetType, CopyOnWriteArrayList<TargetZone>>();
    /**
     * Next frame to update the target map on
     */
    private int nextUpdate = 0;
    /**
     * Future object returned from the update thread
     */
    private Future updateFuture;

    public TargetMap(TeamDelegate teamDelegate) {
        this.teamDelegate = teamDelegate;
    }

    /**
     * Updates the target map periodically.
     * @param frame the current frame
     */
    public int update(int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            if (updateFuture == null || updateFuture.isDone()) {
                updateFuture = ThreadManager.run("TargetMap.update()", new Runnable() {

                    public void run() {
                        update();
                    }
                });
            }
        }
        return AIReturnCode.NORMAL;
    }

    private void update() {
        Profiler.start(TargetMap.class, "update()");
        targetZones.clear();
        for (CachedUnit unit : teamDelegate.getEnemyUnits().values()) {
            AIFloat3 pos = unit.getPos();
            TargetType type = getTargetType(unit);
            TargetZone zone = getClosestTargetZone(pos, type);
            if (zone == null) {
                zone = new TargetZone(type);
                CopyOnWriteArrayList<TargetZone> zones = targetZones.get(type);
                if (zones == null) {
                    zones = new CopyOnWriteArrayList<TargetZone>();
                    targetZones.put(type, zones);
                }
                zones.add(zone);
            }
            zone.addUnit(unit);
        }
        fireUpdate(this);
        Profiler.stop(TargetMap.class, "update()");
    }

    private TargetType getTargetType(CachedUnit unit) {
        UnitInfo info = unit.getUnitInfo();
        if (info != null) {
            if (info.isType(UnitType.MetalExtractor) ||
                    info.isType(UnitType.MetalMaker) ||
                    info.isType(UnitType.MetalStorage)) {
                return TargetType.Metal;
            } else if (info.isType(UnitType.EnergyGenerator) ||
                    info.isType(UnitType.EnergyStorage)) {
                return TargetType.Energy;
            } else if (info.isType(UnitType.Nuke)) {
                return TargetType.Nuke;
            } else if (info.isType(UnitType.AntiNuke)) {
                return TargetType.AntiNuke;
            } else if (info.isType(UnitType.AntiAir)) {
                return TargetType.AntiAir;
            } else if (info.isType(UnitType.DefenseTower)) {
                return TargetType.DefenceTowers;
            } else if (info.isType(UnitType.Factory)) {
                return TargetType.Factory;
            } else if (info.isType(UnitType.Nano)) {
                return TargetType.Nanos;
            } else if (info.isType(UnitType.Building)) {
                return TargetType.Buildings;
            } else if (info.isType(UnitType.Commander)) {
                return TargetType.Commander;
            } else if (info.isType(UnitType.Builder) ||
                    info.isType(UnitType.Scavenger)) {
                return TargetType.Builders;
            } else if (info.isType(UnitType.Aircraft)) {
                return TargetType.Aircraft;
            } else if (info.isType(UnitType.Hovercraft)) {
                return TargetType.Hovercraft;
            } else if (info.isType(UnitType.Ship) ||
                    info.isType(UnitType.Submarine)) {
                return TargetType.Ships;
            } else if (info.isType(UnitType.Kbot) ||
                    info.isType(UnitType.Vehicle)) {
                return TargetType.Units;
            }
        }
        return TargetType.Unknown;
    }

    private TargetZone getClosestTargetZone(AIFloat3 pos, TargetType type) {
        if (targetZones.containsKey(type)) {
            for (TargetZone zone : targetZones.get(type)) {
                if (zone.getTargetType() == type) {
                    AIFloat3 center = zone.getPosition();
                    float distance = GameMath.pointDistance(center.x, center.z, pos.x, pos.z);
                    if (distance < maxDistance.getValue()) {
                        return zone;
                    }
                }
            }
        }
        return null;
    }

    public List<TargetZone> getTargetZones() {
        List<TargetZone> zones = new LinkedList<TargetZone>();
        Iterator<TargetType> i = targetZones.keySet().iterator();
        while (i.hasNext()) {
            CopyOnWriteArrayList<TargetZone> targetZoneList = targetZones.get(i.next());
            if (targetZoneList != null) {
                zones.addAll(targetZoneList);
            }
        }
//        for (TargetType type : targetZones.keySet()) {
//            zones.addAll(targetZones.get(type));
//        }
        return zones;
    }

    public List<TargetZone> getTargetZones(TargetType type) {
        return targetZones.get(type);
    }
}
