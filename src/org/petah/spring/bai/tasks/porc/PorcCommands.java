/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.porc;

import com.springrts.ai.AIFloat3;
import org.petah.spring.bai.ResourceManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.cache.CachedUnitDef;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.tasks.standard.DefaultCommands;
import org.petah.spring.bai.unit.Faction;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.util.BuilderUtil;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class PorcCommands {

    public static final int DEFAULT_FACTORY_DISTANCE = 450;
    public static final int DEFAULT_STORAGE_DISTANCE = 350;
    public static final int DEFAULT_METAL_MAKER_DISTANCE = 800;
    public static final int DEFAULT_SPACING = 10;
    public static final int DEFAULT_SEARCH_RADIUS = 500;
    public static final int DEFAULT_NANO_SPACING = 6;
    public static final int DEFAULT_ENERGY_SPACING = 6;
    public static final int DEFAULT_ENERGY_SEARCH_RADIUS = 1000;

    public static void guardT2ExpansionBuilder(AIDelegate aiDelegate, CachedUnit unit) {
        if (aiDelegate.getGroupManager().getGroup("t2ExpansionBuilders").size() > 0) {
            CommandUtil.guard(aiDelegate, unit, aiDelegate.getGroupManager().getGroup("t2ExpansionBuilders").getUnit(0), false);
        }
    }

    public static void guardBaseBuilder(AIDelegate aiDelegate, CachedUnit builder) {
        if (aiDelegate.getGroupManager().getGroup("baseBuilders").size() > 0) {
            CommandUtil.guard(aiDelegate, builder, aiDelegate.getGroupManager().getGroup("baseBuilders").getUnit(0), false);
        }
    }

    public static void queueSolarGenerator(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef energyGen = GlobalDelegate.getUnitDef("Solar", Faction.getFaction(builder.getDef()));
        AIFloat3 pos = DefaultCommands.getEnergyBuildPos(aiDelegate, builder, energyGen, DEFAULT_ENERGY_SEARCH_RADIUS, DEFAULT_ENERGY_SPACING);
        CommandUtil.queueUnit(aiDelegate, builder, energyGen, pos);
    }

    public static void queueFactory(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef factory = GlobalDelegate.getUnitDef("AircraftFactory", Faction.getFaction(builder.getDef()));
        AIFloat3 pos = BuilderUtil.moveTowardsMapCenter(aiDelegate.getBaseCenter(), DEFAULT_FACTORY_DISTANCE);
        pos = aiDelegate.findClosestBuildSite(factory, pos, DEFAULT_SEARCH_RADIUS, DEFAULT_SPACING, 0);
        CommandUtil.queueUnit(aiDelegate, builder, factory, pos, CommandUtil.getMapCenterFacing(aiDelegate, builder));
    }

    public static void queueMetalMaker(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef building = GlobalDelegate.getUnitDef("MetalMaker", Faction.getFaction(builder.getDef()));
        AIFloat3 pos = BuilderUtil.moveTowardsMapCenter(aiDelegate.getBaseCenter(), DEFAULT_METAL_MAKER_DISTANCE);
        pos = aiDelegate.findClosestBuildSite(building, pos, DEFAULT_SEARCH_RADIUS, DEFAULT_SPACING, 0);
        CommandUtil.queueUnit(aiDelegate, builder, building, pos, CommandUtil.getMapCenterFacing(aiDelegate, builder));
    }

    public static void queueMetalStorage(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef building = GlobalDelegate.getUnitDef("MetalStorage", Faction.getFaction(builder.getDef()));
        AIFloat3 pos = BuilderUtil.moveAwayFromMapCenter(aiDelegate.getBaseCenter(), DEFAULT_STORAGE_DISTANCE);
        pos = aiDelegate.findClosestBuildSite(building, pos, DEFAULT_SEARCH_RADIUS, DEFAULT_SPACING, 0);
        CommandUtil.queueUnit(aiDelegate, builder, building, pos, CommandUtil.getMapCenterFacing(aiDelegate, builder));
    }

    public static void queueEnergyStorage(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef building = GlobalDelegate.getUnitDef("EnergyStorage", Faction.getFaction(builder.getDef()));
        AIFloat3 pos = BuilderUtil.moveAwayFromMapCenter(aiDelegate.getBaseCenter(), DEFAULT_STORAGE_DISTANCE);
        pos = aiDelegate.findClosestBuildSite(building, pos, DEFAULT_SEARCH_RADIUS, DEFAULT_SPACING, 0);
        CommandUtil.queueUnit(aiDelegate, builder, building, pos, CommandUtil.getMapCenterFacing(aiDelegate, builder));
    }

    public static void queueAdvSolar(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef advSolar = GlobalDelegate.getUnitDef("ASolar", Faction.getFaction(builder.getDef()));
        CommandUtil.queueUnit(aiDelegate, builder, advSolar, DefaultCommands.getEnergyBuildPos(aiDelegate, builder, advSolar, DEFAULT_ENERGY_SEARCH_RADIUS, DEFAULT_ENERGY_SPACING));
    }

    public static void queueT2Factory(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef factory = null;
        UnitInfo builderInfo = UnitInfo.getUnitInfo(builder.getDef());
        if (builderInfo.getName().equals("KT1Builder")) {
            factory = GlobalDelegate.getUnitDef("T2KbotFactory", Faction.getFaction(builder.getDef()));
        } else if (builderInfo.getName().equals("VT1Builder")) {
            factory = GlobalDelegate.getUnitDef("T2VehicleFactory", Faction.getFaction(builder.getDef()));
        } else if (builderInfo.getName().equals("AT1Builder")) {
            factory = GlobalDelegate.getUnitDef("T2AircraftFactory", Faction.getFaction(builder.getDef()));
        }
        if (factory != null) {
            AIFloat3 pos = BuilderUtil.moveTowardsMapCenter(aiDelegate.getBaseCenter(), DEFAULT_FACTORY_DISTANCE);
            pos = aiDelegate.findClosestBuildSite(factory, pos, DEFAULT_SEARCH_RADIUS, DEFAULT_SPACING, 0);
            CommandUtil.queueUnit(aiDelegate, builder, factory, pos, CommandUtil.getMapCenterFacing(aiDelegate, builder));
        }
    }

    public static void queueNano(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef nano = GlobalDelegate.getUnitDef("Nano", Faction.getFaction(builder.getDef()));
        AIFloat3 pos = BuilderUtil.moveTowardsMapCenter(aiDelegate.getBaseCenter(), 250);
        pos = aiDelegate.findClosestBuildSite(nano, pos, DEFAULT_SEARCH_RADIUS, DEFAULT_NANO_SPACING, 0);
        CommandUtil.queueUnit(aiDelegate, builder, nano, pos);
    }

    public static void queueFusion(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef fusion = GlobalDelegate.getUnitDef("Fusion", Faction.getFaction(builder.getDef()));
        CommandUtil.queueUnit(aiDelegate, builder, fusion, DefaultCommands.getEnergyBuildPos(aiDelegate, builder, fusion, DEFAULT_ENERGY_SEARCH_RADIUS, DEFAULT_ENERGY_SPACING));
    }

    public static void queueEnergyGenerator(AIDelegate aiDelegate, CachedUnit builder) {
        CachedUnitDef energyGen;
        // If wind is low, only build solar
        if (ResourceManager.getAverageWind() < 9) {
            energyGen = GlobalDelegate.getUnitDef("Solar", Faction.getFaction(builder.getDef()));
        } // If wind is high, only build wind
        else if (ResourceManager.getAverageWind() > 14) {
            energyGen = GlobalDelegate.getUnitDef("Wind", Faction.getFaction(builder.getDef()));
        } // Otherwise randomly build both
        else if (Math.random() < 0.5) {
            energyGen = GlobalDelegate.getUnitDef("Wind", Faction.getFaction(builder.getDef()));
        } else {
            energyGen = GlobalDelegate.getUnitDef("Solar", Faction.getFaction(builder.getDef()));
        }
        AIFloat3 pos = DefaultCommands.getEnergyBuildPos(aiDelegate, builder, energyGen, DEFAULT_ENERGY_SEARCH_RADIUS, DEFAULT_ENERGY_SPACING);
        CommandUtil.queueUnit(aiDelegate, builder, energyGen, pos);
    }
}
