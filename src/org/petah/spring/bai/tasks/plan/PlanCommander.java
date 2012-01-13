/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.plan;

import com.springrts.ai.oo.AIFloat3;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.GlobalOptions;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.tasks.standard.DefaultCommands;
import org.petah.spring.bai.unit.Faction;
import org.petah.spring.bai.util.ArrayUtil;
import org.petah.spring.bai.util.BuilderUtil;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class PlanCommander extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PlanCommander.updateTime", 20));
    // Class properties
    private int nextUpdate = 0;

    public PlanCommander(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public void drawLine(AIFloat3 from, AIFloat3 to) {
        aiDelegate.getCallback().getMap().getDrawer().addLine(from, to);
    }

    public boolean update(UnitGroup group, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            if (group.size() > 0) {
                // TODO: replace deprecated calls
                CachedUnit builder = group.getUnit(0);
                if (builder.getCurrentCommands().size() == 0) {
                    if (aiDelegate.getGroupManager().getGroup("MetalExtractors").size() < 3) {
                        DefaultCommands.queueClosestMetalExtractor(aiDelegate, builder);
                    } else if (aiDelegate.getGroupManager().getGroup("EnergyGenerators").size() < 6) {
                        CachedUnitDef building = BuilderUtil.getBestT1Energy(Faction.getFaction(builder.getDef()));
                        AIFloat3 pos = BuilderUtil.moveTowardsMapCenter(aiDelegate.getBaseCenter(), 400);
                        if (GlobalOptions.isDebug()) {
                            drawLine(aiDelegate.getBaseCenter(), pos);
                            drawLine(pos, BuilderUtil.moveAroundCenter(aiDelegate, pos, 90, 200));
                        }
                        pos = BuilderUtil.moveAroundCenter(aiDelegate, pos, 90, 200);
//                        pos = BuilderUtil.moveTowardsDirection(aiDelegate, pos, 270, 200);
                        PlanCommands.blockQueue(aiDelegate, builder, building, pos, 3, 2);
                    } else if (aiDelegate.getGroupManager().getGroup("Factorys").size() < 1) {
                        CachedUnitDef building = GlobalDelegate.getUnitDef("KbotFactory", Faction.getFaction(builder.getDef()));
                        AIFloat3 pos = BuilderUtil.moveTowardsMapCenter(aiDelegate.getBaseCenter(), 400);
                        if (GlobalOptions.isDebug()) {
                            drawLine(aiDelegate.getBaseCenter(), pos);
                            drawLine(pos, BuilderUtil.moveAroundCenter(aiDelegate, pos, -90, 200));
                        }
//                        pos = BuilderUtil.moveTowardsDirection(aiDelegate, pos, 90, 200);
                        pos = BuilderUtil.moveAroundCenter(aiDelegate, pos, -90, 200);
                        int xSize = 3;
                        int zSize = 3;
                        int facing = CommandUtil.getMapCenterFacing(aiDelegate, builder);
                        List<AIFloat3> block = PlanCommands.getBlock(aiDelegate, building, pos, xSize, zSize, facing);
                        if (block.size() == xSize * zSize) {
                            builder.build(building.getUnitDef(), block.get(ArrayUtil.get1DIndex(xSize / 2, zSize / 2, xSize)), facing, true);
                        }
                    }
                }
            }
        }
        return false;
    }
}
