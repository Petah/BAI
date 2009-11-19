/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import com.springrts.ai.AIFloat3;
import java.util.ArrayList;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.util.BuilderUtil;
import org.petah.spring.bai.util.CommandUtil;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class DefaultScavenger extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultScavenger.updateTime", 250));
    private static Option<Integer> exploreDistance = OptionsManager.getOption(
            new Option<Integer>("DefaultScavenger.exploreDistance", 1000));
    // Class properties
    private float nextUpdate = 0;

    public DefaultScavenger(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            List<CachedUnit> dammagedUnits = getDammagedUnits();
            for (CachedUnit unit : group) {
                if (dammagedUnits.size() > 0) {
                    fightRandomUnit(unit, dammagedUnits);
                } else {
                    CommandUtil.reclaimArea(aiDelegate, unit, BuilderUtil.getMapTerrainCenter(),
                            MapUtil.mapToTerrain(Math.max(GlobalDelegate.getMapWidth(), GlobalDelegate.getMapHeight())), false);
//                    randomFight(group);
                }
            }
        }
        return false;
    }

    private ArrayList<CachedUnit> getDammagedUnits() {
        ArrayList<CachedUnit> dammagedUnits = new ArrayList<CachedUnit>();
        for (CachedUnit unit : aiDelegate.getGroupManager().getGroup("Assault")) {
            if (unit.getHealth() < unit.getDef().getHealth() * 0.5) {
                if (!unit.isBeingBuilt()) {
                    dammagedUnits.add(unit);
                }
            }
        }
        return dammagedUnits;
    }

    private void fightRandomUnit(CachedUnit unit, List<CachedUnit> units) {
        CachedUnit other = units.get((int) (Math.random() * units.size()));
        CommandUtil.fight(aiDelegate, unit, other.getPos(), false);
    }

    private void randomFight(CachedUnit unit) {
        CommandUtil.setMoveState(aiDelegate, unit, CommandUtil.MOVE_STATE_ROAM);
        AIFloat3 pos = unit.getPos();
        pos.x += Math.random() * exploreDistance.getValue() - exploreDistance.getValue() / 2;
        pos.z += Math.random() * exploreDistance.getValue() - exploreDistance.getValue() / 2;
        CommandUtil.fight(aiDelegate, unit, pos, false);
    }
}
