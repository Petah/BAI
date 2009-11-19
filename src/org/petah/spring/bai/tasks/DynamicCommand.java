/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;

/**
 *
 * @author Petah
 */
public abstract class DynamicCommand {

    protected CachedUnit unit;
    protected AIDelegate aiDelegate;

    protected abstract void execute();

    public void execute(AIDelegate aiDelegate, CachedUnit unit) {
        this.aiDelegate = aiDelegate;
        this.unit = unit;
        execute();
    }
}
