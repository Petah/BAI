/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.spring.bai.delegate.AIDelegate;
import com.springrts.ai.oo.AIFloat3;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.map.control.ControlZone;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultRaider extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultRaider.updateTime", 150));
    // Class properties
    private float nextUpdate = 0;

    public DefaultRaider(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            List<ControlZone> zones = aiDelegate.getTeamDelegate().getControlMap().getEnemyControlZones();
            for (CachedUnit unit : group) {
                if (zones.size() > 0) {
                    ControlZone controlZone = zones.get((int) (zones.size() * Math.random()));
                    unit.fight(new AIFloat3(controlZone.getTerrainCenterX(), 0, controlZone.getTerrainCenterZ()));
                }
            }
        }
        return false;
    }
}
