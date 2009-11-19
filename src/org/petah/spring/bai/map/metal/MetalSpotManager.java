package org.petah.spring.bai.map.metal;

import org.petah.spring.bai.cache.CachedMetalMap;
import com.springrts.ai.AIFloat3;
import java.util.LinkedList;
import java.util.TreeMap;
import org.petah.common.util.GameMath;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.cache.CachedUnitManager;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.util.FormatUtil;

/**
 *
 * @author Petah
 */
public class MetalSpotManager {

    private LinkedList<MetalSpot> metalSpots;
    private LinkedList<MetalZone> metalZones;

    public MetalSpotManager(CachedMetalMap cachedMetalMap) {
        Profiler.start(MetalSpotManager.class, "MetalSpotManager()");
        switch (cachedMetalMap.getMetalMapType()) {
            case Normal:
                metalSpots = MetalFinder.getMetalSpots(cachedMetalMap);
                break;
            case Metal:
                metalZones = MetalFinder.getMetalZones(cachedMetalMap);
                break;
        }
        Profiler.stop(MetalSpotManager.class, "MetalSpotManager()");
    }

    public TreeMap<Float, MetalSpot> getMetalSpotDistance(AIFloat3 pos, CachedUnitManager unitManager) {
        Profiler.start(MetalSpotManager.class, "getMetalSpotDistance()");
        TreeMap<Float, MetalSpot> metalSpotsDistance = new TreeMap<Float, MetalSpot>();
        for (MetalSpot metalSpot : metalSpots) {
            if (!isMetalSpotCaptured(metalSpot, unitManager)) {
                float distance = GameMath.pointDistance(pos.x, pos.z, metalSpot.getTerrainX(), metalSpot.getTerrainZ());
                metalSpotsDistance.put(distance, metalSpot);
            }
        }
        Profiler.stop(MetalSpotManager.class, "getMetalSpotDistance()");
        return metalSpotsDistance;
    }

    public boolean isMetalSpotCaptured(MetalSpot metalSpot, CachedUnitManager unitManager) {
        for (CachedUnit unit : unitManager.getFriendlyUnits().values()) {
            if (isMetalSpotCaptured(metalSpot, unit)) {
                return true;
            }
        }
        for (CachedUnit unit : unitManager.getEnemyUnits().values()) {
            if (isMetalSpotCaptured(metalSpot, unit)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMetalSpotCaptured(MetalSpot metalSpot, CachedUnit unit) {
        if (unit.getDef() != null && unit.getDef().getExtractsMetal() > 0) {
            AIFloat3 pos = unit.getPos();
            float distance = GameMath.pointDistance(pos.x, pos.z, metalSpot.getTerrainX(), metalSpot.getTerrainZ());
            if (distance <= GlobalDelegate.getMetalExtractorTerrainRadius()) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<MetalSpot> getMetalSpots() {
        return metalSpots;
    }

    public LinkedList<MetalZone> getMetalZones() {
        return metalZones;
    }
}
