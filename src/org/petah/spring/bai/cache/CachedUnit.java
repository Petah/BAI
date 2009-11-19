/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import org.petah.spring.bai.unit.*;
import org.petah.spring.bai.cache.*;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.oo.CurrentCommand;
import com.springrts.ai.oo.Unit;
import java.io.Serializable;
import java.util.List;
import org.petah.common.option.Option;
import org.petah.common.option.OptionsManager;
import org.petah.spring.bai.ResourceManager;
import org.petah.spring.bai.delegate.GlobalDelegate;
import org.petah.spring.bai.unit.Faction;
import org.petah.spring.bai.unit.UnitInfo;
import org.petah.spring.bai.unit.UnitType;

/**
 *
 * @author Petah
 */
public class CachedUnit implements Serializable {

    public static final long serialVersionUID = 1L;
    // Options
    public static Option<Integer> fastUpdateTime = OptionsManager.getOption(
            new Option<Integer>("CachedUnit.fastUpdateTime", 25));
    public static Option<Integer> slowUpdateTime = OptionsManager.getOption(
            new Option<Integer>("CachedUnit.slowUpdateTime", 25));
    // Update timer
    private int nextFastUpdate = 0;
    private int nextSlowUpdate = 0;
    // Custom properties
    private transient Unit unit;
    private CachedUnitDef def;
    private UnitInfo unitInfo;
    private Faction faction;
    private boolean initialized = false;
    // Default properites
    private int allyTeam;
    private int buildingFacing;
    private float experience;
    private float health;
    private float maxHealth;
    private float maxRange;
    private float maxSpeed;
    private float power;
    private float metalMake;
    private float metalUse;
    private float energyMake;
    private float energyUse;
    private float speed;
    private int stockpile;
    private int stockpileQueued;
    private int team;
    private int unitId;
    private boolean activated;
    private boolean beingBuilt;
    private boolean cloaked;
    private boolean neutral;
    private boolean paralyzed;
    // Non serializable properties
    private transient List<CurrentCommand> currentCommands;
    private transient AIFloat3 pos;
    // Unused properties
//    private int aiHint;
//    private float currentFuel;
//    private int group;
//    private int lastUserOrderFrame;
//    private int lineage;
//    private List<ModParam> modParams;
//    private SupportedCommand supportedCommand;

    public CachedUnit(Unit unit) {
        this.unit = unit;
        fullUpdate();
    }

    public void update(int frame) {
        if (unitInfo != null && unitInfo.isType(UnitType.UpdateEveryFrame)) {
            fastUpdate();
        } else if (unitInfo == null || !unitInfo.isType(UnitType.NoUpdate)) {
            if (nextSlowUpdate <= frame) {
                nextSlowUpdate = frame + slowUpdateTime.getValue();
                nextFastUpdate = frame + fastUpdateTime.getValue();
                fullUpdate();
            } else if (nextFastUpdate <= frame) {
                nextFastUpdate = frame + fastUpdateTime.getValue();
                fastUpdate();
            }
        }
    }

    public void fastUpdate() {
        currentCommands = unit.getCurrentCommands();
        pos = unit.getPos();
        energyUse = unit.getResourceUse(ResourceManager.getMetal());
        health = unit.getHealth();
        beingBuilt = unit.isBeingBuilt();
    }

    public void fullUpdate() {
        // Only update once
        if (!initialized) {
            initialized = true;
            allyTeam = unit.getAllyTeam();
            buildingFacing = unit.getBuildingFacing();
            maxHealth = unit.getMaxHealth();
            maxRange = unit.getMaxRange();
            maxSpeed = unit.getMaxSpeed();
            unitId = unit.getUnitId();
        }
        // Update if known
        if (unit.getDef() != null) {
            def = GlobalDelegate.getCachedUnitDef(unit.getDef().getUnitDefId());
            unitInfo = UnitInfo.getUnitInfo(GlobalDelegate.getCachedUnitDef(def.getUnitDefId()));
            faction = Faction.getFaction(def);
        }
        // Update every time
        currentCommands = unit.getCurrentCommands();
        experience = unit.getExperience();
        health = unit.getHealth();
        pos = unit.getPos();
        power = unit.getPower();
        metalMake = unit.getResourceMake(ResourceManager.getMetal());
        metalUse = unit.getResourceUse(ResourceManager.getMetal());
        energyMake = unit.getResourceMake(ResourceManager.getEnergy());
        energyUse = unit.getResourceUse(ResourceManager.getMetal());
        speed = unit.getSpeed();
        stockpile = unit.getStockpile();
        stockpileQueued = unit.getStockpileQueued();
        team = unit.getTeam();
        activated = unit.isActivated();
        beingBuilt = unit.isBeingBuilt();
        cloaked = unit.isCloaked();
        neutral = unit.isNeutral();
        paralyzed = unit.isParalyzed();
        // Unused
//        currentFuel = unit.getCurrentFuel();
//        aiHint = unit.getAiHint();
//        group = unit.getGroup();
//        lineage = unit.getLineage();
//        modParams = unit.getModParams();
//        supportedCommand = unit.getSupportedCommand();
//        lastUserOrderFrame = unit.getLastUserOrderFrame();
    }

    public boolean isActivated() {
        return activated;
    }

//    public int getAiHint() {
//        return aiHint;
//    }
    public int getAllyTeam() {
        return allyTeam;
    }

    public boolean isBeingBuilt() {
        return beingBuilt;
    }

    public int getBuildingFacing() {
        return buildingFacing;
    }

    public boolean isCloaked() {
        return cloaked;
    }

    public List<CurrentCommand> getCurrentCommands() {
        return currentCommands;
    }

//    public float getCurrentFuel() {
//        return currentFuel;
//    }
    public CachedUnitDef getDef() {
        return def;
    }

    public float getEnergyMake() {
        return energyMake;
    }

    public float getEnergyUse() {
        return energyUse;
    }

    public float getExperience() {
        return experience;
    }

//    public int getGroup() {
//        return group;
//    }
    public float getHealth() {
        return health;
    }

//    public int getLastUserOrderFrame() {
//        return lastUserOrderFrame;
//    }
//
//    public int getLineage() {
//        return lineage;
//    }
    public float getMaxHealth() {
        return maxHealth;
    }

    public float getMaxRange() {
        return maxRange;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getMetalMake() {
        return metalMake;
    }

    public float getMetalUse() {
        return metalUse;
    }

//    public List<ModParam> getModParams() {
//        return modParams;
//    }
    public boolean isNeutral() {
        return neutral;
    }

    public boolean isParalyzed() {
        return paralyzed;
    }

    public AIFloat3 getPos() {
        return pos;
    }

    public float getPower() {
        return power;
    }

    public float getSpeed() {
        return speed;
    }

    public int getStockpile() {
        return stockpile;
    }

    public int getStockpileQueued() {
        return stockpileQueued;
    }

//    public SupportedCommand getSupportedCommand() {
//        return supportedCommand;
//    }
    public int getTeam() {
        return team;
    }

    public Unit getUnit() {
        return unit;
    }

    public int getUnitId() {
        return unitId;
    }

    public UnitInfo getUnitInfo() {
        return unitInfo;
    }

    public Faction getFaction() {
        return faction;
    }
}
