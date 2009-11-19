/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import java.util.logging.Logger;
import org.petah.spring.bai.unit.*;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.UnitDef;
import java.io.File;
import java.io.Serializable;
import java.util.TreeMap;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.common.util.profiler.Profiler;
import org.petah.spring.bai.InformationLogger;
import org.petah.spring.bai.util.IOUtil;

/**
 * Manages a list of cached unit defs. Saves/loads the list of cached unit defs to
 * a file automaticly in the constructor if the CachedUnitDefManager.cacheToDisk
 * option is set.
 * @author Petah
 */
public class CachedUnitDefManager implements Serializable {

    public static final long serialVersionUID = 1L;
    // Options
    private static final Option<Boolean> cacheToDisk = OptionsManager.getOption(
            new Option<Boolean>("CachedUnitDefManager.cacheToDisk", false));
    // Class properties
    private TreeMap<Integer, CachedUnitDef> unitDefs = new TreeMap<Integer, CachedUnitDef>();

    /**
     * Testing constructor, can be used with out loading spring.
     * @param file cached unit defs file
     */
//    public CachedUnitDefManager(File file) {
//        loadCachedFile(file);
//    }
    public CachedUnitDefManager(OOAICallback callback) {
        Profiler.start(CachedUnitDefManager.class, "CachedUnitDefManager()");
        File file = new File(InformationLogger.getCacheDirectory().getAbsolutePath() + File.separator +
                callback.getMod().getFileName() + "." + serialVersionUID + ".unitdef.bin");
        CachedUnitDefManager loadedObject = (CachedUnitDefManager) IOUtil.loadCacheFile(cacheToDisk, file);
        if (loadedObject != null) {
            unitDefs = loadedObject.unitDefs;
            // Update the transient properties
            for (UnitDef def : callback.getUnitDefs()) {
                getCachedUnitDef(def.getUnitDefId()).setDef(def);
            }
            Logger.getLogger(CachedUnitDefManager.class.getName()).info("Loaded unit defs from cache.");
        } else {
            // For each unit def add it to the unit def map
            for (UnitDef def : callback.getUnitDefs()) {
                unitDefs.put(def.getUnitDefId(), new CachedUnitDef(def));
            }
            Logger.getLogger(CachedUnitDefManager.class.getName()).info("Processed unit defs.");
        }
        IOUtil.saveCacheFile(cacheToDisk, file, this);
        Profiler.stop(CachedUnitDefManager.class, "CachedUnitDefManager()");
    }

    /**
     * Gets the cached unit def for the name/faction of a unit.
     * @param name the unit name
     * @param faction the units faction
     * @return the cached unit def that matches the name and faction supplied
     * @throws UnitDefNotFoundException if not CachedUnitDef matches the name and faction supplied
     */
    public CachedUnitDef getUnitDef(String name, Faction faction) {
        UnitInfo unitInfo = UnitInfo.getUnitByName(name);
        if (unitInfo != null) {
            if (faction == Faction.Arm) {
                return unitInfo.getArmUnitDef();
            } else {
                return unitInfo.getCoreUnitDef();
            }
        }
        throw new UnitDefNotFoundException("UnitDef not found in cache: " + name + ", " + faction);
    }

    public CachedUnitDef getCachedUnitDef(int unitDefId) {
        return unitDefs.get(unitDefId);
    }

    public TreeMap<Integer, CachedUnitDef> getUnitDefs() {
        return unitDefs;
    }
}
