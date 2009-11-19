/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import com.springrts.ai.oo.Unit;
import org.petah.spring.bai.BAI;
import org.petah.spring.bai.DefaultOptions;
import org.petah.spring.bai.group.Group;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultCommander extends Task {

    private int nextUpdate = 0;
    private State state = State.Building;

    private enum State {

        Building, Guarding
    }

    public DefaultCommander(BAI bai) {
        super(bai);
    }

    public boolean update(Group group, float tbf, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + DefaultOptions.builderUpdateTime.getValue();
            // Get the builders
            if (group.size() > 0) {
                Unit builder = group.getUnit(0);
                switch (state) {
                    case Guarding:
                        if (getNeededBasicBuildingNeeded(bai) != BasicBuilding.None) {
                            if ((!bai.getResourceManager().isEnergyOver(0.1f) ||
                                    !bai.getResourceManager().isMetalOver(0.1f)) &&
                                    bai.getGroupManager().getGroup("baseBuilders").size() > 0) {
                                CommandUtil.guard(bai, builder, bai.getGroupManager().getGroup("baseBuilders").getUnit(0));
                            } else {
                                CommandUtil.stop(bai, builder);
                                state = State.Building;
                            }
                        } else {
                            break;
                        }
                    case Building:
                        if (builder.getCurrentCommands().size() == 0) {
                            if (buildBasics(bai, builder)) {
                                DefaultCommands.guardFactory(bai, builder);
                                state = State.Guarding;
                            }
                        }
                        break;
                }
            }
        }
        return false;
    }

    private enum BasicBuilding {

        Mex, Energy, Factory, None
    }

    private static BasicBuilding getNeededBasicBuildingNeeded(BAI bai) {
        if (bai.getGroupManager().getGroup("metalExtractors").size() < 3) {
            return BasicBuilding.Mex;
        }
        if (!bai.getResourceManager().isEnergyOver(500, 45) &&
                bai.getGroupManager().getGroup("energyGenerators").size() < 7) {
            return BasicBuilding.Energy;
        }
        if (bai.getGroupManager().getGroup("factorys").size() < 1) {
            return BasicBuilding.Factory;
        }
        return BasicBuilding.None;
    }

    /**
     * 
     * @param bai
     * @param builder
     * @return true if the basic building are complete
     */
    private boolean buildBasics(BAI bai, Unit builder) {
        switch (getNeededBasicBuildingNeeded(bai)) {
            case Energy:
                DefaultCommands.queueEnergyGenerator(bai, builder);
                break;
            case Mex:
                DefaultCommands.queueClosestMetalExtractor(bai, builder);
                break;
            case Factory:
                DefaultCommands.queueFactory(bai, builder);
                break;
            case None:
                return true;
        }
        return false;
    }

}
