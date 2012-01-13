/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks.standard;

import com.springrts.ai.oo.AIFloat3;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.cache.CachedUnit;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.delegate.GlobalDelegate;
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

    public void build(String name, CachedUnit factory) {
        factory.build(GlobalDelegate.getUnitDef(name, factory.getFaction()).getUnitDef(), new AIFloat3());
    }

    public boolean update(UnitGroup group, int frame) {
        if (nextUpdate <= frame) {
            nextUpdate = frame + updateTime.getValue();
            for (CachedUnit unit : group) {
                UnitInfo info = UnitInfo.getUnitInfo(unit.getDef());
//                if (info.isType(UnitType.Factory)) {
                if (unit.getCurrentCommands().isEmpty()) {
                    if (info.getName().equals("KbotFactory")) {
                        if (aiDelegate.getGroupManager().getGroup("Builders").size() < maxBuilders.getValue()) {
                            build("KT1Builder", unit);
                        }
                        if (aiDelegate.getGroupManager().getGroup("Scavengers").size() < maxScavengers.getValue()) {
                            build("Scavenger", unit);
                        }
                        if (Faction.getFaction(unit.getDef()) == Faction.Arm) {
                            if (aiDelegate.getGroupManager().getGroup("Scouts").size() < maxScouts.getValue()) {
                                build("Flea", unit);
                            }
                            build("Warrior", unit);
                        } else {
                            if (aiDelegate.getGroupManager().getGroup("Scouts").size() < maxScouts.getValue()) {
                                build("KLightRaider", unit);
                            }
                            build("KLightRaider", unit);
                        }
                        build("KLightRanger", unit);
                        build("KLightAssault", unit);
//                        if (aiDelegate.getResourceManager().isMetalOver(500)) {
//                            if (aiDelegate.getGroupManager().getGroup("Builders").size() < maxBuilders.getValue()) {
//                                build("KT1Builder", unit);
//                                build("KT1Builder", unit);
//                            }
//                            if (aiDelegate.getGroupManager().getGroup("Scavengers").size() < maxScavengers.getValue()) {
//                                build("Scavenger", unit);
//                            }
//                            if (Faction.getFaction(unit.getDef()) == Faction.Arm) {
//                                if (aiDelegate.getGroupManager().getGroup("Scouts").size() < maxScouts.getValue()) {
//                                    build("Flea", unit);
//                                    build("Flea", unit);
//                                }
//                                build("Warrior", unit);
//                            } else {
//                                build("KLightRaider", unit);
//                            }
//                            build("KLightRanger", unit);
//                            build("KLightAssault", unit);
//                        } else {
//                            if (aiDelegate.getGroupManager().getGroup("scouts").size() < maxScouts.getValue() &&
//                                    Faction.getFaction(unit.getDef()) == Faction.Arm) {
//                                build("Flea", unit);
//                            }
//                            build("KLightRaider", unit);
//                            build("KLightRaider", unit);
//                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
//                                build("KT1Builder", unit);
//                                build("KT1Builder", unit);
//                            }
//                        }
                    } else if (info.getName().equals("VehicleFactory")) {
                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
                                build("VT1Builder", unit);
                                build("VT1Builder", unit);
                            }
                            if (aiDelegate.getGroupManager().getGroup("scouts").size() < maxScouts.getValue()) {
                                build("VScout", unit);
                                build("VScout", unit);
                            }
                            build("VLightRiot", unit);
                            build("VLightAssault", unit);
                            build("VLightRanger", unit);
//                        if (aiDelegate.getResourceManager().isMetalOver(500)) {
//                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
//                                build("VT1Builder", unit);
//                                build("VT1Builder", unit);
//                            }
//                            if (aiDelegate.getGroupManager().getGroup("scouts").size() < maxScouts.getValue()) {
//                                build("VScout", unit);
//                                build("VScout", unit);
//                            }
//                            build("VLightRiot", unit);
//                            build("VLightAssault", unit);
//                            build("VLightRanger", unit);
//                        } else {
//                            if (aiDelegate.getGroupManager().getGroup("scouts").size() < maxScouts.getValue()) {
//                                build("VScout", unit);
//                                build("VScout", unit);
//                                build("VScout", unit);
//                            }
//                            build("VLightRaider", unit);
//                            if (aiDelegate.getGroupManager().getGroup("builders").size() < maxBuilders.getValue()) {
//                                build("VT1Builder", unit);
//                                build("VT1Builder", unit);
//                            }
//                            build("VLightRaider", unit);
//                        }
                    } else if (info.getName().equals("ShipFactory")) {
                    } else if (info.getName().equals("AircraftFactory")) {
                        build("AScout", unit);
                        build("AScout", unit);
                        if (aiDelegate.getGroupManager().getGroup("builders").size() < maxT2Builders.getValue()) {
                            build("AT1Builder", unit);
                        }
                        build("ALightBomber", unit);
                        build("ALightBomber", unit);
                        build("ALightBomber", unit);
                        build("ALightFighter", unit);
                    } else if (info.getName().equals("AKbotFactory")) {
                        if (aiDelegate.getGroupManager().getGroup("Builders").size() < maxT2Builders.getValue()) {
                            build("KT2Builder", unit);
                            build("KT2Builder", unit);
                        }
                        if (aiDelegate.getGroupManager().getGroup("Scavengers").size() < maxScavengers.getValue()) {
                            build("KEngineer", unit);
                        }
                        if (Faction.getFaction(unit.getDef()) == Faction.Arm) {
                            build("Sniper", unit);
                            build("Fido", unit);
                        } else {
                            build("KRocketLauncher", unit);
                        }
                        build("KRaider", unit);
                        build("KAssault", unit);
                        build("KArtillery", unit);
                        build("KHeavyAssault", unit);
                    } else if (info.getName().equals("AVehicleFactory")) {
                        if (aiDelegate.getGroupManager().getGroup("Builders").size() < maxT2Builders.getValue()) {
                            build("VT2Builder", unit);
                            build("VT2Builder", unit);
                        }
                        build("VAssault", unit);
                        build("VAssault", unit);
                        build("VAssault", unit);
                        build("VAssault", unit);
                        build("VArtillery", unit);
                        build("VRanger", unit);
                        build("VArtillery", unit);
                        build("VRanger", unit);
                        build("VAntiAir", unit);
                        if (Faction.getFaction(unit.getDef()) == Faction.Arm) {
                            if (aiDelegate.getGroupManager().getGroup("Scavengers").size() < maxScavengers.getValue()) {
                                build("VEngineer", unit);
                                build("VEngineer", unit);
                            }
                            build("LightningTank", unit);
                            build("LightningTank", unit);
                            build("LightningTank", unit);
                            build("StealthTank", unit);
                        } else {
                            build("Goliath", unit);
                        }
                    }
                }
            }
//            }
        }
        return false;
    }
}
