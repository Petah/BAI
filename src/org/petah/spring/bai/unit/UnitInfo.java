/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.unit;

import org.petah.spring.bai.cache.CachedUnitDef;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.delegate.GlobalDelegate;

/**
 *
 * @author Petah
 */
public class UnitInfo implements Serializable {

    // Static properties
    private static Map<String, UnitInfo> unitsByName = new ConcurrentHashMap<String, UnitInfo>();
    private static Map<String, UnitInfo> unitsByUnitName = new ConcurrentHashMap<String, UnitInfo>();
    // Class properties
    private String name;
    private String armName;
    private String coreName;
    private CachedUnitDef armUnitDef;
    private CachedUnitDef coreUnitDef;
    private List<UnitType> types = new ArrayList<UnitType>();

    // Constructors
    public UnitInfo(String name, String armName, String coreName) {
        this.name = name;
        this.armName = armName == null ? "" : armName;
        this.coreName = coreName == null ? "" : coreName;
    }

    // Implemented methods
    @Override
    public String toString() {
        return name + " " + armName + " " + coreName + " " + Arrays.asList(types);
    }

    // Static methods
    public static void init() {
        // Parse config file
        new ConfigReader().parse();
        // Load unit defs
        for (CachedUnitDef def : GlobalDelegate.getUnitDefs().values()) {
            UnitInfo unitInfo = unitsByUnitName.get(def.getName());
            if (unitInfo != null) {
                if (unitInfo.getArmName().equals(def.getName())) {
                    unitInfo.setArmUnitDef(def);
                } else if (unitInfo.getCoreName().equals(def.getName())) {
                    unitInfo.setCoreUnitDef(def);
                }
            }
        }
    }

    public static UnitInfo getUnitInfo(CachedUnitDef def) {
        Profiler.start(UnitInfo.class, "getUnitInfo()");
        UnitInfo unitInfo = unitsByUnitName.get(def.getName());
        if (unitInfo != null) {
            return unitInfo;
        }
        Profiler.stop(UnitInfo.class, "getUnitInfo()");
        throw new RuntimeException("UnitInfo not found in index: " + def.getName());
    }

    public static UnitInfo getUnitByName(String name) {
        return unitsByName.get(name);
    }

    /**
     * Used by ConfigReader
     * @param unitInfo
     */
    public static void addUnitInfo(UnitInfo unitInfo) {
        unitsByName.put(unitInfo.getName(), unitInfo);
        unitsByUnitName.put(unitInfo.getArmName(), unitInfo);
        unitsByUnitName.put(unitInfo.getCoreName(), unitInfo);
    }

    // Accessors/Mutators
    public void addType(UnitType type) {
        types.add(type);
    }

    public boolean isType(UnitType type) {
        return types.contains(type);
    }

    // Getters
    public String getArmName() {
        return armName;
    }

    public String getCoreName() {
        return coreName;
    }

    public String getName() {
        return name;
    }

    public CachedUnitDef getArmUnitDef() {
        return armUnitDef;
    }

    public CachedUnitDef getCoreUnitDef() {
        return coreUnitDef;
    }

    public void setArmUnitDef(CachedUnitDef armUnitDef) {
        this.armUnitDef = armUnitDef;
    }

    public void setCoreUnitDef(CachedUnitDef coreUnitDef) {
        this.coreUnitDef = coreUnitDef;
    }
}
