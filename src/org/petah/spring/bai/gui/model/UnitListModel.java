/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.gui.model;

import java.util.Map;
import javax.swing.AbstractListModel;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.TeamDelegate;

/**
 *
 * @author Petah
 */
public class UnitListModel extends AbstractListModel {

    Map<Integer, CachedUnit> units;

    public UnitListModel(Map<Integer, CachedUnit> units) {
        this.units = units;
    }

    public Object getElementAt(int index) {
        CachedUnit unit = getUnit(index);
        if (unit != null) {
            if (unit.getDef() != null) {
                return "Team " + unit.getTeam() + ": " + unit.getUnitId() + " " + unit.getDef().getHumanName();
            }
            return "Team " + unit.getTeam() + ": " + unit.getUnitId() + " Unknown Unit";
        }
        return "Exception thrown in UnitListModel.getUnit().";
    }

    public int getSize() {
        return units.values().size();
    }

    public CachedUnit getUnit(int index) {
        try {
            return (CachedUnit) units.values().toArray()[index];
        } catch (Exception e) {
            return null;
        }
    }
}
