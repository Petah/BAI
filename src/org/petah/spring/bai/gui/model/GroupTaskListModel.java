/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.gui.model;

import javax.swing.AbstractListModel;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;

/**
 *
 * @author Petah
 */
public class GroupTaskListModel extends AbstractListModel {

    private UnitGroup group;

    public Object getElementAt(int index) {
        if (group == null) {
            return "No group selected.";
        }
        return group.getTasks().get(index).getClass().getSimpleName();
    }

    public int getSize() {
        if (group == null) {
            return 0;
        }
        return group.getTasks().size();
    }

    public void setGroup(UnitGroup group) {
        this.group = group;
    }
}
