/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.unit;

import org.petah.spring.bai.cache.CachedUnitDef;
import java.io.Serializable;

/**
 *
 * @author Petah
 */
public enum Faction implements Serializable {

    Arm, Core;

    public static Faction getFaction(CachedUnitDef def) {
        UnitInfo unitInfo = UnitInfo.getUnitInfo(def);
        if (unitInfo.getArmName().equals(def.getName())) {
            return Faction.Arm;
        } else if (unitInfo.getCoreName().equals(def.getName())) {
            return Faction.Core;
        }
        throw new RuntimeException("Unknown unit or faction for: " + def.getName());
    }
}
