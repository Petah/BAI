/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

/**
 *
 * @author Petah
 */
public class GroupCondition extends DynamicCondition {

    public enum CheckType {

        Over, Under, Equals
    }
    private int size;
    private String groupName;
    private CheckType checkType;

    public GroupCondition(int size, String groupName, CheckType checkType) {
        this.size = size;
        this.groupName = groupName;
        this.checkType = checkType;
    }

    public boolean check() {
        switch (checkType) {
            case Over:
                return aiDelegate.getGroupManager().getGroup(groupName).size() > size;
            case Under:
                return aiDelegate.getGroupManager().getGroup(groupName).size() < size;
            case Equals:
                return aiDelegate.getGroupManager().getGroup(groupName).size() == size;
        }
        throw new RuntimeException("Dynamic group condition failed.");
    }
}
