/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import com.springrts.ai.oo.CallbackAIException;
import com.springrts.ai.oo.clb.Feature;
import com.springrts.ai.oo.clb.UnitDef;
import org.petah.spring.bai.unit.*;
import org.petah.spring.bai.cache.*;
import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Command;
import com.springrts.ai.oo.clb.Unit;
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

    // Serialization ID
    public static final long serialVersionUID = 2L;

    // Option definitions
    public static final short OPT_NONE = 0;
    public static final short OPT_QUEUE = 32;

    // Building facing directions
    public static final int FACING_NORTH    = 0;
    public static final int FACING_EAST     = 1;
    public static final int FACING_SOUTH    = 2;
    public static final int FACING_WEST     = 3;

    // Move states
    public static final int MOVE_STATE_HOLD_POSITION    = 0;
    public static final int MOVE_STATE_MANEUVER         = 1;
    public static final int MOVE_STATE_ROAM             = 2;

    // Options
    public static Option<Integer> fastUpdateTime = OptionsManager.getOption(
            new Option<Integer>("CachedUnit.fastUpdateTime", 25));
    public static Option<Integer> slowUpdateTime = OptionsManager.getOption(
            new Option<Integer>("CachedUnit.slowUpdateTime", 25));
    private static Option<Integer> commandTimeout = OptionsManager.getOption(
            new Option<Integer>("CachedUnit.commandTimeout", 3000));

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
    private transient List<Command> currentCommands;
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

    //<editor-fold defaultstate="collapsed" desc="Unit Delegates">
    public void unloadUnitsInArea(AIFloat3 aif, float f) throws CallbackAIException {
        unit.unloadUnitsInArea(aif, f, OPT_NONE, commandTimeout.getValue());
    }

    public void unload(AIFloat3 aif, Unit unit) throws CallbackAIException {
        this.unit.unload(aif, unit, OPT_NONE, commandTimeout.getValue());
    }

    public void stop() throws CallbackAIException {
        unit.stop(OPT_NONE, commandTimeout.getValue());
    }

    public void stockpile() throws CallbackAIException {
        unit.stockpile(OPT_NONE, commandTimeout.getValue());
    }

    public void setWantedMaxSpeed(float f) throws CallbackAIException {
        unit.setWantedMaxSpeed(f, OPT_NONE, commandTimeout.getValue());
    }

    public void setTrajectory(int i) throws CallbackAIException {
        unit.setTrajectory(i, OPT_NONE, commandTimeout.getValue());
    }

    public void setRepeat(boolean bln) throws CallbackAIException {
        unit.setRepeat(bln, OPT_NONE, commandTimeout.getValue());
    }

    public void setOn(boolean bln) throws CallbackAIException {
        unit.setOn(bln, OPT_NONE, commandTimeout.getValue());
    }

    public void setMoveState(int i) throws CallbackAIException {
        unit.setMoveState(i, OPT_NONE, commandTimeout.getValue());
    }

    public void setIdleMode(int i) throws CallbackAIException {
        unit.setIdleMode(i, OPT_NONE, commandTimeout.getValue());
    }

    public void setFireState(int i) throws CallbackAIException {
        unit.setFireState(i, OPT_NONE, commandTimeout.getValue());
    }

    public void setBase(AIFloat3 aif) throws CallbackAIException {
        unit.setBase(aif, OPT_NONE, commandTimeout.getValue());
    }

    public void setAutoRepairLevel(int i) throws CallbackAIException {
        unit.setAutoRepairLevel(i, OPT_NONE, commandTimeout.getValue());
    }

    public void selfDestruct() throws CallbackAIException {
        unit.selfDestruct(OPT_NONE, commandTimeout.getValue());
    }

    public void resurrectInArea(AIFloat3 aif, float f) throws CallbackAIException {
        unit.resurrectInArea(aif, f, OPT_NONE, commandTimeout.getValue());
    }

    public void resurrect(Feature ftr) throws CallbackAIException {
        unit.resurrect(ftr, OPT_NONE, commandTimeout.getValue());
    }

    public void restoreArea(AIFloat3 aif, float f) throws CallbackAIException {
        unit.restoreArea(aif, f, OPT_NONE, commandTimeout.getValue());
    }

    public void repair(Unit unit) throws CallbackAIException {
        this.unit.repair(unit, OPT_NONE, commandTimeout.getValue());
    }

    public void removeFromGroup() throws CallbackAIException {
        unit.removeFromGroup(OPT_NONE, commandTimeout.getValue());
    }

    public void reclaimUnit(Unit unit) throws CallbackAIException {
        this.unit.reclaimUnit(unit, OPT_NONE, commandTimeout.getValue());
    }

    public void reclaimUnit(Unit unit, boolean queue) throws CallbackAIException {
        this.unit.reclaimUnit(unit, queue ? OPT_QUEUE : OPT_NONE, commandTimeout.getValue());
    }

    public void reclaimFeature(Feature ftr) throws CallbackAIException {
        unit.reclaimFeature(ftr, OPT_NONE, commandTimeout.getValue());
    }

    public void reclaimInArea(AIFloat3 aif, float area) throws CallbackAIException {
        unit.reclaimInArea(aif, area, OPT_NONE, commandTimeout.getValue());
    }

    public void patrolTo(AIFloat3 aif) throws CallbackAIException {
        unit.patrolTo(aif, OPT_NONE, commandTimeout.getValue());
    }

    public void moveTo(AIFloat3 aif) throws CallbackAIException {
        unit.moveTo(aif, OPT_NONE, commandTimeout.getValue());
    }

    public void loadUnitsInArea(AIFloat3 aif, float f) throws CallbackAIException {
        unit.loadUnitsInArea(aif, f, OPT_NONE, commandTimeout.getValue());
    }

    public void loadUnits(List<Unit> list) throws CallbackAIException {
        unit.loadUnits(list, OPT_NONE, commandTimeout.getValue());
    }

    public void loadOnto(Unit unit) throws CallbackAIException {
        this.unit.loadOnto(unit, OPT_NONE, commandTimeout.getValue());
    }

    public void guard(Unit unit) throws CallbackAIException {
        this.unit.guard(unit, OPT_NONE, commandTimeout.getValue());
    }

    public void fight(AIFloat3 aif) throws CallbackAIException {
        unit.fight(aif, OPT_NONE, commandTimeout.getValue());
    }

    public void executeCustomCommand(int i, List<Float> list) throws CallbackAIException {
        unit.executeCustomCommand(i, list, OPT_NONE, commandTimeout.getValue());
    }

    public void dGunPosition(AIFloat3 aif) throws CallbackAIException {
        unit.dGunPosition(aif, OPT_NONE, commandTimeout.getValue());
    }

    public void dGun(Unit unit) throws CallbackAIException {
        this.unit.dGun(unit, OPT_NONE, commandTimeout.getValue());
    }

    public void cloak(boolean bln) throws CallbackAIException {
        unit.cloak(bln, OPT_NONE, commandTimeout.getValue());
    }

    public void captureInArea(AIFloat3 aif, float f) throws CallbackAIException {
        unit.captureInArea(aif, f, OPT_NONE, commandTimeout.getValue());
    }

    public void capture(Unit unit) throws CallbackAIException {
        this.unit.capture(unit, OPT_NONE, commandTimeout.getValue());
    }

    public void build(UnitDef ud, AIFloat3 aif) throws CallbackAIException {
        unit.build(ud, aif, FACING_EAST, OPT_NONE, commandTimeout.getValue());
    }

    public void build(UnitDef ud, AIFloat3 aif, int facing) throws CallbackAIException {
        unit.build(ud, aif, facing, OPT_QUEUE, commandTimeout.getValue());
    }

    public void build(UnitDef ud, AIFloat3 aif, int facing, boolean queue) throws CallbackAIException {
        unit.build(ud, aif, facing, queue ? OPT_QUEUE : OPT_NONE, commandTimeout.getValue());
    }

    public void attackArea(AIFloat3 aif, float f) throws CallbackAIException {
        unit.attackArea(aif, f, OPT_NONE, commandTimeout.getValue());
    }

    public void attack(Unit unit) throws CallbackAIException {
        this.unit.attack(unit, OPT_NONE, commandTimeout.getValue());
    }

    public void attack(Unit unit, boolean queue) throws CallbackAIException {
        this.unit.attack(unit, queue ? OPT_QUEUE : OPT_NONE, commandTimeout.getValue());
    }
    //</editor-fold>

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

    public List<Command> getCurrentCommands() {
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
