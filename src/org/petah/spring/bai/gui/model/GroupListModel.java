/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.gui.model;

import javax.swing.AbstractListModel;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;

/**
 *
 * @author Petah
 */
public class GroupListModel extends AbstractListModel {

    private AIDelegate aiDelegate;

    public GroupListModel(AIDelegate aiDelegate) {
        this.aiDelegate = aiDelegate;
    }

    public Object getElementAt(int index) {
        UnitGroup group = getGroup(index);
        if (group != null) {
            return group.getName();
        }
        return "Group not found for index: " + index;
    }

    public int getSize() {
        return aiDelegate.getGroupManager().getGroups().size();
    }

    public UnitGroup getGroup(int index) {
        int count = 0;
        for (UnitGroup group : aiDelegate.getGroupManager().getGroups().values()) {
            if (count == index) {
                return group;
            }
            count++;
        }
        return null;
    }
}
