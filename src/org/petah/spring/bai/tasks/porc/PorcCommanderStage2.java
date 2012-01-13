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
import org.petah.spring.bai.tasks.standard.DefaultCommands;
import org.petah.spring.bai.tasks.Task;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class PorcCommanderStage2 extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("PorcCommanderStage2.updateTime", 20));
    // Class properties
    private int nextUpdate = 0;
    private State state = State.Build;

    public PorcCommanderStage2(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group,  int frame) {
        // If its time to update
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            if (group.size() > 0) {
                CachedUnit builder = group.getUnit(0);
                switch (state) {
                    case GuardFactory:
                    case GuardBuilder:
                        if (getNeededBuilding() != Building.None) {
                            builder.stop();
                            state = State.Build;
                        } else {
                            if (aiDelegate.getGroupManager().getGroup("baseBuilders").size() > 0 &&
                                    state != State.GuardBuilder) {
                                PorcCommands.guardBaseBuilder(aiDelegate, builder);
                            }
                            break;
                        }
                    case Build:
                        if (builder.getCurrentCommands().size() == 0) {
                            if (queueBuilding(builder)) {
                                if (aiDelegate.getGroupManager().getGroup("baseBuilders").size() > 0) {
                                    PorcCommands.guardBaseBuilder(aiDelegate, builder);
                                    state = State.GuardBuilder;
                                } else if (aiDelegate.getGroupManager().getGroup("factorys").size() > 0) {
                                    DefaultCommands.guardFactory(aiDelegate, builder);
                                    state = State.GuardFactory;
                                }
                            }
                        }
                        break;
                }
            }
        }
        return false;
    }

    private enum State {

        GuardFactory, GuardBuilder, Build
    }

    private enum Building {

        EnergyStorage, MetalStorage, MetalMaker, None
    }

    private boolean queueBuilding(CachedUnit builder) {
        switch (getNeededBuilding()) {
            case EnergyStorage:
                PorcCommands.queueEnergyStorage(aiDelegate, builder);
                break;
            case MetalStorage:
                PorcCommands.queueMetalStorage(aiDelegate, builder);
                break;
            case MetalMaker:
                PorcCommands.queueMetalMaker(aiDelegate, builder);
                break;
            case None:
                return true;
        }
        return false;
    }

    private Building getNeededBuilding() {
        if (aiDelegate.getResourceManager().isMetalOver(0.90f) &&
                aiDelegate.getGroupManager().getGroup("metalStorage").size() == 0) {
            return Building.MetalStorage;
        } else if (aiDelegate.getResourceManager().isEnergyOver(0.90f, 150)) {
            if (aiDelegate.getGroupManager().getGroup("energyStorage").size() == 0) {
                return Building.EnergyStorage;
            } else {
                return Building.MetalMaker;
            }
        }
        return Building.None;
    }
}
