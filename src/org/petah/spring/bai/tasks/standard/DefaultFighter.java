/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import com.springrts.ai.AIFloat3;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.map.target.TargetZone;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultFighter extends Task {

   // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultFighter.updateTime", 150));
    private static Option<Integer> maxDistance = OptionsManager.getOption(
            new Option<Integer>("DefaultFighter.maxDistance", 5000));
    private static Option<Integer> patrolDistance = OptionsManager.getOption(
            new Option<Integer>("DefaultFighter.patrolDistance", 3000));
    // Class properties
    private float nextUpdate = 0;

    public DefaultFighter(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            List<TargetZone> zones = aiDelegate.getTeamDelegate().getTargetMap().getTargetZones();
            if (zones != null && zones.size() > 0) {
                for (final CachedUnit unit : group) {
                    TargetZone targetZone = zones.get((int) (zones.size() * Math.random()));
                    CachedUnit enemy = DefaultDefence.getClosestEnemyUnit(targetZone, aiDelegate.getBaseCenter(), maxDistance.getValue());
                    if (enemy != null) {
                        CommandUtil.attack(aiDelegate, unit, enemy, false);
                    } else {
                        patrolUnit(unit);
                    }
                }
            } else {
                for (final CachedUnit unit : group) {
                    patrolUnit(unit);
                }
            }
        }
        return false;
    }

    private void patrolUnit(CachedUnit unit) {
        float direction = (float) Math.random() * 360;
        AIFloat3 patrolTo = new AIFloat3();
        patrolTo.x = aiDelegate.getBaseCenter().x + GameMath.lengthDirX(patrolDistance.getValue(), direction);
        patrolTo.z = aiDelegate.getBaseCenter().z + GameMath.lengthDirY(patrolDistance.getValue(), direction);
        CommandUtil.patrol(aiDelegate, unit, patrolTo, false);
    }
}
