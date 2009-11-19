/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.unit.UnitType;

/**
 *
 * @author Petah
 */
public class DefaultExpansionBuilder extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultExpansionBuilder.updateTime", 20));
    // Class properties
    private int nextUpdate = 0;

    public DefaultExpansionBuilder(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group, int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit builder : group) {
                if (builder.getUnitInfo().isType(UnitType.Builder) && builder.getCurrentCommands().size() == 0) {
                    DefaultCommands.queueClosestMetalExtractor(aiDelegate, builder,
                            DefaultCommands.getBestMetalExtractor(aiDelegate, null, builder), builder.getPos());
                }
            }
        }
        return false;
    }
}
