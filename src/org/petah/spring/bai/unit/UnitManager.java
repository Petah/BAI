///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.petah.spring.bai.unit;
//
//import com.springrts.ai.oo.clb.OOAICallback;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import org.petah.common.util.profiler.Profiler;
//import org.petah.spring.bai.cache.CacheManager;
//import org.petah.spring.bai.cache.CachedUnitDef;
//
///**
// *
// * @author Petah
// */
//public class UnitManager {
//
//    private static Map<String, UnitInfo> unitsByName = new ConcurrentHashMap<String, UnitInfo>();
//    private static Map<String, UnitInfo> unitsByUnitName = new ConcurrentHashMap<String, UnitInfo>();
//
//    public static void init() {
//        loadUnitInfoConfig();
//        loadUnitInfoDefs();
//    }
//
//    private static void loadUnitInfoConfig() {
//        new ConfigReader().parse();
//    }
//
//    private static void loadUnitInfoDefs() {
//        for (CachedUnitDef def : CacheManager.getUnitDefs()) {
//            UnitInfo unitInfo = unitsByUnitName.get(def.getName());
//            if (unitInfo != null) {
//                if (unitInfo.getArmName().equals(def.getName())) {
//                    unitInfo.setArmUnitDef(def);
//                } else if (unitInfo.getCoreName().equals(def.getName())) {
//                    unitInfo.setCoreUnitDef(def);
//                }
//            }
//        }
//    }
//
//    public static Faction getFaction(CachedUnitDef def) {
//        UnitInfo unitInfo = getUnitInfo(def);
//        if (unitInfo.getArmName().equals(def.getName())) {
//            return Faction.Arm;
//        } else if (unitInfo.getCoreName().equals(def.getName())) {
//            return Faction.Core;
//        }
//        throw new RuntimeException("Unknown unit or faction for: " + def.getName());
//    }
//
//    public static UnitInfo getUnitInfo(CachedUnitDef def) {
//        Profiler.start(UnitManager.class, "getUnitInfo()");
//        UnitInfo unitInfo = unitsByUnitName.get(def.getName());
//        if (unitInfo != null) {
//            return unitInfo;
//        }
//        Profiler.stop(UnitManager.class, "getUnitInfo()");
//        throw new RuntimeException("UnitInfo not found in index: " + def.getName());
//    }
//
//    public static CachedUnitDef getUnitDef(String name, Faction faction) {
//        UnitInfo unitInfo = unitsByName.get(name);
//        if (unitInfo != null) {
//            if (faction == Faction.Arm) {
//                return unitInfo.getArmUnitDef();
//            } else {
//                return unitInfo.getCoreUnitDef();
//            }
//        }
//        throw new RuntimeException("UnitDef not found in index: " + name + ", " + faction);
//    }
//
//    public static void addUnitInfo(UnitInfo unitInfo) {
//        unitsByName.put(unitInfo.getName(), unitInfo);
//        unitsByUnitName.put(unitInfo.getArmName(), unitInfo);
//        unitsByUnitName.put(unitInfo.getCoreName(), unitInfo);
//    }
//}
