/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import java.util.List;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.map.target.TargetType;
import org.petah.spring.bai.map.target.TargetZone;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultBomber extends Task {

   // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultBomber.updateTime", 300));
    // Class properties
    private float nextUpdate = 0;

    public DefaultBomber(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group,  int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            List<TargetZone> zones = aiDelegate.getTeamDelegate().getTargetMap().getTargetZones(TargetType.Factory);
            if (zones == null) {
                zones = aiDelegate.getTeamDelegate().getTargetMap().getTargetZones(TargetType.Commander);
            }
            if (zones == null) {
                zones = aiDelegate.getTeamDelegate().getTargetMap().getTargetZones(TargetType.Energy);
            }
            if (zones == null) {
                zones = aiDelegate.getTeamDelegate().getTargetMap().getTargetZones();
            }
            if (zones != null) {
                for (final CachedUnit unit : group) {
//                if (unit.getCurrentCommands().size() == 0) {
                    if (zones.size() > 0) {
                        TargetZone targetZone = zones.get((int) (zones.size() * Math.random()));
                        for (CachedUnit zoneUnit : targetZone.getUnits()) {
                                CommandUtil.attack(aiDelegate, unit, zoneUnit, true);
                        }
                    }
//                }
                }
            }
        }
        return false;
    }
}
