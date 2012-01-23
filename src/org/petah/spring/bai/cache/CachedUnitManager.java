/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.cache;

import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.WeaponDef;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import org.petah.spring.bai.AIReturnCode;
import org.petah.spring.bai.listener.DamageEventListener;
import org.petah.spring.bai.listener.EnemyEventListener;
import org.petah.spring.bai.listener.UnitEventListener;
import org.petah.spring.bai.listener.UpdateEventListener;

/**
 *
 * @author Petah
 */
public class CachedUnitManager implements UnitEventListener,
        EnemyEventListener, DamageEventListener, UpdateEventListener {

    // Units lists
    private ConcurrentHashMap<Integer, CachedUnit> friendlyUnits = new ConcurrentHashMap<Integer, CachedUnit>();
    private ConcurrentHashMap<Integer, CachedUnit> enemyUnits = new ConcurrentHashMap<Integer, CachedUnit>();

    // Public methods
    public void update(int frame) {
        for (CachedUnit unit : friendlyUnits.values()) {
            unit.update(frame);
        }
        for (CachedUnit unit : enemyUnits.values()) {
            unit.update(frame);
        }
    }

    // List accessors/mutators
    public void addFriendlyUnit(Unit unit) {
        if (!friendlyUnits.containsKey(unit.getUnitId())) {
            friendlyUnits.put(unit.getUnitId(), new CachedUnit(unit));
        }
    }

    public void removeFriendlyUnit(Unit unit) {
        friendlyUnits.remove(unit.getUnitId());
    }

    public void addEnemyUnit(Unit unit) {
        if (!enemyUnits.containsKey(unit.getUnitId())) {
            // TODO: remove this when all units have a config, maybe
            try {
                enemyUnits.put(unit.getUnitId(), new CachedUnit(unit));
            } catch (Exception e) {
                System.err.println("Unit could not be created, config might not exist.");
                System.err.println(e.getMessage());
            }
        }
    }

    public void removeEnemyUnit(Unit unit) {
        enemyUnits.remove(unit.getUnitId());
    }

    // Implemented methods
    public void unitCaptured(Unit unit, int oldTeamId, int newTeamId) {
        addFriendlyUnit(unit);
    }

    public void unitCreated(Unit unit, Unit builder) {
        addFriendlyUnit(unit);
    }

    public void unitFinished(Unit unit) {
        addFriendlyUnit(unit);
    }

    public void unitGiven(Unit unit, int oldTeamId, int newTeamId) {
        addFriendlyUnit(unit);
    }

    public void enemyEnterLOS(Unit enemy) {
        addEnemyUnit(enemy);
    }

    public void enemyEnterRadar(Unit enemy) {
        addEnemyUnit(enemy);
    }

    public void enemyLeaveLOS(Unit enemy) {
        removeEnemyUnit(enemy);
    }

    public void enemyLeaveRadar(Unit enemy) {
        removeEnemyUnit(enemy);
    }

    public void enemyDamaged(Unit enemy, Unit attacker, float damage, AIFloat3 dir, WeaponDef weaponDef, boolean paralyzer) {
    }

    public void enemyDestroyed(Unit enemy, Unit attacker) {
        removeEnemyUnit(enemy);
    }

    public void unitDamaged(Unit unit, Unit attacker, float damage, AIFloat3 dir, WeaponDef weaponDef, boolean paralyzer) {
    }

    public void unitDestroyed(Unit unit, Unit attacker) {
        removeFriendlyUnit(unit);
    }

    // Getters
    public CachedUnit getCachedUnit(int unitID) {
        if (friendlyUnits.containsKey(unitID)) {
            return friendlyUnits.get(unitID);
        }
        if (enemyUnits.containsKey(unitID)) {
            return enemyUnits.get(unitID);
        }
        return null;
    }

    public ConcurrentHashMap<Integer, CachedUnit> getEnemyUnits() {
        return enemyUnits;
    }

    public ConcurrentHashMap<Integer, CachedUnit> getFriendlyUnits() {
        return friendlyUnits;
    }
}
