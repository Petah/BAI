/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.slope;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.petah.spring.bai.cache.CachedMoveData;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class IslandMap {

    // Class properties
    private short[][] islandMap;
    private short maxValue = 1;

    // Class constructors
    public IslandMap() {
        islandMap = new short[GlobalDelegate.getSlopeMapWidth()][GlobalDelegate.getSlopeMapHeight()];
    }

    // Class methods
    public void setValue(int x, int y, short value) {
        islandMap[x][y] = value;
    }

    public short[][] getIslandMap() {
        return islandMap;
    }

    public short getValue(int x, int y) {
        return islandMap[x][y];
    }

    public void incMaxValue() {
        maxValue++;
    }

    public short getMaxValue() {
        return maxValue;
    }
}
