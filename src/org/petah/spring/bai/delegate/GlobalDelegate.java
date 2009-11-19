/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.delegate;

import com.springrts.ai.oo.MoveData;
import com.springrts.ai.oo.OOAICallback;
import java.util.TreeMap;
import org.petah.spring.bai.cache.CachedHeightMap;
import org.petah.spring.bai.cache.CachedMap;
import org.petah.spring.bai.cache.CachedMetalMap;
import org.petah.spring.bai.cache.CachedMoveData;
import org.petah.spring.bai.cache.CachedMoveDataManager;
import org.petah.spring.bai.cache.CachedSlopeMap;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.cache.CachedUnitDefManager;
import org.petah.spring.bai.map.slope.IslandMap;
import org.petah.spring.bai.map.slope.IslandMapManager;
import org.petah.spring.bai.unit.Faction;

/**
 *
 * @author Petah
 */
public class GlobalDelegate {

    private static CachedMap cachedMap;
    private static CachedMetalMap cachedMetalMap;
    private static CachedSlopeMap cachedSlopeMap;
    private static CachedHeightMap cachedHeightMap;
    private static CachedMoveDataManager cachedMoveDataManager;
    private static CachedUnitDefManager cachedUnitDefManager;
    private static IslandMapManager islandMapManager;

    public static void init(OOAICallback callback) {
        // All are dependant on InformationLogger for the cache directory
        cachedMap = new CachedMap(callback.getMap());
        cachedMetalMap = new CachedMetalMap(callback.getMap());
        cachedSlopeMap = new CachedSlopeMap(callback.getMap());
        cachedHeightMap = new CachedHeightMap(callback.getMap());
        cachedMoveDataManager = new CachedMoveDataManager(callback);
        cachedUnitDefManager = new CachedUnitDefManager(callback); // Dependant on CachedMoveDataManager
        islandMapManager = new IslandMapManager(); // Dependant on CachedSlopeMap
    }

    // Delegate methods
    public static int getMapWidth() {
        return cachedMap.getWidth();
    }

    public static int getMapHeight() {
        return cachedMap.getHeight();
    }

    public static int getSlopeMapWidth() {
        return cachedSlopeMap.getWidth();
    }

    public static int getSlopeMapHeight() {
        return cachedSlopeMap.getHeight();
    }

    public static Float getSlopeValue(int x, int y) {
        return cachedSlopeMap.getValue(x, y);
    }

    public static float getMetalExtractorRadius() {
        return cachedMetalMap.getMetalExtractorRadius();
    }

    public static float getMetalExtractorTerrainRadius() {
        return cachedMetalMap.getMetalExtractorTerrainRadius();
    }

    public static CachedUnitDef getUnitDef(String name, Faction faction) {
        return cachedUnitDefManager.getUnitDef(name, faction);
    }

    public static CachedUnitDef getCachedUnitDef(int unitDefId) {
        return cachedUnitDefManager.getCachedUnitDef(unitDefId);
    }

    public static TreeMap<Integer, CachedUnitDef> getUnitDefs() {
        return cachedUnitDefManager.getUnitDefs();
    }

    public static TreeMap<String, CachedMoveData> getCachedMoveData() {
        return cachedMoveDataManager.getCachedMoveData();
    }

    public static CachedMoveData getCachedMoveData(MoveData data) {
        return cachedMoveDataManager.getCachedMoveData(data);
    }

    public static IslandMap getIslandMap(CachedMoveData moveData) {
        return islandMapManager.getIslandMap(moveData);
    }

    // Getters
    public static CachedHeightMap getCachedHeightMap() {
        return cachedHeightMap;
    }

    public static CachedMap getCachedMap() {
        return cachedMap;
    }

    public static CachedMetalMap getCachedMetalMap() {
        return cachedMetalMap;
    }

    public static CachedMoveDataManager getCachedMoveDataManager() {
        return cachedMoveDataManager;
    }

    public static CachedSlopeMap getCachedSlopeMap() {
        return cachedSlopeMap;
    }

    public static CachedUnitDefManager getCachedUnitDefManager() {
        return cachedUnitDefManager;
    }

    public static IslandMapManager getIslandMapManager() {
        return islandMapManager;
    }
}
