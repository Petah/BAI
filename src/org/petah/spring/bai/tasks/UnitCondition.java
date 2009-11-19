/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

import org.petah.spring.bai.unit.UnitInfo;

/**
 *
 * @author Petah
 */
public class UnitCondition extends DynamicCondition {

    private String unitName;

    public UnitCondition(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public boolean check() {
        UnitInfo info = UnitInfo.getUnitInfo(unit.getDef());
        return info.getName().equals(unitName);
    }
}
