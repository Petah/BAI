/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.spring.bai.tasks.*;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.oo.Unit;
import com.springrts.ai.oo.UnitDef;
import org.petah.spring.bai.BAI;
import org.petah.spring.bai.DefaultOptions;
import org.petah.spring.bai.ResourceManager;
import org.petah.spring.bai.group.Group;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitManager;
import org.petah.spring.bai.unit.UnitType;
import org.petah.spring.bai.util.BuilderUtil;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultBaseBuilder extends Task {

    private int nextUpdate = 0;

    public DefaultBaseBuilder(BAI bai) {
        super(bai);
    }

    public boolean update(Group group, float tbf, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + DefaultOptions.builderUpdateTime.getValue();
            for (Unit unit : group) {
                UnitInfo info = UnitManager.getUnitInfo(unit.getDef());
                if (info.isType(UnitType.Builder) && unit.getCurrentCommands().size() == 0) {
                    if (!bai.getResourceManager().isEnergyOver(0.5f, 150)) {
                        DefaultCommands.queueAdvSolar(bai, unit);
                    } else if (bai.getResourceManager().isMetalOver(250) &&
                            bai.getGroupManager().getGroup("nanos").size() < 12) {
                        DefaultCommands.queueNano(bai, unit);
                    } else {
//                        InitBuild.guardFactory(bai, unit);
                    }
                }
            }
        }
        return false;
    }
}
