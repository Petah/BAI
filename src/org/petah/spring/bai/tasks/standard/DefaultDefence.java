/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import com.springrts.ai.oo.AIFloat3;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.map.target.TargetZone;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;
import org.petah.spring.bai.util.BuilderUtil;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultDefence extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultDefence.updateTime", 20));
    private static Option<Integer> maxDistance = OptionsManager.getOption(
            new Option<Integer>("DefaultDefence.maxDistance", 3000));
    private static Option<Integer> patrolDistance = OptionsManager.getOption(
            new Option<Integer>("DefaultDefence.patrolDistance", 2000));
    // Class properties
    private float nextUpdate = 0;

    public DefaultDefence(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            List<TargetZone> zones = aiDelegate.getTeamDelegate().getTargetMap().getTargetZones();
            if (zones != null && zones.size() > 0) {
                TargetZone targetZone = zones.get((int) (zones.size() * Math.random()));
                CachedUnit enemy = getClosestEnemyUnit(targetZone, aiDelegate.getBaseCenter(), maxDistance.getValue());
                if (enemy != null) {
                    for (CachedUnit unit : group) {
                        if (unit.getCurrentCommands().size() == 0) {
                            unit.attack(enemy.getUnit());
                        }
                    }
                } else {
                    surroundBase(group);
                }
            } else {
                surroundBase(group);
            }
        }
        return false;
    }

    public static CachedUnit getClosestEnemyUnit(TargetZone targetZone, AIFloat3 pos, int maxDistance) {
        CachedUnit enemy = null;
        float closestDistance = Float.MAX_VALUE;
        for (CachedUnit zoneUnit : targetZone.getUnits()) {
            AIFloat3 attackPos = zoneUnit.getPos();
            UnitInfo info = zoneUnit.getUnitInfo();
            float distance = GameMath.pointDistance(attackPos.x, attackPos.y, pos.x, pos.y);
            if ((enemy == null || distance < closestDistance) &&
                    distance < maxDistance &&
                    info != null && !info.isType(UnitType.Aircraft)) {
                closestDistance = distance;
                enemy = zoneUnit;
            }
        }
        return enemy;
    }

    private void surroundBase(UnitGroup group) {
        float direction = BuilderUtil.getMapCenterDirection(aiDelegate.getBaseCenter());
        for (CachedUnit unit : group) {
            if (unit.getCurrentCommands().size() == 0) {
                AIFloat3 moveTo = new AIFloat3();
                moveTo.x = aiDelegate.getBaseCenter().x + GameMath.lengthDirX(patrolDistance.getValue(), direction);
                moveTo.z = aiDelegate.getBaseCenter().z + GameMath.lengthDirY(patrolDistance.getValue(), direction);
                direction += 360 / group.size();
                unit.moveTo(moveTo);
            }
        }
    }

    private void patrolParameter(UnitGroup group) {
        for (CachedUnit unit : group) {
//        if (unit.getCurrentCommands().size() == 0) {
            float direction = (float) Math.random() * 360;
            AIFloat3 patrolTo = new AIFloat3();
            patrolTo.x = aiDelegate.getBaseCenter().x + GameMath.lengthDirX(patrolDistance.getValue(), direction);
            patrolTo.z = aiDelegate.getBaseCenter().z + GameMath.lengthDirY(patrolDistance.getValue(), direction);
            unit.patrolTo(patrolTo);
//        }
        }
    }
}


