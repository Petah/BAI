///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.petah.spring.bai.group;
//
//import org.petah.spring.bai.cache.CachedUnit;
//import org.petah.spring.bai.delegate.AIDelegate;
//import org.petah.spring.bai.tasks.standard.DefaultBomber;
//import org.petah.spring.bai.tasks.standard.DefaultExpansionBuilder;
//import org.petah.spring.bai.tasks.standard.DefaultMetalMaker;
//import org.petah.spring.bai.tasks.standard.DefaultNanoStage1;
//import org.petah.spring.bai.tasks.porc.PorcT1BaseBuilder;
//import org.petah.spring.bai.tasks.porc.PorcCommanderStage1;
//import org.petah.spring.bai.tasks.porc.PorcFactory;
//import org.petah.spring.bai.tasks.porc.PorcT2BaseBuilder;
//import org.petah.spring.bai.tasks.porc.PorcT2ExpansionBuilder;
//import org.petah.spring.bai.tasks.standard.DefaultFighter;
//import org.petah.spring.bai.tasks.standard.DefaultRaider;
//import org.petah.spring.bai.tasks.standard.DefaultScout;
//import org.petah.spring.bai.unit.Faction;
//import org.petah.spring.bai.unit.UnitInfo;
//import org.petah.spring.bai.unit.UnitType;
//
///**
// *
// * @author Petah
// */
//public class PorcGroupManager extends GroupManager {
//
//    public PorcGroupManager(AIDelegate aiDelegate) {
//        super(aiDelegate);
//    }
//
//    @Override
//    public void initGroups() {
//        addGroup("Commanders", new UnitGroup(aiDelegate));
//        getGroup("Commanders").addTask(new PorcCommanderStage1(aiDelegate));
//
//        addGroup("MetalExtractors", new UnitGroup(aiDelegate));
//        addGroup("MetalStorage", new UnitGroup(aiDelegate));
//        addGroup("MetalMakers", new UnitGroup(aiDelegate));
//        getGroup("MetalMakers").addTask(new DefaultMetalMaker(aiDelegate));
//
//        addGroup("EnergyGenerators", new UnitGroup(aiDelegate));
//        addGroup("EnergyStorage", new UnitGroup(aiDelegate));
//
//        addGroup("Nanos", new UnitGroup(aiDelegate));
//        getGroup("Nanos").addTask(new DefaultNanoStage1(aiDelegate));
//
//        addGroup("T2Factorys", new UnitGroup(aiDelegate));
//        getGroup("T2Factorys").addTask(new PorcFactory(aiDelegate));
//
//        addGroup("Factorys", new UnitGroup(aiDelegate));
//        getGroup("Factorys").addTask(new PorcFactory(aiDelegate));
//
//        addGroup("Scouts", new UnitGroup(aiDelegate));
//        getGroup("Scouts").addTask(new DefaultScout(aiDelegate));
//
//        addGroup("Raiders", new UnitGroup(aiDelegate));
//        getGroup("Raiders").addTask(new DefaultRaider(aiDelegate));
//
//        addGroup("Assault", new UnitGroup(aiDelegate));
//        getGroup("Assault").addTask(new DefaultRaider(aiDelegate));
//
//        addGroup("Bombers", new UnitGroup(aiDelegate));
//        getGroup("Bombers").addTask(new DefaultBomber(aiDelegate));
//
//        addGroup("Fighters", new UnitGroup(aiDelegate));
//        getGroup("Fighters").addTask(new DefaultFighter(aiDelegate));
//
//        addGroup("T2Builders", new UnitGroup(aiDelegate));
//
//        addGroup("T2ExpansionBuilders", new UnitGroup(aiDelegate));
//        getGroup("T2ExpansionBuilders").addTask(new PorcT2ExpansionBuilder(aiDelegate));
//
//        addGroup("T2BaseBuilders", new UnitGroup(aiDelegate));
//        getGroup("T2BaseBuilders").addTask(new PorcT2BaseBuilder(aiDelegate));
//
//        addGroup("Builders", new UnitGroup(aiDelegate));
//
//        addGroup("ExpansionBuilders", new UnitGroup(aiDelegate));
//        getGroup("ExpansionBuilders").addTask(new DefaultExpansionBuilder(aiDelegate));
//
//        addGroup("BaseBuilders", new UnitGroup(aiDelegate));
//        getGroup("BaseBuilders").addTask(new PorcT1BaseBuilder(aiDelegate));
//
//        addGroup("Other", new UnitGroup(aiDelegate));
//    }
//
//    @Override
//    public void groupUnit(CachedUnit unit) {
//        UnitInfo unitInfo = unit.getUnitInfo();
//        if (!unitInfo.isType(UnitType.Level2)) {
//            // Units
//            if (unitInfo.isType(UnitType.Scout)) {
//                getGroup("Scouts").addUnit(unit);
//            } else if (unitInfo.isType(UnitType.Raider)) {
//                if (unitInfo.isType(UnitType.Kbot) &&
//                        Faction.getFaction(unit.getDef()) == Faction.Core) {
//                    if (getGroup("Scouts").size() <= getGroup("Raiders").size() &&
//                            !getGroup("Raiders").contains(unit)) {
//                        getGroup("Scouts").addUnit(unit);
//                    } else if (!getGroup("Scouts").contains(unit)) {
//                        getGroup("Raiders").addUnit(unit);
//                    }
//                } else {
//                    getGroup("Raiders").addUnit(unit);
//                }
//            } else if (unitInfo.isType(UnitType.Bomber)) {
//                getGroup("Bombers").addUnit(unit);
//            } else if (unitInfo.isType(UnitType.Fighter)) {
//                getGroup("Fighters").addUnit(unit);
//            } else if (unitInfo.isType(UnitType.Assault) ||
//                    unitInfo.isType(UnitType.Ranger)) {
//                getGroup("Assault").addUnit(unit);
//            } else if (unitInfo.isType(UnitType.Builder)) {
//                getGroup("Builders").addUnit(unit);
//                if (getGroup("BaseBuilders").size() >= 1 &&
//                        !getGroup("BaseBuilders").contains(unit)) {
//                    getGroup("ExpansionBuilders").addUnit(unit);
//                } else if (!getGroup("ExpansionBuilders").contains(unit)) {
//                    getGroup("BaseBuilders").addUnit(unit);
//                }
//            } // Metal
//            else if (unitInfo.isType(UnitType.MetalExtractor)) {
//                getGroup("MetalExtractors").addUnit(unit);
//            } else if (unitInfo.isType(UnitType.MetalStorage)) {
//                getGroup("MetalStorage").addUnit(unit);
//            } else if (unitInfo.isType(UnitType.MetalMaker)) {
//                getGroup("MetalMakers").addUnit(unit);
//            } // Energy
//            else if (unitInfo.isType(UnitType.EnergyGenerator)) {
//                getGroup("EnergyGenerators").addUnit(unit);
//            } else if (unitInfo.isType(UnitType.EnergyStorage)) {
//                getGroup("EnergyStorage").addUnit(unit);
//            } // Buildings
//            else if (unitInfo.isType(UnitType.Factory)) {
//                getGroup("Factorys").addUnit(unit);
//            } else if (unitInfo.isType(UnitType.Nano)) {
//                getGroup("Nanos").addUnit(unit);
//            } // Commander
//            else if (unitInfo.isType(UnitType.Commander)) {
//                getGroup("Commanders").addUnit(unit);
//            } else {
//                getGroup("Other").addUnit(unit);
//            }
//        } else {
//            if (unitInfo.isType(UnitType.Builder)) {
//                getGroup("T2Builders").addUnit(unit);
//                if (getGroup("T2BaseBuilders").size() >= 1 &&
//                        !getGroup("T2BaseBuilders").contains(unit)) {
//                    getGroup("T2ExpansionBuilders").addUnit(unit);
//                } else if (!getGroup("T2ExpansionBuilders").contains(unit)) {
//                    getGroup("T2BaseBuilders").addUnit(unit);
//                }
//            } else if (unitInfo.isType(UnitType.Factory)) {
//                getGroup("T2Factorys").addUnit(unit);
//            } else {
//                getGroup("Other").addUnit(unit);
//            }
//        }
//    }
//}
