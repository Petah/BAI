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
public class PorcExpansionBuilderStage2 extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PorcExpansionBuilderStage2.updateTime", 20));
    // Class properties
    private int nextUpdate = 0;

    public PorcExpansionBuilderStage2(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group,  int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                if (unit.getCurrentCommands().size() == 0) {
                }
            }
        }
        return false;
    }
}
