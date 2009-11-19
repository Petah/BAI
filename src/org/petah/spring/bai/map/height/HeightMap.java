/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.height;

import com.springrts.ai.AIFloat3;
import com.springrts.ai.oo.Map;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.Resource;
import com.springrts.ai.oo.UnitDef;

/**
 *
 * @author Petah
 */
public class HeightMap {

    private static Map map;

    public static synchronized void init(OOAICallback callback) {
        map = callback.getMap();
    }

    public static float getTerrainElevationAt(int x, int z) {
        return getElevationAt(x / 8, z / 8) * 8;
    }

    //Delegates

    public static int getWidth() {
        return map.getWidth();
    }

    public static float getMinHeight() {
        return map.getMinHeight();
    }

    public static float getMaxHeight() {
        return map.getMaxHeight();
    }

    public static int getHeight() {
        return map.getHeight();
    }

    public static float getElevationAt(float x, float z) {
        return map.getElevationAt(x, z);
    }

}
