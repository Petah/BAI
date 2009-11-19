/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.porc;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;

/**
 *
 * @author Petah
 */
public class PorcT2BaseBuilder extends Task {

   // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PorcT2BaseBuilder.updateTime", 20));
    // Class properties
    private int nextUpdate = 0;

    public PorcT2BaseBuilder(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public boolean update(UnitGroup group,  int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                if (unit.getCurrentCommands().size() == 0) {
                    PorcCommands.queueFusion(aiDelegate, unit);
                }
            }
        }
        return false;
    }
}
