/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.map.slope;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.cache.CachedMoveData;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class IslandMapManager {

    private HashMap<CachedMoveData, IslandMap> islandMaps = new HashMap<CachedMoveData, IslandMap>();

    public IslandMapManager() {
        Profiler.start(IslandMapManager.class, "IslandMapManager()");
        HashMap<CachedMoveData, ArrayList<String>> slopes = new HashMap<CachedMoveData, ArrayList<String>>();
        // Get all slopes
        for (CachedUnitDef def : GlobalDelegate.getUnitDefs().values()) {
            if (def.getSpeed() > 0) {
                if (def.getMoveData() != null) {
                    if (!slopes.containsKey(def.getMoveData())) {
                        slopes.put(GlobalDelegate.getCachedMoveData(def.getMoveData()), new ArrayList<String>());
                    }
                    slopes.get(def.getMoveData()).add(def.getHumanName());
                }
            }
        }
        for (CachedMoveData moveData : slopes.keySet()) {
            islandMaps.put(moveData, createIslandMap(moveData));
        }
        Profiler.stop(IslandMapManager.class, "IslandMapManager()");
    }

    private IslandMap createIslandMap(CachedMoveData moveData) {
        float maxSlope = moveData.getMaxSlope();
        float maxDepth = moveData.getDepth();
        boolean[][] slopeMap = new boolean[GlobalDelegate.getSlopeMapWidth()][GlobalDelegate.getSlopeMapHeight()];
        for (int y = 0; y < GlobalDelegate.getSlopeMapHeight(); y++) {
            for (int x = 0; x < GlobalDelegate.getSlopeMapWidth(); x++) {
                if (GlobalDelegate.getSlopeValue(x, y) > maxSlope) {
                    slopeMap[x][y] = false;
                } else {
                    slopeMap[x][y] = true;
                }
            }
        }

        IslandMap island = new IslandMap();
        for (int y = 0; y < GlobalDelegate.getSlopeMapHeight(); y++) {
            for (int x = 0; x < GlobalDelegate.getSlopeMapWidth(); x++) {
                if (slopeMap[x][y] == true) {
                    computeIslandMap(x, y, slopeMap, island);
                    island.incMaxValue();
                }
            }
        }
        return island;
    }

    private void computeIslandMap(int x, int y, boolean[][] slopeMap, IslandMap island) {
        LinkedList<Point> queue = new LinkedList<Point>();
        queue.add(new Point(x, y));
        while (!queue.isEmpty()) {
            Point p = queue.pop();
            if (slopeMap[p.x][p.y]) {
                slopeMap[p.x][p.y] = false;
                island.setValue(p.x, p.y, island.getMaxValue());
                if (p.x + 1 < GlobalDelegate.getSlopeMapWidth() &&
                        slopeMap[p.x + 1][p.y]) {
                    queue.add(new Point(p.x + 1, p.y));
                }
                if (p.x - 1 >= 0 &&
                        slopeMap[p.x - 1][p.y]) {
                    queue.add(new Point(p.x - 1, p.y));
                }
                if (p.y + 1 < GlobalDelegate.getSlopeMapHeight() &&
                        slopeMap[p.x][p.y + 1]) {
                    queue.add(new Point(p.x, p.y + 1));
                }
                if (p.y - 1 >= 0 &&
                        slopeMap[p.x][p.y - 1]) {
                    queue.add(new Point(p.x, p.y - 1));
                }
            }
        }
    }

    public IslandMap getIslandMap(CachedMoveData moveData) {
        return islandMaps.get(moveData);
    }
}
