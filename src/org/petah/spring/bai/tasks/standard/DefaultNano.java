/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.spring.bai.tasks.*;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.oo.Unit;
import org.petah.spring.bai.BAI;
import org.petah.spring.bai.DefaultOptions;
import org.petah.spring.bai.ResourceManager;
import org.petah.spring.bai.group.Group;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitManager;
import org.petah.spring.bai.unit.UnitType;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultNano extends Task {

    private int nextUpdate = 0;

    public DefaultNano(BAI bai) {
        super(bai);
    }

    public boolean update(Group group, float tbf, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + DefaultOptions.nanoUpdateTime.getValue();
            // Get the builders
            for (Unit unit : group) {
                UnitInfo info = UnitManager.getUnitInfo(unit.getDef());
                if (info.isType(UnitType.Nano)) {
                    if (bai.getResourceManager().isEnergyOver(500)) {
                        CommandUtil.setMoveState(bai, unit, CommandUtil.MOVE_STATE_ROAM);
                        AIFloat3 pos = unit.getPos();
                        pos.x += Math.random() * 500 - 250;
                        pos.z += Math.random() * 500 - 250;
                        CommandUtil.patrol(bai, unit, pos);
                    } else {
                        CommandUtil.stop(bai, unit);
                    }
                }
            }
        }
        return false;
    }
}
