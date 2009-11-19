/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;

/**
 *
 * @author Petah
 */
public abstract class Task {

    protected AIDelegate aiDelegate;

    public Task(AIDelegate aiDelegate) {
        this.aiDelegate = aiDelegate;
    }

    /**
     * 
     * @param tbf time since last frame (time between frames)
     * @return true if task is finished
     */
    public abstract boolean update(UnitGroup group, int frame);
}
