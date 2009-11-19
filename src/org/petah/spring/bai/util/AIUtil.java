/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.util;

import com.springrts.ai.AIFloat3;
import org.petah.spring.bai.cache.CachedUnit;

/**
 *
 * @author Petah
 */
public class AIUtil {

    public static AIFloat3 getCenterPosition(Iterable<CachedUnit> units) {
        float highestX = Float.MIN_VALUE;
        float highestZ = Float.MIN_VALUE;
        float lowestX = Float.MAX_VALUE;
        float lowestZ = Float.MAX_VALUE;
        for (CachedUnit unit : units) {
            AIFloat3 pos = unit.getPos();
            if (pos.x < lowestX) {
                lowestX = pos.x;
            }
            if (pos.z < lowestZ) {
                lowestZ = pos.z;
            }
            if (pos.x > highestX) {
                highestX = pos.x;
            }
            if (pos.z > highestZ) {
                highestZ = pos.z;
            }
        }

        return new AIFloat3(lowestX + ((highestX - lowestX) / 2), 0, lowestZ + ((highestZ - lowestZ) / 2));
    }
}
