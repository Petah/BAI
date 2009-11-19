/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.tasks.porc.PorcCommands;
import org.petah.spring.bai.unit.Faction;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultCommanderStage1 extends Task {

    // Options
    protected static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultCommander.updateTime", 20));
    // Class properties
    protected int nextUpdate = 0;
    protected State state = State.Building;

    protected enum State {

        Building, Guarding
    }

    public DefaultCommanderStage1(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            // Get the builders
            if (group.size() > 0) {
                // TODO: replace deprecated calls
                CachedUnit builder = group.getUnit(0);
                switch (state) {
                    case Guarding:
                        if (getNeededBasicBuildingNeeded(aiDelegate) != BasicBuilding.None) {
                            if ((!aiDelegate.getResourceManager().isEnergyOver(0.1f) ||
                                    !aiDelegate.getResourceManager().isMetalOver(0.1f)) &&
                                    aiDelegate.getGroupManager().getGroup("BaseBuilders").size() > 0) {
                                CommandUtil.guard(aiDelegate, builder,
                                        aiDelegate.getGroupManager().getGroup("BaseBuilders").getUnit(0), false);
                            } else {
                                CommandUtil.stop(aiDelegate, builder);
                                state = State.Building;
                            }
                        } else {
                            break;
                        }
                    case Building:
                        if (builder.getCurrentCommands().size() == 0) {
                            if (buildBasics(aiDelegate, builder)) {
                                DefaultCommands.guardFactory(aiDelegate, builder);
                                state = State.Guarding;
                            }
                        }
                        break;
                }
            }
        }
        return false;
    }

    protected enum BasicBuilding {

        Mex, Energy, Factory, None, EnergyStorage, MetalStorage, Radar, MetalMaker
    }
    // TODO: Convert to options
    // Energy storage
    public static final float BUILD_ESTOR_MIN_E = 0.9f;
    public static final int BUILD_ESTOR_MIN_EINCOME = 120;
    public static final float BUILD_ESTOR_MIN_M = 0.1f;
    public static final int BUILD_ESTOR_MIN_MINCOME = 6;
    public static final int BUILD_MAX_ESTOR = 1;
    // Metal storage
    public static final float BUILD_MSTOR_MIN_E = 0.05f;
    public static final int BUILD_MSTOR_MIN_EINCOME = 120;
    public static final float BUILD_MSTOR_MIN_M = 0.9f;
    public static final int BUILD_MSTOR_MIN_MINCOME = 6;
    public static final int BUILD_MAX_MSTOR = 3;
    // Energy
    public static final int BUILD_ENERGY_IF_E_UNDER = 500;
    public static final int BUILD_ENERGY_IF_EINCOME_UNDER = 45;
    public static final int BUILD_MAX_ENERGY_AMOUNT = 7;
    // Metal
    public static final int BUILD_MAX_METAL_AMOUNT = 3;
    // Other
    public static final int BUILD_MAX_FACTORYS = 1;
    public static final int BUILD_MAX_RADAR = 1;
    // Metal maker
    public static final float BUILD_MM_MIN_E = 0.9f;
    public static final int BUILD_MM_MIN_EINCOME = 120;
    public static final float BUILD_MM_MAX_M = 0.9f;

    protected BasicBuilding getNeededBasicBuildingNeeded(AIDelegate aiDelegate) {
        int mexAmount = aiDelegate.getGroupManager().getGroup("MetalExtractors").size();
        int energyAmount = aiDelegate.getGroupManager().getGroup("EnergyGenerators").size();
        if (mexAmount < BUILD_MAX_METAL_AMOUNT && mexAmount <= energyAmount) {
            return BasicBuilding.Mex;
        }
        if (!aiDelegate.getResourceManager().isEnergyOver(BUILD_ENERGY_IF_E_UNDER, BUILD_ENERGY_IF_EINCOME_UNDER) &&
                energyAmount < BUILD_MAX_ENERGY_AMOUNT) {
            return BasicBuilding.Energy;
        }

        if (aiDelegate.getGroupManager().getGroup("Factorys").size() < BUILD_MAX_FACTORYS) {
            return BasicBuilding.Factory;
        }

        if (aiDelegate.getGroupManager().getGroup("Radar").size() < BUILD_MAX_RADAR) {
            return BasicBuilding.Radar;
        }

        if (aiDelegate.getResourceManager().isEnergyOver(BUILD_ESTOR_MIN_E, BUILD_ESTOR_MIN_EINCOME) &&
                aiDelegate.getResourceManager().isMetalOver(BUILD_ESTOR_MIN_M, BUILD_ESTOR_MIN_MINCOME) &&
                aiDelegate.getGroupManager().getGroup("EnergyStorage").size() < BUILD_MAX_ESTOR) {
            return BasicBuilding.EnergyStorage;
        }
        if (aiDelegate.getResourceManager().isMetalOver(BUILD_MSTOR_MIN_M, BUILD_MSTOR_MIN_MINCOME) &&
                aiDelegate.getResourceManager().isEnergyOver(BUILD_MSTOR_MIN_E, BUILD_MSTOR_MIN_EINCOME) &&
                aiDelegate.getGroupManager().getGroup("MetalStorage").size() < BUILD_MAX_MSTOR) {
            return BasicBuilding.MetalStorage;
        }

        if (aiDelegate.getResourceManager().isEnergyOver(BUILD_MM_MIN_E, BUILD_MM_MIN_EINCOME) &&
                !aiDelegate.getResourceManager().isMetalOver(BUILD_MM_MAX_M)) {
            return BasicBuilding.MetalMaker;
        }
        return BasicBuilding.None;
    }

    /**
     * 
     * @param aiDelegate
     * @param builder
     * @return true if the basic building are complete
     */
    protected boolean buildBasics(AIDelegate aiDelegate, CachedUnit builder) {
        switch (getNeededBasicBuildingNeeded(aiDelegate)) {
            case Energy:
                DefaultCommands.queueEnergyGenerator(aiDelegate, builder);
                break;
            case Mex:
                DefaultCommands.queueClosestMetalExtractor(aiDelegate, builder,
                        GlobalDelegate.getUnitDef("MetalExtractor", Faction.getFaction(builder.getDef())),
                        aiDelegate.getBaseCenter());
                break;
            case Factory:
                DefaultCommands.queueFactory(aiDelegate, builder);
                break;
            case EnergyStorage:
                PorcCommands.queueEnergyStorage(aiDelegate, builder);
                break;
            case MetalStorage:
                PorcCommands.queueMetalStorage(aiDelegate, builder);
                break;
            case Radar:
                DefaultCommands.queueRadar(aiDelegate, builder);
                break;
            case MetalMaker:
                PorcCommands.queueMetalMaker(aiDelegate, builder);
                break;
            case None:
                return true;
        }
        return false;
    }
}
