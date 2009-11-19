/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.gui.model;

import javax.swing.AbstractListModel;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;

/**
 *
 * @author Petah
 */
public class GroupUnitListModel extends AbstractListModel {

    private UnitGroup group;

    public Object getElementAt(int index) {
        if (group == null) {
            return "No group selected.";
        }
        CachedUnit unit = group.getUnit(index);
        return unit.getUnitId() + ": " + unit.getDef().getHumanName();
    }

    public int getSize() {
        if (group == null) {
            return 0;
        }
        return group.size();
    }

    public void setGroup(UnitGroup group) {
        this.group = group;
    }
}
