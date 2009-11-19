/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.group;

import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.tasks.JavaScriptTask;
import org.petah.spring.bai.tasks.standard.DefaultAssault;
import org.petah.spring.bai.tasks.standard.DefaultBaseBuilderStage1;
import org.petah.spring.bai.tasks.standard.DefaultBomber;
import org.petah.spring.bai.tasks.standard.DefaultExpansionBuilder;
import org.petah.spring.bai.tasks.standard.DefaultCommanderStage1;
import org.petah.spring.bai.tasks.standard.DefaultDefence;
import org.petah.spring.bai.tasks.standard.DefaultFactory;
import org.petah.spring.bai.tasks.standard.DefaultFighter;
import org.petah.spring.bai.tasks.standard.DefaultMetalMaker;
import org.petah.spring.bai.tasks.standard.DefaultNanoStage1;
import org.petah.spring.bai.tasks.standard.DefaultRaider;
import org.petah.spring.bai.tasks.standard.DefaultScavenger;
import org.petah.spring.bai.tasks.standard.DefaultScout;
import org.petah.spring.bai.unit.Faction;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;

/**
 *
 * @author Petah
 */
public class DefaultGroupManager extends GroupManager {

    // Options
    private static Option<Integer> maxDefenceGroupSize = OptionsManager.getOption(
            new Option<Integer>("DefaultGroupManager.maxDefenceGroupSize", 20));

    public DefaultGroupManager(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    @Override
    public void initGroups() {
        addGroup(new UnitGroup(aiDelegate, "Commanders"),
                new DefaultCommanderStage1(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "MetalExtractors"));
        addGroup(new UnitGroup(aiDelegate, "EnergyGenerators"));

        addGroup(new UnitGroup(aiDelegate, "MetalStorage"));
        addGroup(new UnitGroup(aiDelegate, "EnergyStorage"));

        addGroup(new UnitGroup(aiDelegate, "MetalMakers"),
                new DefaultMetalMaker(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Radar"));

        addGroup(new UnitGroup(aiDelegate, "Nanos"),
                new DefaultNanoStage1(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Factorys"),
                new DefaultFactory(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "T2Factorys"),
                new DefaultFactory(aiDelegate));

//        addGroup(new UnitGroup(aiDelegate, "Scouts"),
//                new DefaultScout(aiDelegate));
        addGroup(new UnitGroup(aiDelegate, "Scouts"),
                new JavaScriptTask(aiDelegate, "DefaultScout", "org/petah/spring/bai/tasks/standard/script/DefaultScout.js"));

        addGroup(new UnitGroup(aiDelegate, "Defence"),
                new DefaultDefence(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Raiders"),
                new DefaultRaider(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Assault"),
                new DefaultAssault(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Artillery"),
                new DefaultAssault(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Bombers"),
                new DefaultBomber(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Fighters"),
                new DefaultFighter(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Builders"));

        addGroup(new UnitGroup(aiDelegate, "Scavengers"),
                new DefaultScavenger(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "ExpansionBuilders"),
                new DefaultExpansionBuilder(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "BaseBuilders"),
                new DefaultBaseBuilderStage1(aiDelegate));

        addGroup(new UnitGroup(aiDelegate, "Other"));
    }

    public void groupUnit(CachedUnit unit) {
        UnitInfo unitInfo = unit.getUnitInfo();
        if (unitInfo.isType(UnitType.Building)) {
            /**
             * Buildings
             */
            if (unitInfo.isType(UnitType.MetalExtractor)) {
                getGroup("MetalExtractors").addUnit(unit);
            } else if (unitInfo.isType(UnitType.EnergyGenerator)) {
                getGroup("EnergyGenerators").addUnit(unit);
            } else if (unitInfo.isType(UnitType.MetalStorage)) {
                getGroup("MetalStorage").addUnit(unit);
            } else if (unitInfo.isType(UnitType.MetalMaker)) {
                getGroup("MetalMakers").addUnit(unit);
            } else if (unitInfo.isType(UnitType.EnergyStorage)) {
                getGroup("EnergyStorage").addUnit(unit);
            } else if (unitInfo.isType(UnitType.Factory)) {
                if (unitInfo.isType(UnitType.Level2)) {
                    getGroup("T2Factorys").addUnit(unit);
                } else {
                    getGroup("Factorys").addUnit(unit);
                }
            } else if (unitInfo.isType(UnitType.Nano)) {
                getGroup("Nanos").addUnit(unit);
            } else if (unitInfo.isType(UnitType.Radar)) {
                getGroup("Radar").addUnit(unit);
            }
        } else if (unitInfo.isType(UnitType.Kbot) ||
                unitInfo.isType(UnitType.Vehicle) ||
                unitInfo.isType(UnitType.Aircraft) ||
                unitInfo.isType(UnitType.SeaPlane) ||
                unitInfo.isType(UnitType.Ship) ||
                unitInfo.isType(UnitType.Submarine) ||
                unitInfo.isType(UnitType.Experimental)) {
            /**
             * Units
             */
            if (unitInfo.isType(UnitType.Commander)) {
                getGroup("Commanders").addUnit(unit);
            } else if (unitInfo.isType(UnitType.Scavenger) ||
                    unitInfo.isType(UnitType.Engineer)) {
                getGroup("Scavengers").addUnit(unit);
            } else if (unitInfo.isType(UnitType.Scout)) {
                getGroup("Scouts").addUnit(unit);
            } else if (unitInfo.isType(UnitType.Bomber)) {
                getGroup("Bombers").addUnit(unit);
            } else if (unitInfo.isType(UnitType.Fighter)) {
                getGroup("Fighters").addUnit(unit);
            } else if (unitInfo.isType(UnitType.Raider)) {
                if (unitInfo.isType(UnitType.Kbot) &&
                        Faction.getFaction(unit.getDef()) == Faction.Core &&
                        getGroup("Scouts").size() <= getGroup("Raiders").size()) {
                    getGroup("Scouts").addUnit(unit);
                } else {
                    if (getGroup("Defence").size() <= getGroup("Assault").size() &&
                            getGroup("Defence").size() < maxDefenceGroupSize.getValue()) {
                        getGroup("Defence").addUnit(unit);
                    } else {
                        getGroup("Raiders").addUnit(unit);
                    }
                }
            } else if (unitInfo.isType(UnitType.Assault) ||
                    unitInfo.isType(UnitType.Ranger)) {
                if (getGroup("Defence").size() <= getGroup("Assault").size() &&
                        getGroup("Defence").size() < maxDefenceGroupSize.getValue()) {
                    getGroup("Defence").addUnit(unit);
                } else {
                    getGroup("Assault").addUnit(unit);
                }
            } else if (unitInfo.isType(UnitType.Artillery)) {
                getGroup("Artillery").addUnit(unit);
            } else if (unitInfo.isType(UnitType.Builder)) {
                if (unitInfo.isType(UnitType.Vehicle) &&
                        getGroup("Builders").size() - 3 > getGroup("Scavengers").size()) {
                    getGroup("Scavengers").addUnit(unit);
                } else {
                    getGroup("Builders").addUnit(unit);
                    if (getGroup("ExpansionBuilders").size() <= getGroup("BaseBuilders").size() &&
                            !getGroup("BaseBuilders").contains(unit)) {
                        getGroup("ExpansionBuilders").addUnit(unit);
                    } else if (!getGroup("ExpansionBuilders").contains(unit)) {
                        getGroup("BaseBuilders").addUnit(unit);
                    }
                }
            }
        } else {
            getGroup("Other").addUnit(unit);
        }
    }
}
