/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.plan;

import com.springrts.ai.oo.AIFloat3;
import java.util.LinkedList;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.GlobalOptions;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.util.CommandUtil;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class PlanCommands {

    // Options
    private static Option<Integer> blockMaxAttempts = OptionsManager.getOption(
            new Option<Integer>("PlanCommands.blockMaxAttempts", 20));
    private static Option<Integer> blockJiggleSize = OptionsManager.getOption(
            new Option<Integer>("PlanCommands.blockMaxAttempts", 300));

    public static void markBuilding(AIDelegate aiDelegate, CachedUnitDef def, AIFloat3 pos) {
        aiDelegate.getCallback().getMap().getDrawer().addPoint(pos, def.getHumanName());
    }

    public static void outlineBuilding(AIDelegate aiDelegate, CachedUnitDef def, AIFloat3 pos) {
        AIFloat3 from, to;
        int width = MapUtil.mapToTerrain(def.getXSize());
        int height = MapUtil.mapToTerrain(def.getZSize());
        from = new AIFloat3(pos.x - width / 2, 0, pos.z - height / 2);
        to = new AIFloat3(pos.x + width / 2, 0, pos.z - height / 2);
        aiDelegate.getCallback().getMap().getDrawer().addLine(from, to);
        from = new AIFloat3(pos.x + width / 2, 0, pos.z - height / 2);
        to = new AIFloat3(pos.x + width / 2, 0, pos.z + height / 2);
        aiDelegate.getCallback().getMap().getDrawer().addLine(from, to);
        from = new AIFloat3(pos.x + width / 2, 0, pos.z + height / 2);
        to = new AIFloat3(pos.x - width / 2, 0, pos.z + height / 2);
        aiDelegate.getCallback().getMap().getDrawer().addLine(from, to);
        from = new AIFloat3(pos.x - width / 2, 0, pos.z + height / 2);
        to = new AIFloat3(pos.x - width / 2, 0, pos.z - height / 2);
        aiDelegate.getCallback().getMap().getDrawer().addLine(from, to);
    }

    public static List<AIFloat3> getBlock(AIDelegate aiDelegate, CachedUnitDef building, AIFloat3 pos, int xSize, int zSize) {
        return getBlock(aiDelegate, building, pos, xSize, zSize, 0);
    }

    public static List<AIFloat3> getBlock(AIDelegate aiDelegate, CachedUnitDef building, AIFloat3 pos, int xSize, int zSize, int facing) {
        List<AIFloat3> list = new LinkedList<AIFloat3>();
        AIFloat3 currentPos = new AIFloat3(pos);
        int count = 0;
        int breakWhile = blockMaxAttempts.getValue();
        while (count < xSize * zSize && breakWhile > 0) {
            boolean breakFor = false;
            for (int z = 0; z < zSize && !breakFor; z++) {
                for (int x = 0; x < xSize && !breakFor; x++) {
                    if (!aiDelegate.isPossibleToBuildAt(building, currentPos, facing)) {
                        currentPos = new AIFloat3((float) (pos.x + Math.random() * blockJiggleSize.getValue()),
                                0, (float) (pos.z + Math.random() * blockJiggleSize.getValue()));
                        list.clear();
                        count = 0;
                        breakWhile--;
                        breakFor = true;
                        break;
                    }
                    count++;
                    list.add(new AIFloat3(currentPos));
                    if (GlobalOptions.isDebug()) {
                        outlineBuilding(aiDelegate, building, currentPos);
                    }
                    if (count >= xSize * zSize) {
                        breakFor = true;
                        break;
                    }
                    currentPos.x += MapUtil.mapToTerrain(building.getXSize());
                }
                currentPos.x -= MapUtil.mapToTerrain(building.getXSize()) * xSize;
                currentPos.z += MapUtil.mapToTerrain(building.getZSize());
            }
        }
        return list;
    }

    public static boolean blockQueue(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef building, AIFloat3 pos, int xSize, int zSize) {
        return blockQueue(aiDelegate, builder, building, pos, xSize, zSize, 0);
    }

    public static boolean blockQueue(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef building, AIFloat3 pos, int xSize, int zSize, int facing) {
        List<AIFloat3> list = getBlock(aiDelegate, building, pos, xSize, zSize, facing);
        if (list.size() > 0) {
            for (AIFloat3 buildPos : list) {
                if (GlobalOptions.isDebug()) {
                    markBuilding(aiDelegate, building, buildPos);
                }
                builder.build(building.getUnitDef(), buildPos, facing, true);
            }
            return true;
        }
        return false;
    }
}
