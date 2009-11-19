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
import org.petah.spring.bai.unit.Faction;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.util.CommandUtil;

/**
 *
 * @author Petah
 */
public class DefaultFactory extends Task {

    // Options
    private static Option<Integer> updateTime = OptionsManager.getOption(
            new Option<Integer>("DefaultFactory.updateTime", 20));
    private static Option<Integer> maxBuilders = OptionsManager.getOption(
            new Option<Integer>("DefaultFactory.maxBuilders", 4));
    private static Option<Integer> maxT2Builders = OptionsManager.getOption(
            new Option<Integer>("DefaultFactory.maxT2Builders", 6));
    private static Option<Integer> maxScavengers = OptionsManager.getOption(
            new Option<Integer>("DefaultFactory.maxScavengers", 4));
    private static Option<Integer> maxScouts = OptionsManager.getOption(
            new Option<Integer>("DefaultFactory.maxScouts", 8));
    // Class properties
    private int nextUpdate = 0;

    public DefaultFactory(AIDelegate aiDelegate) {
        super(aiDelegate);
    }

    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                UnitInfo info = UnitInfo.getUnitInfo(unit.getDef());
//                if (info.isType(UnitType.Factory)) {
                if (unit.getCurrentCommands().size() == 0) {
                    if (info.getName().equals("KbotFactory")) {
                        if (aiDelegate.getGroupManager().getGroup("Builders").size() < maxBuilders.getValue()) {
                            CommandUtil.factoryBuild(aiDelegate, "KT1Builder", unit);
                        }
                        if (aiDelegate.getGroupManager().getGroup("Scavengers").size() < maxScavengers.getValue()) {
                            CommandUtil.factoryBuild(aiDelegate, "Scavenger", unit);
                        }
                        if (Faction.getFaction(unit.getDef()) == Faction.Arm) {
                            if (aiDelegate.getGroupManager().getGroup("Scouts").size() < maxScouts.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "Flea", unit);
                            }
                            CommandUtil.factoryBuild(aiDelegate, "Warrior", unit);
                        } else {
                            if (aiDelegate.getGroupManager().getGroup("Scouts").size() < maxScouts.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "KLightRaider", unit);
                            }
                            CommandUtil.factoryBuild(aiDelegate, "KLightRaider", unit);
                        }
                        CommandUtil.factoryBuild(aiDelegate, "KLightRanger", unit);
                        CommandUtil.factoryBuild(aiDelegate, "KLightAssault", unit);
//                        if (aiDelegate.getResourceManager().isMetalOver(500)) {
//                            if (aiDelegate.getGroupManager().getGroup("Builders").size() < maxBuilders.getValue()) {
//                                CommandUtil.factoryBuild(aiDelegate, "KT1Builder", unit);
//                                CommandUtil.factoryBuild(aiDelegate, "KT1Builder", unit);
//                            }
//                            if (aiDelegate.getGroupManager().getGroup("Scavengers").size() < maxScavengers.getValue()) {
//                                CommandUtil.factoryBuild(aiDelegate, "Scavenger", unit);
//                            }
//                            if (Faction.getFaction(unit.getDef()) == Faction.Arm) {
//                                if (aiDelegate.getGroupManager().getGroup("Scouts").size() < maxScouts.getValue()) {
//                                    CommandUtil.factoryBuild(aiDelegate, "Flea", unit);
//                                    CommandUtil.factoryBuild(aiDelegate, "Flea", unit);
//                                }
//                                CommandUtil.factoryBuild(aiDelegate, "Warrior", unit);
//                            } else {
//                                CommandUtil.factoryBuild(aiDelegate, "KLightRaider", unit);
//                            }
//                            CommandUtil.factoryBuild(aiDelegate, "KLightRanger", unit);
//                            CommandUtil.factoryBuild(aiDelegate, "KLightAssault", unit);
//                        } else {
//                            if (aiDelegate.getGroupManager().getGroup("scouts").size() < maxScouts.getValue() &&
//                                    Faction.getFaction(unit.getDef()) == Faction.Arm) {
//                                CommandUtil.factoryBuild(aiDelegate, "Flea", unit);
//                            }
//                            CommandUtil.factoryBuild(aiDelegate, "KLightRaider", unit);
//                            CommandUtil.factoryBuild(aiDelegate, "KLightRaider", unit);
//                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
//                                CommandUtil.factoryBuild(aiDelegate, "KT1Builder", unit);
//                                CommandUtil.factoryBuild(aiDelegate, "KT1Builder", unit);
//                            }
//                        }
                    } else if (info.getName().equals("VehicleFactory")) {
                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "VT1Builder", unit);
                                CommandUtil.factoryBuild(aiDelegate, "VT1Builder", unit);
                            }
                            if (aiDelegate.getGroupManager().getGroup("scouts").size() < maxScouts.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "VScout", unit);
                                CommandUtil.factoryBuild(aiDelegate, "VScout", unit);
                            }
                            CommandUtil.factoryBuild(aiDelegate, "VLightRiot", unit);
                            CommandUtil.factoryBuild(aiDelegate, "VLightAssault", unit);
                            CommandUtil.factoryBuild(aiDelegate, "VLightRanger", unit);
//                        if (aiDelegate.getResourceManager().isMetalOver(500)) {
//                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
//                                CommandUtil.factoryBuild(aiDelegate, "VT1Builder", unit);
//                                CommandUtil.factoryBuild(aiDelegate, "VT1Builder", unit);
//                            }
//                            if (aiDelegate.getGroupManager().getGroup("scouts").size() < maxScouts.getValue()) {
//                                CommandUtil.factoryBuild(aiDelegate, "VScout", unit);
//                                CommandUtil.factoryBuild(aiDelegate, "VScout", unit);
//                            }
//                            CommandUtil.factoryBuild(aiDelegate, "VLightRiot", unit);
//                            CommandUtil.factoryBuild(aiDelegate, "VLightAssault", unit);
//                            CommandUtil.factoryBuild(aiDelegate, "VLightRanger", unit);
//                        } else {
//                            if (aiDelegate.getGroupManager().getGroup("scouts").size() < maxScouts.getValue()) {
//                                CommandUtil.factoryBuild(aiDelegate, "VScout", unit);
//                                CommandUtil.factoryBuild(aiDelegate, "VScout", unit);
//                                CommandUtil.factoryBuild(aiDelegate, "VScout", unit);
//                            }
//                            CommandUtil.factoryBuild(aiDelegate, "VLightRaider", unit);
//                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
//                                CommandUtil.factoryBuild(aiDelegate, "VT1Builder", unit);
//                                CommandUtil.factoryBuild(aiDelegate, "VT1Builder", unit);
//                            }
//                            CommandUtil.factoryBuild(aiDelegate, "VLightRaider", unit);
//                        }
                    } else if (info.getName().equals("ShipFactory")) {
                    } else if (info.getName().equals("AircraftFactory")) {
                        CommandUtil.factoryBuild(aiDelegate, "AScout", unit);
                        CommandUtil.factoryBuild(aiDelegate, "AScout", unit);
                        if (aiDelegate.getGroupManager().getGroup("builders").size() < maxT2Builders.getValue()) {
                            CommandUtil.factoryBuild(aiDelegate, "AT1Builder", unit);
                        }
                        CommandUtil.factoryBuild(aiDelegate, "ALightBomber", unit);
                        CommandUtil.factoryBuild(aiDelegate, "ALightBomber", unit);
                        CommandUtil.factoryBuild(aiDelegate, "ALightBomber", unit);
                        CommandUtil.factoryBuild(aiDelegate, "ALightFighter", unit);
                    } else if (info.getName().equals("AKbotFactory")) {
                        if (aiDelegate.getGroupManager().getGroup("Builders").size() < maxT2Builders.getValue()) {
                            CommandUtil.factoryBuild(aiDelegate, "KT2Builder", unit);
                            CommandUtil.factoryBuild(aiDelegate, "KT2Builder", unit);
                        }
                        if (aiDelegate.getGroupManager().getGroup("Scavengers").size() < maxScavengers.getValue()) {
                            CommandUtil.factoryBuild(aiDelegate, "KEngineer", unit);
                        }
                        if (Faction.getFaction(unit.getDef()) == Faction.Arm) {
                            CommandUtil.factoryBuild(aiDelegate, "Sniper", unit);
                            CommandUtil.factoryBuild(aiDelegate, "Fido", unit);
                        } else {
                            CommandUtil.factoryBuild(aiDelegate, "KRocketLauncher", unit);
                        }
                        CommandUtil.factoryBuild(aiDelegate, "KRaider", unit);
                        CommandUtil.factoryBuild(aiDelegate, "KAssault", unit);
                        CommandUtil.factoryBuild(aiDelegate, "KArtillery", unit);
                        CommandUtil.factoryBuild(aiDelegate, "KHeavyAssault", unit);
                    } else if (info.getName().equals("AVehicleFactory")) {
                        if (aiDelegate.getGroupManager().getGroup("Builders").size() < maxT2Builders.getValue()) {
                            CommandUtil.factoryBuild(aiDelegate, "VT2Builder", unit);
                            CommandUtil.factoryBuild(aiDelegate, "VT2Builder", unit);
                        }
                        CommandUtil.factoryBuild(aiDelegate, "VAssault", unit);
                        CommandUtil.factoryBuild(aiDelegate, "VAssault", unit);
                        CommandUtil.factoryBuild(aiDelegate, "VAssault", unit);
                        CommandUtil.factoryBuild(aiDelegate, "VAssault", unit);
                        CommandUtil.factoryBuild(aiDelegate, "VArtillery", unit);
                        CommandUtil.factoryBuild(aiDelegate, "VRanger", unit);
                        CommandUtil.factoryBuild(aiDelegate, "VArtillery", unit);
                        CommandUtil.factoryBuild(aiDelegate, "VRanger", unit);
                        CommandUtil.factoryBuild(aiDelegate, "VAntiAir", unit);
                        if (Faction.getFaction(unit.getDef()) == Faction.Arm) {
                            if (aiDelegate.getGroupManager().getGroup("Scavengers").size() < maxScavengers.getValue()) {
                                CommandUtil.factoryBuild(aiDelegate, "VEngineer", unit);
                                CommandUtil.factoryBuild(aiDelegate, "VEngineer", unit);
                            }
                            CommandUtil.factoryBuild(aiDelegate, "LightningTank", unit);
                            CommandUtil.factoryBuild(aiDelegate, "LightningTank", unit);
                            CommandUtil.factoryBuild(aiDelegate, "LightningTank", unit);
                            CommandUtil.factoryBuild(aiDelegate, "StealthTank", unit);
                        } else {
                            CommandUtil.factoryBuild(aiDelegate, "Goliath", unit);
                        }
                    }
                }
            }
//            }
        }
        return false;
    }
}
