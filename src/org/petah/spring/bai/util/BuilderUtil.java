/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import com.springrts.ai.AIFloat3;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.ResourceManager;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.unit.Faction;

/**
 *
 * @author Petah
 */
public class BuilderUtil {

    public static CachedUnitDef getBestT1Energy(Faction faction) {
        CachedUnitDef energyGen;
        // If wind is low, only build solar
        if (ResourceManager.getAverageWind() < 9) {
            energyGen = GlobalDelegate.getUnitDef("Solar", faction);
        } // If wind is high, only build wind
        else if (ResourceManager.getAverageWind() > 14) {
            energyGen = GlobalDelegate.getUnitDef("Wind", faction);
        } // Otherwise randomly build both
        else if (Math.random() < 0.5) {
            energyGen = GlobalDelegate.getUnitDef("Wind", faction);
        } else {
            energyGen = GlobalDelegate.getUnitDef("Solar", faction);
        }
        return energyGen;
    }

    // Positioning
    public static AIFloat3 moveAroundCenter(AIDelegate aiDelegate, AIFloat3 pos, float direction, int distance) {
        return moveTowardsDirection(aiDelegate, pos, getMapCenterDirection(pos) + direction, distance);
    }

    public static AIFloat3 moveTowardsDirection(AIDelegate aiDelegate, AIFloat3 pos, float direction, int distance) {
        GameMath.wrapAboveZero(direction, 360);
        AIFloat3 newPos = new AIFloat3(pos);
        newPos.x += GameMath.lengthDirX(distance, direction);
        newPos.z += GameMath.lengthDirY(distance, direction);
        return newPos;
    }

    public static AIFloat3 moveTowardsMapCenter(AIFloat3 pos, int distance) {
        return moveMapCenter(pos, distance, false);
    }

    public static AIFloat3 moveAwayFromMapCenter(AIFloat3 pos, int distance) {
        return moveMapCenter(pos, distance, true);
    }

    private static AIFloat3 moveMapCenter(AIFloat3 pos, int distance, boolean reverse) {
        AIFloat3 newPos = new AIFloat3(pos);
        float direction = getMapCenterDirection(newPos);
        if (reverse) {
            direction -= 180;
            GameMath.wrapAboveZero(direction, 360);
        }
        newPos.x += GameMath.lengthDirX(distance, direction);
        newPos.z += GameMath.lengthDirY(distance, direction);
        return newPos;
    }

    public static AIFloat3 getMapTerrainCenter() {
        return new AIFloat3(MapUtil.mapToTerrain(GlobalDelegate.getMapWidth()) / 2, 0, MapUtil.mapToTerrain(GlobalDelegate.getMapHeight()) / 2);
    }

    public static float getMapCenterDirection(AIFloat3 pos) {
        return GameMath.pointDirection(pos.x, pos.z,
                MapUtil.mapToTerrain(GlobalDelegate.getMapWidth()) / 2, MapUtil.mapToTerrain(GlobalDelegate.getMapHeight()) / 2);
    }
}

