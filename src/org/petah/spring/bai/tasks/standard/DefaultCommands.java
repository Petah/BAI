/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import com.springrts.ai.oo.AIFloat3;
import org.petah.spring.bai.Strategy;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.map.metal.MetalSpot;
import org.petah.spring.bai.unit.Faction;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;
import org.petah.spring.bai.util.BuilderUtil;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultCommands {

    public static final float FACTORY_RANDOM_FACTOR = 0.5f;
    public static final int DEFAULT_FACTORY_DISTANCE = 450;
    public static final int DEFAULT_SPACING = 10;
    public static final int DEFAULT_SEARCH_RADIUS = 500;
    public static final int DEFAULT_T2_FACTORY_SPACING = 8;
    public static final int DEFAULT_T2_FACTORY_SEARCH_RADIUS = 100;
    public static final int DEFAULT_METAL_EXTRACTOR_SPACING = 0;
//    public static final int DEFAULT_METAL_EXTRACTOR_SEARCH_RADIUS = 70;
    public static final int DEFAULT_ENERGY_DISTANCE = 10;
    public static final int DEFAULT_ENERGY_SPACING = 6;
    public static final int DEFAULT_ENERGY_SEARCH_RADIUS = 1000;

    // TODO: make nanos, scavengers use this, and make mexes redundant (from the closest to the base cetner)
    public static void findReclaimableBuildings(AIDelegate aiDelegate) {
        for (CachedUnit building : aiDelegate.getGroupManager().getGroup("Factorys")) {
            String name = building.getUnitInfo().getName();
            if (name.equals("KbotFactory") ||
                    name.equals("AircraftFactory") ||
                    name.equals("VehicleFactory") ||
                    name.equals("ShipFactory")) {
                aiDelegate.getAIOvermind().addReclaimableUnit(building);
            }
        }
        for (CachedUnit building : aiDelegate.getGroupManager().getGroup("EnergyGenerators")) {
            String name = building.getUnitInfo().getName();
            if (name.equals("Solar") || name.equals("Wind")) {
                aiDelegate.getAIOvermind().addReclaimableUnit(building);
            }
        }
    }

    public static void guardFactory(AIDelegate aiDelegate, CachedUnit builder) {
        if (aiDelegate.getGroupManager().getGroup("Factorys").size() > 0) {
            builder.guard(aiDelegate.getGroupManager().getGroup("Factorys").getUnit(0).getUnit());
        }
    }

    public static void queueAdvSolar(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef building = getBuildDef("ASolar", builder);
        builder.build(building.getUnitDef(), getEnergyBuildPos(aiDelegate, builder, building, DEFAULT_ENERGY_SEARCH_RADIUS, DEFAULT_ENERGY_SPACING));
    }

    public static void queueFusion(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef building = getBuildDef("Fusion", builder);
        builder.build(building.getUnitDef(), getEnergyBuildPos(aiDelegate, builder, building, DEFAULT_ENERGY_SEARCH_RADIUS, DEFAULT_ENERGY_SPACING));
    }

    public static void queueNano(AIDelegate aiDelegate, CachedUnit builder) {
        UnitGroup group = aiDelegate.getGroupManager().getGroup("Factorys");
        if (group.size() > 0) {
            CachedUnitDef nano = getBuildDef("Nano", builder);
            AIFloat3 pos = aiDelegate.findClosestBuildSite(nano, group.getUnit(0).getPos(), DEFAULT_SEARCH_RADIUS, DEFAULT_SPACING, 0);
            builder.build(nano.getUnitDef(), pos);
        }
    }

    private static CachedUnitDef getBuildDef(String name, CachedUnit builder) {
        return GlobalDelegate.getUnitDef(name, Faction.getFaction(builder.getDef()));
    }

    public static void queueFactory(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef factory;
        if (aiDelegate.getTeamDelegate().getTeamOvermind().getStrategyAmount(Strategy.Kbot) == 0) {
            factory = getBuildDef("KbotFactory", builder);
            aiDelegate.addStrategy(Strategy.Kbot);
        } else if (aiDelegate.getTeamDelegate().getTeamOvermind().getStrategyAmount(Strategy.Vehicle) == 0) {
            factory = getBuildDef("VehicleFactory", builder);
            aiDelegate.addStrategy(Strategy.Vehicle);
        } else if (aiDelegate.getTeamDelegate().getTeamOvermind().getStrategyAmount(Strategy.Aircraft) == 0) {
            factory = getBuildDef("AircraftFactory", builder);
            aiDelegate.addStrategy(Strategy.Aircraft);
        } else {
            if (Math.random() < FACTORY_RANDOM_FACTOR) {
                factory = getBuildDef("KbotFactory", builder);
                aiDelegate.addStrategy(Strategy.Kbot);
            } else {
                factory = getBuildDef("VehicleFactory", builder);
                aiDelegate.addStrategy(Strategy.Vehicle);
            }
        }
        AIFloat3 pos = BuilderUtil.moveTowardsMapCenter(aiDelegate.getBaseCenter(), DEFAULT_FACTORY_DISTANCE);
        int facing = CommandUtil.getMapCenterFacing(aiDelegate, builder);
//        System.err.println("facing: " + facing);
        pos = aiDelegate.findClosestBuildSite(factory, pos, DEFAULT_SEARCH_RADIUS, DEFAULT_SPACING, facing);
        builder.build(factory.getUnitDef(), pos, facing);
    }

    public static void queueT2Factory(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef factory = null;
        if (builder.getUnitInfo().isType(UnitType.Kbot)) {
            factory = getBuildDef("AKbotFactory", builder);
        } else if (builder.getUnitInfo().isType(UnitType.Vehicle)) {
            factory = getBuildDef("AVehicleFactory", builder);
        }
        if (factory != null) {
            AIFloat3 pos = BuilderUtil.moveTowardsMapCenter(aiDelegate.getBaseCenter(), DEFAULT_FACTORY_DISTANCE);
            int facing = CommandUtil.getMapCenterFacing(aiDelegate, builder);
            pos = aiDelegate.findClosestBuildSite(factory, pos, DEFAULT_T2_FACTORY_SEARCH_RADIUS, DEFAULT_T2_FACTORY_SPACING, facing);
            if (aiDelegate.isPossibleToBuildAt(factory, pos, facing)) {
                builder.build(factory.getUnitDef(), pos, facing);
            }
        }
    }

    public static void queueEnergyGenerator(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef energyGen = BuilderUtil.getBestT1Energy(Faction.getFaction(builder.getDef()));
//        AIFloat3 pos = getEnergyBuildPos(aiDelegate, builder, energyGen, DEFAULT_ENERGY_SEARCH_RADIUS, DEFAULT_ENERGY_SPACING);
        AIFloat3 pos = aiDelegate.findClosestBuildSite(energyGen, builder.getPos(), DEFAULT_ENERGY_SEARCH_RADIUS, DEFAULT_ENERGY_SPACING, 0);
        builder.build(energyGen.getUnitDef(), pos);
    }

    public static boolean queueClosestMetalExtractor(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef mexDef;
        // TODO: improve metal extractor selection
        if (builder.getUnitInfo().isType(UnitType.Level2)) {
            mexDef = getBuildDef("AMetalExtractor", builder);
        } else {
            mexDef = getBuildDef("MetalExtractor", builder);
        }
        return queueClosestMetalExtractor(aiDelegate, builder, mexDef, builder.getPos());
    }

    public static boolean queueClosestMetalExtractor(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef mexDef, AIFloat3 pos) {
        for (MetalSpot metalSpot : aiDelegate.getMetalSpotManager().getMetalSpotDistance(pos, aiDelegate.getCachedUnitManager()).values()) {
            AIFloat3 metalSpotPos = aiDelegate.findClosestBuildSite(mexDef, new AIFloat3(metalSpot.getTerrainX(), 1, metalSpot.getTerrainZ()),
                    GlobalDelegate.getMetalExtractorTerrainRadius(), DEFAULT_METAL_EXTRACTOR_SPACING, 0);
            builder.build(mexDef.getUnitDef(), metalSpotPos);
            return true;
        }
        return false;
    }
//    public static boolean queueClosestMetalExtractor(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef mexDef, AIFloat3 pos) {
//        MetalSpot metalSpot = aiDelegate.getTeamDelegate().getMetalMap().findFreeMetalSpot(pos);
//        if (metalSpot != null) {
//            AIFloat3 metalSpotPos = aiDelegate.findClosestBuildSite(mexDef, new AIFloat3(metalSpot.getTerrainX(), 1, metalSpot.getTerrainZ()),
//                    CachedMetalMap.getMetalExtractorRadius(), DEFAULT_METAL_EXTRACTOR_SPACING, 0);
//            builder.queueUnit(mexDef, metalSpotPos);
//            return true;
//        } else {
//            aiDelegate.sendMessage("No avalible metal spots");
//            return false;
//        }
//    }

    /**
     * TODO: get best metal extractor base on the y pos of the metalSpot for under water etc
     * @param aiDelegate
     * @param builder
     * @return
     */
    public static CachedUnitDef getBestMetalExtractor(AIDelegate aiDelegate, MetalSpot metalSpot, CachedUnit builder) {
        UnitInfo info = builder.getUnitInfo();
        if (info.isType(UnitType.Level2)) {
            if (info.isType(UnitType.Submarine) ||
                    info.isType(UnitType.SeaPlane)) {
                return getBuildDef("AWMetalExtractor", builder);
            }
            if (aiDelegate.getResourceManager().isEnergyOver(0.5f, 150) &&
                    aiDelegate.getResourceManager().isMetalOver(0.5f, 10) &&
                    Faction.getFaction(builder.getDef()) == Faction.Core) {
                return getBuildDef("ATMetalExtractor", builder);
            }
            return getBuildDef("AMetalExtractor", builder);
        } else {
            if (info.isType(UnitType.Ship) ||
                    info.isType(UnitType.Hovercraft)) {
                return getBuildDef("WMetalExtractor", builder);
            }
            if (aiDelegate.getResourceManager().isEnergyOver(0.5f, 150) &&
                    aiDelegate.getResourceManager().isMetalOver(0.5f, 10)) {
                if (Faction.getFaction(builder.getDef()) == Faction.Arm) {
                    return getBuildDef("CMetalExtractor", builder);
                } else {
                    return getBuildDef("TMetalExtractor", builder);
                }
            }
            return getBuildDef("MetalExtractor", builder);
        }
    }

//    @Deprecated
//    public static boolean queueClosestMetalExtractor(AIDelegate aiDelegate, CachedUnit builder) {
//        CachedUnitDef mex;
//        UnitInfo info = builder.getUnitInfo();
//        if (info.isType(UnitType.Level2)) {
//            mex = getBuildDef("AMetalExtractor", builder);
//        } else {
//            mex = getBuildDef("MetalExtractor", builder);
//        }
//        return queueClosestMetalExtractor(aiDelegate, builder, mex, builder.getPos());
//    }
    public static AIFloat3 getEnergyBuildPos(AIDelegate aiDelegate, CachedUnit builder, CachedUnitDef building, int searchRadius, int spacing) {
        AIFloat3 pos = BuilderUtil.moveAwayFromMapCenter(aiDelegate.getBaseCenter(), DEFAULT_ENERGY_DISTANCE);
        if (!aiDelegate.isPossibleToBuildAt(building, pos, 0)) {
            pos = aiDelegate.findClosestBuildSite(building, pos, searchRadius, spacing, 0);
        }
        return pos;
    }

    public static void queueRadar(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef building = getBuildDef("Radar", builder);
        AIFloat3 pos = aiDelegate.findClosestBuildSite(building, builder.getPos(), DEFAULT_SEARCH_RADIUS, DEFAULT_SPACING, 0);
        builder.build(building.getUnitDef(), pos);
    }
}
